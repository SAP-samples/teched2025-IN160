# Get ready (read only)

In this exercise, you will get an overview of the prerequisites needed for the configuration and setup of essential system requirements for the automated order processing scenario.
As these steps are only executed once per tenant, we have completed all steps for you. Nevertheless, you can go through them to understand the context before moving on to the next exercises and getting your hands dirty.


This is an architecture diagram describing our use case:

<br>![](./images/architectureOverview.png)


1. First you have to setup your Shopify system to create API keys and configure the order webhook.
2. Webhooks in Shopify use Hash-Based Message Authentication Codes (HMAC), see [Shopify documentation](https://shopify.dev/docs/apps/build/webhooks/subscribe/https). The API Management capability of SAP Integration Suite validates the origin of the webhooks.
3. After successful validation, an Integration Flow is triggered to publish the order event to Event Mesh. 
4. In Event Mesh, there is a queue subscribed to the topic.  The event will be stored in the queue until it is delivered via webhook to SAP Build Process Automation.
5. The user has to approve or reject the Sales Order. Once approved, an Integration Flow is triggered via an action.
6. The Integration Flow finally creates the Sales Order in SAP S/4HANA Public Cloud. Additionally, the order in Shopify gets updated with the SAP S/4HANA external ID to allow an easy mapping between Shopify and SAP Orders.


## 1. Create API Keys in Shopify (read only)

You need to create API keys to execute Shopify APIs. The API credentials need to be maintained in your SAP Integration Suite tenant.

1. Log in to your Shopify system and go to **Settings**.

<br>![](./images/0_31.png)

2.	Go to **Apps and sales channels**.

<br>![](./images/0_32.png)

3.	Click the **Develop apps** button.

<br>![](./images/0_33.png)

4.	Click on **Allow legacy custom app development**.

<br>![](./images/0_34.png)

5.	Click **Allow custom app development**.

<br>![](./images/0_35.png)

6.	Click on **Create a legacy custom app**.

<br>![](./images/0_36.png)

7.	**Enter** app details, then click **Create app**.

<br>![](./images/0_37.png)

8. Go to the **Configuration** tab, **select** all boxes to assign roles, and click on **Save**.

<br>![](./images/0_38.png)

9. Go to the **API credentials** tab, then click **Install app**.

<br>![](./images/0_39.png)

10. In the dialog, click on **Install**.

<br>![](./images/0_40.png)

11. **Reveal** the admin API access token, and copy it in your notepad.

<br>![](./images/0_41.png)

12. Go to your provided cloud integration tenant. Click on **Monitor>Integration and APIs**.

<br>![](./images/0_42.png)

13. Go to **security material**.

<br>![](./images/0_43.png)

14. Click **Create>Secure parameter**.

<br>![](./images/0_44.png)

15. Enter **name**, **description** and the **shopify key** you copied in the previous step, and click on **Deploy**.

<br>![](./images/0_45.png)


### 2. Create Webhook and validate HMAC header (read only)
There are generally two different options for retrieving orders from Shopify. The prepackaged content uses a pull-based approach via the Shopify API to retrieve all newly created orders. However, for this hand-on exercise using webhooks is more suitable due to the real-time data replication. 
<br>
Webhooks in Shopify only support digest-based authentication via HMAC (Hash-based Message Authentication Code), which verifies the data integrity and authenticity of a message using a secret cryptographic key and a hash function. Here is how it works:<br> 

1. **Secret Key Generation:** Shopify creates a secret key that is shared with the client.<br> 

2. **HMAC Computation**: Shopify computes the HMAC using the message payload and the secret key and stores the result in the header "x-shopify-hmac-sha256". <br>

3. **Client Verification**
The client verifies the origin of the webhook, by computing the HMAC in the same way as Shopify and compares the result with the HMAC provided in the header. 
<br>
Since Cloud Integration does not support such digest-based authentication, we will leverage the API-Management capability to achieve this.
<br>

### 2.1 Get Shopify secret key for webhooks

Navigate to your Shopify Tenant and open the **Settings** page. Navigate to **Notifications** and choose **Webhook**. 
Here you find the secret key that is used for signing the webhook. This key needs to be maintained as secret parameter in API Management capability of SAP Integration Suite. 
<br>![](./images/webhookSecret.png)

This secret key has to be maintained in SAP Integration Suite as encrypted Key Value Map. 
Open **Configure APIs** and create a new Key Value Map. This Key Value Map will be referenced in the API Policy for HMAC validation. 
<br>
<br>![](./images/webhookAPIM.png)


### 2.2 Create an API Proxy for HMAC validation

We now create an API Proxy to validate the HMAC header provided by Shopify. After successful validation, an Integration Flow will be triggered to publish the order from Shopify to Event Mesh. Therefore, you can register Cloud Integration as an **API Provider**, see this link for more information: https://help.sap.com/docs/sap-api-management/sap-api-management-for-neo-environment/creating-api-from-sap-cloud-integration-api-provider

1. First you have to create a new API Proxy from the Cloud Integration API Provider. You need to define the HTTP Endpoint of the Integration Flow you want to call. The creation of the integration flow is explained in the next section.
      
<br>![](./images/0_100.png)

2. Next, you'll define policies for API proxy. Click on **ShopifyWebhook**, then on the three dots and **Policies**.

<br>![](./images/0_11.png)

3. Define proxies in the **PreFlow**. Add **Key Value Map Operations** by clicking on **+** symbol next to it. We need to retrieve the Shopify secret stored in the step before.

<br>![](./images/0_12.png)

4. In the dialog, you'll define the Key Value Map Operations policy. For that, provide the **Policy Name** as **GetShopifyKey** and click on **Add**.

<br>![](./images/0_13.png)

5. Click on **GetShopifyKey** and update the XML as follows:

<br>![](./images/0_14.png)


Add following configuration:
```
<!-- Key/value pairs can be stored, retrieved, and deleted from named existing maps by configuring this policy by specifying PUT, GET, or DELETE operations -->
<!-- mapIdentifier refers to the name of the key value map -->
<!-- Don't use Key Value Maps to store your logs as this can impact API Proxy runtime flow -->
<KeyValueMapOperations mapIdentifier="ShopifyKey" async="true" continueOnError="false" enabled="true" xmlns="http://www.sap.com/apimgmt">
    <Get assignTo="private.key">
        <Key>
            <Parameter>key</Parameter>
        </Key>
    </Get>
	<!-- the scope of the key value map. Valid values are environment, organization, apiproxy and policy -->
	<Scope>environment</Scope>
</KeyValueMapOperations>
```


<br>![](./images/0_15.png)

6. Next, we will add a script for validating the HMAC header. Navigate to **Extension Policies** and click on **Python Script**.
    
<br>![](./images/0_16.png)

Provide the **Policy Name: checkHash** and click **Add**.

<br>![](./images/0_17.png)

The Policy editor will appear as follows:

<br>![](./images/0_18.png)

7. Next, add the **checkHash** python script by clicking on new script

<br>![](./images/0_19.png)

Then, provide details as follows:
- Script name: validate
- Script type: python
- Script: create
And click **Add**.

<br>![](./images/0_20.png)


8. Provide **Script Resource** by pasting the following in the section:
```
import hashlib
import hmac
import base64
import random
import string

def make_digest(secret, payload):
  hashBytes = hmac.new(secret, msg=payload, digestmod=hashlib.sha256).digest();
  base64Hash = base64.b64encode(hashBytes);
  return base64Hash;

message = "";
message = flow.getVariable("request.content");

webhookHash = flow.getVariable("request.header.x-shopify-hmac-sha256");
key =  flow.getVariable("private.key")
message = str(message);
flow.setVariable("keylog",key);
flow.setVariable("messagelog",message);

resultHash = make_digest(key, message);
flow.setVariable("resultHash",resultHash);

lettersAndDigits = string.ascii_letters + string.digits;
randomKey = ''.join((random.choice(lettersAndDigits) for i in range(44)));

flow.setVariable("randomKey",randomKey);
doubleWebhookHash = make_digest( randomKey, webhookHash );
doubleResultHash = make_digest( randomKey, resultHash );

flow.setVariable("doubleWebhookHash",doubleWebhookHash);
flow.setVariable("doubleResultHash",doubleResultHash);

if (doubleResultHash != doubleWebhookHash):
    raise NameError("Invalid Origin! Different HMAC.")
```
<br>![](./images/0_21_2.png)


9. Go to **ProxyEndpoint>PreFlow**, click on **checkHash** policy, point the resource in the xml to the python script created earlier.

<br>![](./images/0_23.png)

The updated script in the **checkHash** policy will be as follows:
```
<!-- timelimit refers to the time limit for the execution of the policy -->
<Script async="false" continueOnError="false" enabled="true" timeLimit="200" xmlns='http://www.sap.com/apimgmt'>
	<!-- Include URL elements can refer to the script libraries that the main python script uses -->
	<!-- Resource URL refers to the main script file that should be run -->
	<ResourceURL>py://validate.py</ResourceURL>
</Script>
<!-- also creates two script files, myscriptmain.py and myscripthelp.py -->
```



Click on **Update** in the top right corner.

10. Now, click on **Save** and then **Deploy** to deploy the API Proxy. After successful deployment, you will find the API Proxy URL.

<br>![](./images/0_25.png)


### 2.3 Create Shopify Webhook on Order Creation

1. Open your Shopify system and create a webhook which calls the API Proxy URL created in the step before. Click **Settings** on the lower left corner, then navigate to **Notifications** and click on Webhooks.

<br>![](./images/0_27.png)

2. Click on **Create webhook**.

<br>![](./images/0_28.png)

Add webhook details:
- Event: order creation
- Format: JSON
- URL: <API proxy created>
- Webhook API version: latest

3. Click on **Save** and you're done!

<br>![](./images/0_30.png)


## 3. Publish an order event to the Event Mesh capability of SAP Integration Suite (read only)

Now that we have configured the webhook, this section describes how to set up an Integration Flow to publish order events to Event Mesh.


<br>![](./images/eventHandling.png)

1. The Integration Flow creates a dynamic topic that includes the order's `last_name` property. To make sure participants only receive events they created, you will use your user ID (e.g., userXX) as the last name when creating the order in Shopify.
2. Each participant creates a message queue to temporarily persist the events until they are delivered to the target application. You will include your user ID in the topic subscription to ensure that your queue only receives order events created by you.
3. Later in the exercise we will create a webhook to deliver the event to SAP Build Process Automation

For now, let's start with the first part, the integration flow publishing the order events.


1.	Login to SAP Integration Suite and create a package: navigate to **Design>Integrations and APIs** and click **Create**.

<br>![](../ex2/images/2_2.png)

2.	Provide details as follows:
- **Name**: Shopify Order Processing
- **Technical Name**: Shopify Order Processing 
- **Short Description**: Package to publish event and replicate shopify orders to S/4HANA system and S/4 HANA order id back to Shopify system.

Click on **Save**.

<br>![](../ex2/images/2_3.png)

3. Go to the **Artifacts** tab, and select **Add>Integration Flow**.

<br>![](../ex2/images/2_4.png)

4. Add Integration Flow details as follows:
- **Name**: Publish Shopify Order to EMIS
- **ID**: Publish_Shopify_Order_to_EMIS
- **Description**: Shopify triggers Integration Flow via Webhook on Order Creation. Integration Flow publishes Order to EMIS.

Click on **Add**.

<br>![](../ex2/images/2_5.png)

5.	Now that you created your integration flow, click **Edit**.

<br>![](../ex2/images/2_6.png)

6. Click on the **Sender** block and then on the **arrow symbol** next to it.

<br>![](../ex2/images/2_7.png)

7. Choose the  **Adapter Type: HTTPS**. 

<br>![](../ex2/images/2_8.png)

After choosing the adapter type, select the **arrow** connecting Sender and Process, and then on the three dots at the bottom of the screen. 

<br>![](../ex2/images/2_9.png)

8. In the **Connection** tab, enter the **Address** as: /in160/shopify/webhook/order. And make sure **CSRF Protected** is **not** selected.

<br>![](../ex2/images/2_10.png)

9. Now click on the **Transformation** icon in the tool bar, and in the drop-down click the option **Script>Groovy Script**. 

<br>![](../ex2/images/2_11.png)

10. You should see an additional step added to the Integration Process as Groovy Script 1. Click on that step, then on the **Processing** tab and the **Select** button.

<br>![](../ex2/images/2_12.png)

11. Here you'll upload a local file, click **Upload from File System** and select the **“Ordermapping.groovy” script file**.

<br>![](../ex2/images/2_13.png)

The script will open. Read it through and click on **Apply**.

<br>![](../ex2/images/2_14.png)

12. Click on the **Transformation** icon and choose **Content Modifier** and place the step in the integration process, next to the Groovy Script you added earlier.

<br>![](../ex2/images/2_15.png)

13. Select the Content Modifier you just added, go to the **Message Header** tab and click on **Add**.

<br>![](../ex2/images/2_16.png)

Add the following content:
- **Name**: content-type
- **Source Type**: Constant
- **Source value**: application/cloudevents+json

<br>![](../ex2/images/2_17.png)

14. Now, go to the **Exchange Property** tab and click **Add**.

<br>![](../ex2/images/2_18.png)

Add details:
- **Name**: topic
- **Source Type**: expression
- **Source Value**: ${property.topic}

<br>![](../ex2/images/2_19.png)

15. Click on **End** (envelope icon) and drag the Arrow to the **Receiver**.

<br>![](../ex2/images/2_20.png)

The list of Adapter Types will appear. Choose the type **AMQP** and then **WebSocket**.

<br>![](../ex2/images/2_21.png)

16. Go to the **Connection** tab and **add the connection details** as follows:
- **Host**: workshop-eu-02a-ff52646b-3869-4db9-bc8c-712e49ed1840.eu10.a.eventmesh.integration.cloud.sap
- **Port**: 443
- **Path**: /protocols/amqp10ws
- **Authentication**: oAuth2ClientCredentials
- **Credential Name**: EMIS(Already created as a pre-requisite)

<br>![](../ex2/images/2_22.png)

17. Navigate to the **Processing** tab: add **Destination Name: ${property.topic}** and make sure **Header Format Handling** is set to **Passthrough**.
    Then click **Save**. And lastly, click **Deploy**, ...

<br>![](../ex2/images/2_23.png)

... select runtime **Cloud Integration** and click **Yes**.

<br>![](../ex2/images/2_24.png)

18. Navigate to **Monitor>Integration and APIs**.

<br>![](../ex2/images/2_25.png)

And then, click **All** in the **Manage Integration Content** section...

<br>![](../ex2/images/2_26.png)

... to view your successfully deployed integration flow in the list of integration content. Well done!

<br>![](../ex2/images/2_27.png)


## Summary

Now that you have an overview of the steps to set up and configure system requirements for the automated order processing scenario, continue to: [Exercise 1 - Configure Event Mesh Queues in SAP Integration Suite](../ex1/README.md).
