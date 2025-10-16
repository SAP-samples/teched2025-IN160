# 1. Configure Event Mesh Queues in SAP Integration Suite 

In this exercise, you'll configure Event Mesh queues in SAP Integration Suite and create a conenction to the webhook. Queues are endpoints configured with topic subscriptions that can publish and consume messages.

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

Provide webhook details as follows (remember to remove spaces while copy-pasting):

- **Name**: userXX (Replace **XX** by the user ID provided during workshop)
- **Queue name**: choose the queue that you just created
- **Webhook URL**: https://spa-api-gateway-bpi-eu-prod.cfapps.eu10.hana.ondemand.com/internal/be/v1/events
- **Authentication**: oAuth2 Client Credentials
- **Client ID**: sb-899a0150-8fc6-4b79-948e-9683254f2c26!b583795|xsuaa!b120249
- **Client Secret**: 5d84187a-1728-4c27-ae6b-1c62a6fe22b8$Y5GJG7LMK0k2y4goFdMf7M4C62as6ry9rxF0HUVTK0g=
- **Token URL**: https://workshop-eu-02a.authentication.eu10.hana.ondemand.com/oauth/token

And click **Create**.

<br>![](/exercises/ex1/images/1_6.png)


# Summary

You've now configure queues in Event Mesh and associated them to the webhook, created a connection. These steps will allow the order event to trigger the process in SAP Build later on.

Now, continue to: [Exercise 2 - Replicate approved sales order to Shopify and SAP S/4HANA](../ex3/README.md).

