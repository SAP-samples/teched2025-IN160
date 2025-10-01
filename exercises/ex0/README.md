# Exercise 0

In this exercise, you will get an overview of the pre-requesites needed for the configuration and setup of essencial system requirements for the automated order processing scenario. 
Given the limited time during the workshop, we have completed all steps for you, nevertheless you can go through them to understand the context before moving on to the next exercises and getting your hands dirty.
The overview includes:
- Adapter configuration
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

## 2. Webhook configuration

The configuration of the Webhook is necessary to...
Again, we have already configured the webhook as part of this exercise. Refer the IN160 solution package to refer the iflow. And you can go through below steps for your understanding.

1. A dummy integration flow (iflow) exposing the URL  is created in the **Solution Package IN160**.

<br>![](/exercises/ex0/images/0_6.png)

2.	Now, you need to create an **API provider** to the iflow deployed to cloud integration, as shown in the screenshot below. Go to **Configure>APIs>API Providers** and click **Create**.

<br>![](/exercises/ex0/images/0_7.png)

3. As the name of the API Provider, add **CloudIntegration**.

<br>![](/exercises/ex0/images/0_8.png)

4. Now, go to the **Connection** tab and provide the nexessary connection details. Add **Client ID** and **Client Secret** from the Process Integration runtime service instance. Please refer to the [help documentation](https://help.sap.com/docs/integration-suite/sap-integration-suite/create-api-provider?locale=en-US) for the setup details.
   Click **Save**.

<br>![](/exercises/ex0/images/0_9.png)

5. Create an API proxy using the API Provider. Select **API Provider** and add the name **ShopifyWebhook** and further details as shown in the image below. Then click **Create**.
      
<br>![](/exercises/ex0/images/0_10.png)

6. Next, you'll define policies for API proxy. Click on **ShopifyWebhook**, then on the three dots and **Policies**.

<br>![](/exercises/ex0/images/0_11.png)

7. Define proxies in the **PreFlow**. Add **Key Value Map Operations** by clicking on **+** symbol next to it.

<br>![](/exercises/ex0/images/0_12.png)

8. In the dialog, you'll define the Key Value Map Operations policy. For that, provide the **Policy Name** as **GetShopifyKey** and click on **Add**.

<br>![](/exercises/ex0/images/0_13.png)

9. Click on **GetShopifyKey** and update the script as follows:

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

10. Add extension policy, click on **Python Script**.
    
<br>![](/exercises/ex0/images/0_16.png)

Provide the **Policy Name: checkHash** and click **Add**.

<br>![](/exercises/ex0/images/0_17.png)

The Policy editor will appear as follows:

<br>![](/exercises/ex0/images/0_18.png)

11. Next, add the **checkHash** python script by clicking on new script

<br>![](/exercises/ex0/images/0_19.png)

Then, provide details as follows:
- Script name: validate
- Script type: python
- Script: create
And click **Add**.

<br>![](/exercises/ex0/images/0_20.png)


12. Provide **Script Resource** by pasting the following in the section:
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


13. Go to **ProxyEndpoint>PreFlow**, click on **checkHash** policy, point the resource in the xml to the python script created earlier.

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

14. Now, click on **Save** and then **Deploy** to deploy the API Proxy.

<br>![](/exercises/ex0/images/0_25.png)

15. Go to the Shopify system and add the reference to the webhook proxy just deployed. Click **Settings** on the lower left corner, then navigate to **Notifications** and click on Webhooks.

<br>![](/exercises/ex0/images/0_27.png)

16. Click on **Create webhook**.

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
