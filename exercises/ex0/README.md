# Exercise 0

In this exercise, you will get an overview of the pre-requesites needed for the configuration and setup of essencial system requirements for the automated order processing scenario. 
Given the limited time during the workshop, we have completed all steps for you, nevertheless you can go through them to understand the context before moving on to the next exercises and getting your hands dirty.
The overview includes:
- Adapter configuration
- Publish Shopify events in Event Mesh
- Webhook configuration
- Destinations setup
- Adding security keys of Shopify and SAP S/4HANA systems

## 1. Adapter configuration

To get started, the first step is to configure the Shopify adapter in SAP Integration Suite. Please note that we have already deployed the adapter in your tenant as part of this exercise. You can go through the below steps if you would like to deploy a non-SAP adater in your own tenants.

1.	Go to **Discover>Integrations** and search for Shopify. Select the **Shopify Adapter for SAP Integration Suite**.

<br>![](/exercises/ex0/images/0_1.png)

2. In the **Overview** tab you can see the details of the Adapter including a description, ID, supported category, etc. Here, click **Copy** to copy the Adapter into your workspace.

<br>![](/exercises/ex0/images/0_2.png)

3. Navigate back to **Design>Integrations and APIs**,search for Shopify and you should see the copied adapter in your workspace.

<br>![](/exercises/ex0/images/0_3.png)

4. Now, it's time to deploy the Adapter. Click on the Adapter, go to the **Artifacts** tab, click the three dots and select **Deploy** in the menu.

<br>![](/exercises/ex0/images/0_4.png)

5. For the runtime profile, select **Cloud Integration** and click **Yes**. You're done configuring the Shopify Adapter in SAP Integration Suite.

<br>![](/exercises/ex0/images/0_5.png)


## 2. Publishing new order event using Event Mesh in SAP Integration Suite

1.	Login to SAP Integration Suite and create a package: navigate to **Design>Integrations and APIs** and click **Create**.

<br>![](/exercises/ex2/images/2_2.png)

2.	Provide details as follows:
- **Name**: Shopify Order Processing UserXX (where XX is the user ID assign to you for this workshop)
- **Technical Name**: Shopify Order Processing UserXX (where XX is the user ID assign to you for this workshop)
- **Short Description**: Package to publish event and replicate shopify orders to S/4HANA system and S/4 HANA order id back to Shopify system.

Click on **Save**.

<br>![](/exercises/ex2/images/2_3.png)

3. Go to the **Artifacts** tab, and select **Add>Integration Flow**.

<br>![](/exercises/ex2/images/2_4.png)

4. Add Integration Flow details as follows:
- **Name**: Publish Shopify Order to EMIS User XX (replace XX with your user ID)
- **ID**: Publish_Shopify_Order_to_EMIS_User_XX (replace XX with your user ID)
- **Description**: Shopify triggers Integration Flow via Webhook on Order Creation. Integration Flow publishes Order to EMIS.

Click on **Add**.

<br>![](/exercises/ex2/images/2_5.png)

5.	Now that you created your iflow, click **Edit**.

<br>![](/exercises/ex2/images/2_6.png)

6. Click on the **Sender** block and then on the **arrow symbol** next to it.

<br>![](/exercises/ex2/images/2_7.png)

7. Choose the  **Adapter Type: HTTPS**. 

<br>![](/exercises/ex2/images/2_8.png)

After choosing the adapter type, select the **arrow** connecting Sender and Process, and then on the three dots at the bottom of the screen. 

<br>![](/exercises/ex2/images/2_9.png)

8. In the **Connection** tab, enter the **Address** as: /in160/shopify/webhook/order/userXX (replace XX by your assigned user ID). And make sure **CSRF Protected** is **not** selected.

<br>![](/exercises/ex2/images/2_10.png)

9. Now click on the **Transformation** icon in the tool bar, and in the drop-down click the option **Script>Groovy Script**. 

<br>![](/exercises/ex2/images/2_11.png)

10. You should see an additional step added to the Integration Process as Groovy Script 1. Click on that step, then on the **Processing** tab and the **Select** button.

<br>![](/exercises/ex2/images/2_12.png)

11. Here you'll upload a local file, click **Upload from File System** and select the **“Ordermapping.groovy” script file**.

<br>![](/exercises/ex2/images/2_13.png)

The script will open. Read it through and click on **Apply**.

<br>![](/exercises/ex2/images/2_14.png)

12. Click on the **Transformation** icon and choose **Content Modifier** and place the step in the integration process, next to the Groovy Script you added earlier.

<br>![](/exercises/ex2/images/2_15.png)

13. Select the Content Modifier you just added, go to the **Message Header** tab and click on **Add**.

<br>![](/exercises/ex2/images/2_16.png)

Add the following content:
- **Name**: content-type
- **Source Type**: Constant
- **Source value**: application/cloudevents+json

<br>![](/exercises/ex2/images/2_17.png)

14. Now, go to the **Exchange Property** tab and click **Add**.

<br>![](/exercises/ex2/images/2_18.png)

Add details:
- **Name**: topic
- **Source Type**: expression
- **Source Value**: ${property.topic}

<br>![](/exercises/ex2/images/2_19.png)

