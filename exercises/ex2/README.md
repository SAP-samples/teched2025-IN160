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

## Summary

You've now ...

Continue to - [Exercise 3 - Excercise 3 ](../ex3/README.md)
