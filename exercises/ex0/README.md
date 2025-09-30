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





## Summary

Now that you have ... 
Continue to - [Exercise 1 - Exercise 1 Description](../ex1/README.md)