15. Click on **End** (envelope icon) and drag the Arrow to the **Receiver**.

<br>![](/exercises/ex2/images/2_20.png)

The list of Adapter Types will appear. Choose the type **AMQP** and then **WebSocket**.

<br>![](/exercises/ex2/images/2_21.png)

16. Go to the **Connection** tab and **add the connection details** as follows:
- **Host**: workshop-eu-02a-ff52646b-3869-4db9-bc8c-712e49ed1840.eu10.a.eventmesh.integration.cloud.sap
- **Port**: 443
- **Path**: /protocols/amqp10ws
- **Authentication**: oAuth2ClientCredentials
- **Credential Name**: EMIS(Already created as a pre-requisite)

<br>![](/exercises/ex2/images/2_22.png)

17. Navigate to the **Processing** tab: add **Destination Name: ${property.topic}** and make sure **Header Format HAndling** is set to **Passthrough**.
    Then click **Save**. And lastly, click **Deploy**,...

<br>![](/exercises/ex2/images/2_23.png)

... select runtime **Cloud Integration** and click **Yes**.

<br>![](/exercises/ex2/images/2_24.png)

18. Navigate to **Monitor>Integration and APIs**.

<br>![](/exercises/ex2/images/2_25.png)

And then, click **All** in the **Manage Integration Content** section...

<br>![](/exercises/ex2/images/2_26.png)

... to view your successfully deployed integration flow in the list of integration content. Well done!

<br>![](/exercises/ex2/images/2_27.png)


## 3. Webhook configuration

The configuration of the Webhook is necessary to...
Again, we have already configured the webhook as part of this exercise. Refer the IN160 solution package to refer the iflow. And you can go through below steps for your understanding.

1.	First, you'll create an **API provider** to the iflow deployed to cloud integration, as shown in the screenshot below. Go to **Configure>APIs>API Providers** and click **Create**.

<br>![](/exercises/ex0/images/0_7.png)

2. As the name of the API Provider, add **CloudIntegration**.

<br>![](/exercises/ex0/images/0_8.png)

3. Now, go to the **Connection** tab and provide the nexessary connection details. Add **Client ID** and **Client Secret** from the Process Integration runtime service instance. Please refer to the [help documentation](https://help.sap.com/docs/integration-suite/sap-integration-suite/create-api-provider?locale=en-US) for the setup details.
   Click **Save**.

<br>![](/exercises/ex0/images/0_9.png)

4. Create an API proxy using the API Provider. Select **API Provider** and add the following information:
**Name**: ShopifyWebhook, **URL**: /http/in160/shopify/webhook/order/user00, and further details as shown in the image below. Then click **Create**.
      
<br>![](/exercises/ex0/images/0_100.png)

5. Next, you'll define policies for API proxy. Click on **ShopifyWebhook**, then on the three dots and **Policies**.

<br>![](/exercises/ex0/images/0_11.png)

6. Define proxies in the **PreFlow**. Add **Key Value Map Operations** by clicking on **+** symbol next to it.

<br>![](/exercises/ex0/images/0_12.png)

7. In the dialog, you'll define the Key Value Map Operations policy. For that, provide the **Policy Name** as **GetShopifyKey** and click on **Add**.

<br>![](/exercises/ex0/images/0_13.png)

8. Click on **GetShopifyKey** and update the script as follows:

<br>![](/exercises/ex0/images/0_14.png)


Add the script for **Shopify Key** as follows:
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


<br>![](/exercises/ex0/images/0_15.png)

9. Add extension policy, click on **Python Script**.
    
<br>![](/exercises/ex0/images/0_16.png)

Provide the **Policy Name: checkHash** and click **Add**.

<br>![](/exercises/ex0/images/0_17.png)

The Policy editor will appear as follows:

<br>![](/exercises/ex0/images/0_18.png)

10. Next, add the **checkHash** python script by clicking on new script

<br>![](/exercises/ex0/images/0_19.png)

Then, provide details as follows:
- Script name: validate
- Script type: python
- Script: create
And click **Add**.

<br>![](/exercises/ex0/images/0_20.png)


11. Provide **Script Resource** by pasting the following in the section:
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
<br>![](/exercises/ex0/images/0_21_2.png)


12. Go to **ProxyEndpoint>PreFlow**, click on **checkHash** policy, point the resource in the xml to the python script created earlier.

<br>![](/exercises/ex0/images/0_23.png)

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

13. Now, click on **Save** and then **Deploy** to deploy the API Proxy.

<br>![](/exercises/ex0/images/0_25.png)

14. Go to the Shopify system and add the reference to the webhook proxy just deployed. Click **Settings** on the lower left corner, then navigate to **Notifications** and click on Webhooks.

<br>![](/exercises/ex0/images/0_27.png)

15. Click on **Create webhook**.

<br>![](/exercises/ex0/images/0_28.png)

Add webhook details:
- Event: order creation
- Format: JSON
- URL: <API proxy created>
- Webhook API version: latest

Click on **Save** and you're done!

<br>![](/exercises/ex0/images/0_30.png)


## Summary

Now that you have ... 
Continue to - [Exercise 1 - Exercise 1 Description](../ex1/README.md)


