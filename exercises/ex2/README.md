# Publishing new order event using Event Mesh in SAP Integration Suite

In this exercise, we will publish...

## Create package

You can copy the already created integration flow (iflow) with your user ID suffixed as name from [Solution package](https://workshop-eu-02a.integrationsuite-cpi033.cfapps.eu10-005.hana.ondemand.com/shell/design/contentpackage/IN160SolutionPackage?section=ARTIFACTS).
Alternatively, go through the following steps:

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



## Summary

You've now ...

Continue to - [Exercise 3 - Excercise 3 ](../ex3/README.md)
