# Build a custom approval process in SAP Build 
In this exercise...

## 1. Establish the connectivity to the Event Mesh service

1. Navigate to the [SAP Build Lobby](https://workshop-eu-02a.eu10.build.cloud.sap/lobby). The login credentials have been granted by the instructors for this Hands-On session. Within the SAP Build Lobby, **navigate** to the **Events** section.

<br>![](/exercises/ex4/Images/4_1.png)

And then, click **create** to generate a new event specification.

<br>![](/exercises/ex4/Images/4_2.png)

2. To generate event specifications, SAP Build offers you multiple possibilities, which you can explore. For this exercise, select **Upload Specification**.

<br>![](/exercises/ex4/Images/4_3.png)

Next, you'll upload the **JSON file**, which was previously downloaded in exercise 1 where you worked with the Event Mesh service. As a result, a new Event is now available in the SAP Build library, which you can reuse for your projects.

<br>![](/exercises/ex4/Images/4_4.png)

Give your event project a **name**, ideally something that is recognizable. Start with **TechED_IN160_userXX** (replace XX with your User ID). Click **Create**.

<br>![](/exercises/ex4/Images/4_5.png)

Congratulations you have now added a new event to SAP Build!

## Create a new project in SAP Build

1. Go back to the SAP Build Lobby front and create a new project by clicking on the **Create** button once gain.

<br>![](/exercises/ex4/Images/4_6.png)

2. SAP Build allows you to generate multiple artifacts, such as Fiori Apps, Mobile Apps, Digital Workspaces and so on. In this tutorial we focus on the capability to generate process extensions and automations. Click on **Automated Process** and select **Next**.

<br>![](/exercises/ex4/Images/4_7.png)

Select **Process** and click on **Next** once again.

<br>![](/exercises/ex4/Images/4_8.png)

3. Give your project a name: **TechED_IN160_userXX_Process** and replace XX with your assigned user ID. After that click **Review**. Review your project details and click on **Create**.

<br>![](/exercises/ex4/Images/4_9.png)

Well done! You now have an new project created in SAP Build.

## Modify your process in SAP Build

1. After your project has been created, a new window pops up which forwards you to our Low-Code/No-Code development environment of SAP Build, in this case the Process Automation capability. Give your new process a name (**TechEd Approval Workflow**) and click on **Create**.

<br>![](/exercises/ex4/Images/4_10.png)

2. The first step that you need to do in your process is to **select** the trigger. SAP Build offers you many different mechanisms, like Form-based trigger, Scheduling or API-based triggers.
In our case we want to select the **event**, which we have generated previously as the trigger of a new process instance. That means every time a customer generates a new order in Shopify we trigger our custom approval workflow. Click **Add a trigger**. 

<br>![](/exercises/ex4/Images/4_11.png)

Then, select for **Wait for an Event**.

<br>![](/exercises/ex4/Images/4_12.png)

3. Here you need to select the event, which you have generated previously. Under the event tile you can find the **Project Name**, where you have added your User ID. Click on the **Add** button next to your event.

<br>![](/exercises/ex4/Images/4_13.png)

You need to provide a **unique name** to your trigger: **TechED_IN160_userXX_Trigger**, replace XX with your assigned user ID. Then click on **Create**.

<br>![](/exercises/ex4/Images/4_14.png)

4. Now you have updated your process flow. After a process instance is initiated by an event, you can define multiple activities and tasks which need to be orchestrated.
In our scenario, we start with a simple approval form. Click on the **Plus-Button** within the Process Builder and select **Approval**.

<br>![](/exercises/ex4/Images/4_15.png)

5. Afterwards, you have the option to either create a form by using the graphical layout designer, or you can simple use the new GenAI feature, which allows you to establish artifacts with a simple language promt. Please **copy** the following prompt into the tool:

_Add an approval form with the name ,,Shopify Order Approval" and add a header at the beginning with the title ,,Please approve this order”. Add a numeric field called Order Number. Add text fields which are Currency, Confirmation Number, Customer First Name, Customer Last Name, Customer E-Mail, and a table for line-items. All values should be read only._

Then, you'll **submit** your prompt to SAP Build by clicking the **Send** button, and wait a couple of seconds for the form to be generated.

<br>![](/exercises/ex4/Images/4_16.png)

6. Now go back to the Process Builder and click on the three dots of the Shopify order Approval Form and select **Open Editor**.

<br>![](/exercises/ex4/images/4_17.png)

7. In the Line-Item table we can now add additional variables, that should be displayed to the approver. The data is coming directly form the Event Mesh service.
Add the following variables into the table view:
- **Number**: Text Input
- **SKU**: Text Input
- **Quantity**: Numeric Input
- **Price**: Text Input

All items should be declared as **Read Only**. If your result looks like the screenshot below, you can click on **Save**.

<br>![](/exercises/ex4/Images/4_18.png)

8. After you have saved you Form, please go back to the Process Builder. There you can see that the Approval form is still _red-flagged_. In order to avoid it, you need to **assign** the Subject and Recipients in the tab **General**.
- **Subject**: Shopify Order Approval
- **Recipients**: Your UserID

<br>![](/exercises/ex4/Images/4_19.png)

9. As a next step, navigate to the tab **Input**. Here, you'll map the data vriables, which have been configured by the Event specification at the beginning to the labels of the approval form application:
Map:
-	Currency to Currency
-	Customer E-Mail to Customer E-Mail
-	Customer First name to Customer First Name
-	Customer Last Name to Customer Last Name
-	Line Items as a binded List to Line Items
-	Order Number to Order Number

Once you have mapped all the variables you can proceed **saving** your project.

<br>![](/exercises/ex4/Images/4_20.png)

## Establish a connectivity from your process in SAP Build to the iFlow in SAP Integration Suite.

1. Navigate back to the SAP Build Lobby and select the section **Actions** on the left-hand side.

<br>![](/exercises/ex4/Images/4_21.png)

2. In the Actions Builder, you'll establish a new Action from scratch. Click on the **Create** button.

<br>![](/exercises/ex4/Images/4_22.png)

SAP Build gives you many options to create an Action, which results in a connectivity to either a cloud system or an on-Premise application. In our scenario we want to take advantage of the **native connectivity** we have to SAP Integration Suite. Click on **Integration Suite** as the API source.

<br>![](/exercises/ex4/Images/4_23.png)

Navigate to **Integration Flow**.

<br>![](/exercises/ex4/Images/4_24.png)

Select the **iFlow Project**, which you have established in exercise 2.

<br>![](/exercises/ex4/Images/4_25.png)

Select the respective project which allows you to replicate sales order data to S/4HANA and Shopify. Check for your **username**, which is included in the **project ID** of the iFlow.

<br>![](/exercises/ex4/Images/4_26.png)

Afterwards click on **Create New Project**.

<br>![](/exercises/ex4/Images/4_27.png)

As a next step, give your project a unique name: **TechEd_IN160_userXX_Action**, replace XX with your assigned user ID. Click on the **Create** button.

<br>![](/exercises/ex4/Images/4_28.png)


3. Done! You have now established a new **Action**. To make sure that SAP Build shoots the right payload to Integration Suite, we need to add the **input parameter**. Here we use a simple trick to add the input variables buy copy and pasting the Payload from exercise 2. 

Go to the **Input** tab, select **Add** and click on **Fields from Sample JSON**.

<br>![](/exercises/ex4/Images/4_29.png)

Now add this payload to the editor: 

```
{
    "order_number": 1005,
    "confirmation_number": "KYZI4QQVN",
    "currency": "EUR",
    "customer": {
        "first_name": "John",
        "last_name": "user00",
        "email": "john.doer@example.com"
    },
    "shipping_address": {
        "first_name": "John",
        "last_name": "Doe",
        "address1": "Dietmar-Hopp-Allee",
        "address2": null,
        "city": "Walldorf",
        "zip": "69190",
        "country": "Germany"
    },
    "line_items": [
        {
            "sku": "sku-managed-1",
            "quantity": 1,
            "price": "629.95",
            "name": "The Multi-managed Snowboard"
        },
        {
            "sku": "sku-managed-2",
            "quantity": 2,
            "price": "635.95",
            "name": "The Multi-managed Snowboard"
        }
    ]
}
```

This is what it should look like as a result. Click on **Next** to proceed and click on **Add** afterwards.

<br>![](/exercises/ex4/Images/4_30.png)

4. If you like, you can also test your connectivity, by navigating to the **Test** tab. In order to properly shoot the API-Call you need to select the following Destination: **RunTimeDestination**.

<br>![](/exercises/ex4/Images/4_31.png)

Now after you have tested your Action Project, you can click on **Save** and then on **Release**.

<br>![](/exercises/ex4/Images/4_32.png)

5. As a final step click on **Publish** to release the Action to the SAP Build library.

<br>![](/exercises/ex4/Images/4_33.png)


## Add the Action to your process in SAP Build

tbd





