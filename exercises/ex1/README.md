# Configure Event Mesh Queues in SAP Integration Suite 

In this exercise, we will create...

## Configuration

In the interest of time we have already configured the message client for you. To understand how this step is done, review the [help documentation](https://help.sap.com/docs/integration-suite/sap-integration-suite/configure-message-client?locale=en-US).

1.	In SAP Integration Suite, navigate to **Configure>Event Mesh**.

<br>![](/exercises/ex1/images/1_1.png)

2. Click on **Message Client: IN160**.

<br>![](/exercises/ex1/images/1_2.png)

4. Navigate to the **Queues** tab and click on **Create**.

<br>![](/exercises/ex1/images/1_3.png)

5. In the dialog, provide the name as **UserXX**, where **XX** is the ID assign to you for the workshop. And click **Create**.

<br>![](/exercises/ex1/images/1_4.png)

6. Go to the **Webhook Subscriptions** tab and click on **Create**. 

<br>![](/exercises/ex1/images/1_5.png)

Provide webhook detailsas follows:

- Name: userXX (Replace XX by the user ID provided during workshop)
- Queue name: choose the quq that you just created
- Webhook URL: https://spa-api-gateway-bpi-eu-dev.cfapps.sap.hana.ondemand.com/internal/be/v1/events
- Authentication: oAuth2 Client Credentials
- Client ID: <Will be provided during hands on>
- Client Secret: <Will be provided during hands on>
- Token URL:< Will be provided during hands on>

And click **Create**.

<br>![](/exercises/ex1/images/1_6.png)


# Summary

You've now ...

Continue to - [Exercise 2 - Replicate approved sales order to Shopify and SAP S/4HANA](../ex2/README.md).

