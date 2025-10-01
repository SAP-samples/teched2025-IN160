# Replicate approved sales order to Shopify and SAP S/4HANA 
In this exercise...

## Create integration flow
You can copy the already created integration flow **IN160 - Replicate Sales Order to S4 and Shopify** with your user ID suffixed as name from the [Solution package](https://workshop-eu-02a.integrationsuite-cpi033.cfapps.eu10-005.hana.ondemand.com/shell/design/contentpackage/IN160SolutionPackage?section=ARTIFACTS).

Alternatively, follow the steps below:
1.	Create a new integration flow with the following details:
- **Name**: Replicate Sales order to S4 and Shopify UserXX (where XX should be replaced by the user ID provided to you for the workshop)
- **Description**: Replicate approved Sales order from SAP Build to S/4HANA system. Further,replicate the generated S/4 ID to Shopify.

<br>![](/exercises/ex3/images/3_1.png)

2.	Click on **Sender** and drag the Arrow to the Start icon.

<br>![](/exercises/ex3/images/3_2.png)

3. Choose **HTTPS** adapter from the Adapter list.

<br>![](/exercises/ex3/images/3_3.png)

4. Int the **Connection** tab, provide the connection details:
- **Address**: /shopify/order/userXX (replace XX with your assigned user ID)
- Make sure the **CSRF Protected** is **not** selected.

<br>![](/exercises/ex3/images/3_4.png)

5. Click outside of the integration process box. Navigate to the **References** tab and select **Add>Mapping>Message Mapping**.

<br>![](/exercises/ex3/images/3_5.png)

6. Upload the message mapping file saved in your system by clicking on **Browse**.

<br>![](/exercises/ex3/images/3_5.png)

The mapping files will get uploaded as follows:

<br>![](/exercises/ex3/images/3_7.png)

7. Click on the **Mapping** icon in the tool bar and then choose **Message Mapping**.

<br>![](/exercises/ex3/images/3_8.png)

8. Go to the **Processing** tab and click the **Select** button.

<br>![](/exercises/ex3/images/3_9.png)

9. in **Local Resources** select the message mapping file **shopify_to_s4hana_order_mapping_mmap** and then click **Ok**.

<br>![](/exercises/ex3/images/3_10.png)

10. You'll see Mapping defined for you as follows:

<br>![](/exercises/ex3/images/3_11.png)

Click **Ok**.

11. Now, navigate to the toolbar and select the **External call** button (double arrows),and **Request Reply**.

<br>![](/exercises/ex3/images/3_12.png)

And place the **Request Reply** box in the integration flow:

<br>![](/exercises/ex3/images/3_13.png)

12. Go to the **Participants** toolbar icon and select **Receiver**.

<br>![](/exercises/ex3/images/3_14.png)

And place the **Receiver** box below the integration process. Then, click on **Request reply**, and on **Connector** (round arrow icon).

<br>![](/exercises/ex3/images/3_15.png)

13. Choose **OData** as the Adapter Type. And **OData V2** as Message Protocol (on the next dialog). 

<br>![](/exercises/ex3/images/3_16.png)

14. Go to **Connection** tab add connection details as follows:
- **Address**: https://my427029-api.s4hana.cloud.sap/sap/opu/odata/sap/API_SALES_ORDER_SRV
- **Authentication**: Basic
- **Credential Name**: IN160_S4

<br>![](/exercises/ex3/images/3_17.png)

15. Go to **Processing**, and select **Operation details: Create(POST)**.

<br>![](/exercises/ex3/images/3_19.png)

Enter **Resource Path: A_Sales Order**.

<br>![](/exercises/ex3/images/3_20.png)













