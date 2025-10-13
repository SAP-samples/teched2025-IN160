# Replicate approved sales order to Shopify and SAP S/4HANA 
In this exercise...

## Create package

1. Login to the SAP Integration Suite system and **create a package**. On the home page, **click** on the three lines on the top left hand side and go to **Integrations and APIs**. Click on **Create package**.

<br>![](/exercises/ex3/images/3_36.png)

2. Provide details as follows:
- **Name**:“Shopify Order Processing User XX” (replace **XX** with your assigned user ID)
- **Technical Name**: “Shopify Order Processing User XX” (replace **XX** with your assigned user ID)
- **Short Description**: Package to publish event and replicate shopify orders to S/4HANA system and S/4 HANA Order ID back to Shopify system.

Click **Save**.

<br>![](/exercises/ex3/images/3_37.png)


**Note**: You can copy the already created integration flow **IN160 - Replicate Sales Order to S4 and Shopify** with your user ID suffixed as name from the [Solution package](https://workshop-eu-02a.integrationsuite-cpi033.cfapps.eu10-005.hana.ondemand.com/shell/design/contentpackage/IN160SolutionPackage?section=ARTIFACTS). Please choose the package you just created while copying.

Alternatively, follow the next steps.


## Create integration flow

1. Go to **Artifacts>Add>Integration flow**. 

<br>![](/exercises/ex3/images/3_38.png)

2. Create a new integration flow with the following details:
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

Enter Resource Path: **A_SalesOrder**.

<br>![](/exercises/ex3/images/3_20.png)

16. Click the **Transformation** icon form  the tool bar, then **Content Modifier**.

<br>![](/exercises/ex3/images/3_21.png)

And place the content modifier in the integration process, between the Tequest Reply and End.

<br>![](/exercises/ex3/images/3_22.png)

17. Click on the **content modifier**. Then navigate to **Exchange Property>Add** to add an exchange property.

<br>![](/exercises/ex3/images/3_23.png)

Add the details to the property as follows:
- **Action**: Create
- **Name**: shopify_order_id
- **SourceType**: XPath
- **Source Value**: //A_SalesOrder/A_SalesOrderType/PurchaseOrderByCustomer
- **Data Type**: java.lang.String

<br>![](/exercises/ex3/images/3_24.png)

18. Add another exchange property with following details:
- **Action**: Create
- **Name**: sales_order_s4hana_id
- **Source Type**: xpath
- **Source Value**: //A_SalesOrder/A_SalesOrderType/SalesOrder
- **DataType**: java.lang.String

<br>![](/exercises/ex3/images/3_25.png)

19. Navigate to the **message body** and select **Expression** as Type.

<br>![](/exercises/ex3/images/3_26.png)

And past the following to **Body**:
```
{
  "query": "mutation updateS4HANACloudOrderID { metafieldsSet( metafields: { key: \"s_4hana_cloud_order_id\", ownerId: \"gid://shopify/Order/${property.shopify_order_id}\", value: \"${property.sales_order_s4hana_id}\", type: \"single_line_text_field\", namespace: \"shopifyaccelerator\" } ) { metafields { id key value } userErrors { code elementIndex field message } } }"
}
```

<br>![](/exercises/ex3/images/3_27.png)

20. Select the **call** icon form the toolbar and choose **external call>request reply**.

<br>![](/exercises/ex3/images/3_28.png)

And place it in integration process.

<br>![](/exercises/ex3/images/3_29.png)

21. Click on the **Request reply** and then the arrow and connect it to the **receiver**. Choose **Shopify adapter** from adapter pop up.

<br>![](/exercises/ex3/images/3_30.png)

Choose **GraphQL**.

<br>![](/exercises/ex3/images/3_31.png)

Go to the **Connection** tab and enter the following details:
- **Shopify base URL**: https://sap-teched-2025.myshopify.com
- **Shopify secure parameter**: IN160_Shopify(Created as a pre-requisite)

<br>![](/exercises/ex3/images/3_32.png)

22. **Save** the integration flow and **deploy** in cloud integration runtime.

<br>![](/exercises/ex3/images/3_33.png)

23. Go to **Monitor>Integration and APIs>Manage integration content>All**.

<br>![](/exercises/ex3/images/3_34.png)

And view the deployed iflow.

<br>![](/exercises/ex3/images/3_35.png)

# Summary

You have...

Continue to: [Excercise 3 - Build a custom approval process in SAP Build](exercises/ex4).













