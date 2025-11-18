# 3. Build a custom approval process in SAP Build 


In the first two sections, you successfully subscribed to order creation events in Shopify and set up your iFlow to post and replicate orders in both SAP S/4HANA and Shopify.
In this part of the Hands-On, we will move on to creating a custom approval process in SAP Build. The goal of this process is to add validation and configuration steps, which will be managed through conditions and human-based approval tasks.
The process in SAP Build will be triggered by the order event configured in the Event Mesh service. Fortunately, Event Mesh and SAP Build integrate natively. This same native integration applies to iFlows created in SAP Integration Suite. This connection is important because, once an order has been approved, the iFlow will be triggered to post and replicate the order in both S/4HANA and Shopify.

<br>![](/exercises/ex4/Images/Scenario.png)

Beyond business applications and workspaces, SAP Build also enables you to create custom process extensions, RPA bots, and automations that help free employees from repetitive tasks. As you will see in this Hands-On, SAP Build offers an intuitive Low-Code/No-Code environment, allowing you to build extensions quickly using simple drag-and-drop functionality.


## 1. Establish the connectivity to the Event Mesh service

1. Navigate to the [SAP Build Lobby](https://workshop-eu-02a.eu10.build.cloud.sap/lobby). The login credentials have been granted by the instructors for this Hands-On session. Within the SAP Build Lobby, **navigate** to the **Events** section.

<br>![](/exercises/ex4/Images/4_1.png)

And then, click **create** to generate a new event specification.

<br>![](/exercises/ex4/Images/4_2.png)

2. To generate event specifications, SAP Build offers you multiple possibilities, which you can explore. For this exercise, select **Upload Specification**.

<br>![](/exercises/ex4/Images/4_3.png)

Next, you'll upload the **JSON file**, which includes the Event specification from the previous exercise. You can also download the file from here (Open it in new tab and download the file):

[Download Final_Event.json](../../resources/Final_Event.json)

If you are unable to download the file you can also navigate to the **resources** folder and search fo the Final_Event.json file - there you can download it as well.


As a result, a new Event is now available in the SAP Build library, which you can reuse for your projects.

<br>![](/exercises/ex4/Images/4_4.png)

Give your event project a **name**, ideally something that is recognizable. Start with **TechED_IN160_WorkshopuserXX** (replace XX with your User ID). Click **Create**.

<br>![](/exercises/ex4/Images/4_5.png)

Congratulations you have now added a new event to SAP Build!

## Create a new project in SAP Build

1. Go back to the SAP Build Lobby front and create a new project by clicking on the **Create** button once gain.

<br>![](/exercises/ex4/Images/4_6.png)

2. SAP Build allows you to generate multiple artifacts, such as Fiori Apps, Mobile Apps, Digital Workspaces and so on. In this tutorial we focus on the capability to generate process extensions and automations. Click on **Automated Process** and select **Next**.

<br>![](/exercises/ex4/Images/4_7.png)

Select **Process** and click on **Next** once again.

<br>![](/exercises/ex4/Images/4_8.png)

3. Give your project a name: **TechED_IN160_WorkshopuserXX_Process** and replace XX with your assigned user ID. After that click **Review**. Review your project details and click on **Create**.

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

You need to provide a **unique name** to your trigger: **TechED_IN160_WorkshopuserXX_Trigger**, replace XX with your assigned user ID. Then click on **Create**.

<br>![](/exercises/ex4/Images/4_14.png)

4. After adding the Event as a trigger you can click on the **Plus-Button** to add an additional process artifact. Here you need to click on **Control & Events.**
   
<br>![](/exercises/ex4/Images/X1.png)

 
Next select **Condition**. This will automatically add a new Condition Step into your process flow, which will occur after the Event-trigger.

<br>![](/exercises/ex4/Images/X2.png)



Click on **Open Condition Editor** to configure the rules of this condition.

<br>![](/exercises/ex4/Images/X3.png)

 
Here you need to specify when the **If-Path** should occur and not the default path. In this case we want to proceed every time your last name is passed on from the Event-Trigger to make sure that we do not continue if any other last name is in the payload of an event. With that we make sure that we only proceed when the order was created via your user. In order to find the last name variable you need to click on **Process Inputs**, select **data**, then **shipping_address** and then select **last_name**. If Process Inputs do not show up then save your project and refresh the page, that should resolve your problem. This step will also avoid that your process is going to proceed if it is triggered by other users from this Hands-On exercise. On the right side please add the **last_name** of the your user in Shopify in this case it should be **userXX**. Then click on **Apply.**

<br>![](/exercises/ex4/Images/X111.png)

 

5. In the If-Path of your condition, click on the **Plus-Button** and select **Approval.** That means we trigger a new Approval Task in your Inbox whenever the events triggers the process.
   
<br>![](/exercises/ex4/Images/X5.png)

 
Select **Blank Approval**, to create a new form from scratch.

<br>![](/exercises/ex4/Images/X6.png)


Give your new form the name **Shopify Order Approval** and click on **Create.**

<br>![](/exercises/ex4/Images/X7.png)

 
Once created you can click on the three dots of your new process step and click on **Open Editor.** That will open the **Forms-Builder,** which allows you to customize your approval form.

<br>![](/exercises/ex4/Images/X8.png)


Now you need to work with the **Forms-Builder** to design the UX for the approval form. Drag&Drop the UI-Elements from the left side into the screen and add the respective names:

•	UI-Element Type: H1 - Label: Order Approval Form

•	UI-Element Type: H2 - Label: Sales Order from Shopify

•	UI-Element Type: Numerical Input - Label: Order Number

•	UI-Element Type: Text Input - Label: Confirmation Number

•	UI-Element Type: Text Input - Label: Currency

•	UI-Element Type: Text Input - Label: E-Mail

•	UI-Element Type: Table - Label: Line-Items


Inside the table UI please configure following values:

o	Material-Name (Text)

o	SKU (Text)

o	Quantity (Number)

o	Price (Text)



Please declare all input fields as **Read Only** – that means that the approver cannot modify any values. At the end it should look like this:



<br>![](/exercises/ex4/Images/X9.png)


To finalize your form you need to go back to the approval form step inside the Process Builder. Under the tab **General** add the subject **Shopify Order Approval.** Under **Recipients** add your username that you have used to login to SAP Build and SAP Integration Suite.

In this case it should look like this:

userXX@techedusers.com

Here you need to exchange XX with your userID.

<br>![](/exercises/ex4/Images/X112.png)



Under the tab **Inputs** make sure that you map all the value from the Event-Trigger to the Approval Form. You can find all the relevant variables under Process-Inputs. For the Line-Items click on the arrow-down button to make sure that you have correctly assigned Name, SKU, Price and Quantity as a **Bind List.**

<br>![](/exercises/ex4/Images/X11.png)

Don't forget to save your project!


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

As a next step, give your project a unique name: **TechEd_IN160_WorkshopuserXX_Action**, replace XX with your assigned user ID. Click on the **Create** button.

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

   Make sure that the CSRF-Token is disabled.

<br>![](/exercises/ex4/Images/4_31.png)

Here are a couple of values, which you can enter, that should trigger the iFlow:

•	order_number: 12129708540288

•	confirmation_number: HZM0CMI4X

•	currency: EUR

•	customer: 

      o	first_name: John
      o	last_name: user00
      o	email: john.doer@sap.com
      
•	shipping_address: 

      o	first_name: John
      o	last_name: user00
      o	address1: Dietmar-Hopp-Allee
      o	city: Walldorf
      o	zip: 69190
      o	country: DE
      
•	line_items:

      o	sku: MZ-TG-B107
      o	quantity: 1
      o	price: 455.00
      o	name: Google Pixel 9a


Click on **Test** to start the API-Call

<br>![](/exercises/ex4/Images/X124.png)

As a result, you should receive the message **201: Created.** Under the tab **Response** you can navigate to the API and click on the Body. This is the output coming from the iFlow in SAP Integration Suite. Click on **Generate Output.**

<br>![](/exercises/ex4/Images/X14.png)

This will make sure, that your new Action will expect and save the Output from Integration Suite as a variable. Click on **Add Output.** You can see the updates now under the tab **Output.**

<br>![](/exercises/ex4/Images/X15.png)

To validate that the iFlow ran succesfully search for the **value** that you have received as a response from Integration Suite. This is the new order ID that has been replicated to the **SAP S/4HANA** system.
   
Then login to the SAP S/4HANA Cloud instance via this link: https://my427029.s4hana.cloud.sap/ui
Login with the credentials that have been granted to you by the instructors. Open the transaction **VA03 – Display Sales Orders.** Once the app has been opened, search for the Order ID that you have copied in the previous step.

<br>![](/exercises/ex4/Images/X36.png)
 

Here you can see that your order has been successfully replicated to **SAP S/4HANA** with all the data from your order that you created initially in Shopify.

<br>![](/exercises/ex4/Images/X37.png)



After checking on the order in S/4, navigate back to the Action Builder. Now after you have tested your Action Project, you can click on **Save** and then on **Release**.

<br>![](/exercises/ex4/Images/4_32.png)

5. As a final step click on **Publish** to release the Action to the SAP Build library.

<br>![](/exercises/ex4/Images/4_33.png)


## Add the Action to your process in SAP Build

1. In this part of the Hands-On, you will now add the newly created Action to your process logic. This will result, that everytime you approve the sales order, the iFlow will be triggered to replicate the order in **SAP S/4HANA** and change the order status in **Shopify.**  For this, you need to get back to your project and open the Process Builder. Under the Approve-Path of the approval step, click on the **Plus-Button** and select **Action.**
   
<br>![](/exercises/ex4/Images/X16.png)

 
Now select the Action that you have created previously, select your project by checking your username in the Project-ID. Click on **Add.**

<br>![](/exercises/ex4/Images/X17.png)


To properly connect to the Integration Suite Runtime destination in our subaccount simply add a new **Destination Variable** to your Action.

<br>![](/exercises/ex4/Images/X18.png)


Add the name **IntegrationSuite** as the identifier and click on the **Create** button.

<br>![](/exercises/ex4/Images/X19.png)


Now, like you did with the approval step, assign all the Process Input Variables to the variables inside the Action as you can see in the screenshot. For **customer** and **shipping_adress** select Single properties. For **Line Items** select Bind List. You can skip the **adress2** variable.

<br>![](/exercises/ex4/Images/X20.png)


2. Now once you have finalized your process in SAP Build click on **Release** on the top-right side to get it ready for deployment.

<br>![](/exercises/ex4/Images/X25.png)

 
Once released, click on the **Deploy** button and select the **public environment.**

<br>![](/exercises/ex4/Images/X26.png)


In the next step you need map your destination variable to the actual destination in the BTP Subaccount. The name of this destination is **RunTimeDestination.** Click on **Deploy** next

<br>![](/exercises/ex4/Images/X27.png)

 



# 4.Test your process:

## 4.1 Create a new order

Create a new order in Shopify, as you already did in exercise 1.2.

## 4.2 Approve Sales Order in SAP Build Process Automation

 Navigate to the **Monitoring** tab of the SAP Build Lobby. In order to simplify your search you can select for your process under the project-filter. Your process should be in the status **Running** because the order has not yet been approved by you.
   
<br>![](/exercises/ex4/Images/X28.png)


By clicking on this process instance you can see all the ongoing steps. Right now your process should be stuck because the order has not yet been approved by you. This means a new task is available in your inbox.

<br>![](/exercises/ex4/Images/X29.png)


To look for your task click on the inbox button on the top-right of the SAP Build Lobby.

<br>![](/exercises/ex4/Images/X30.png)


Here, a new task should be populated with all the details on the order that you have created. Review the data and click on **Approve.**

<br>![](/exercises/ex4/Images/X125.png)

 
Now, go back to the Monitoring application and search again for your projects. Make sure that select the status **Completed,** because your process should have ended.

<br>![](/exercises/ex4/Images/X32.png)


You can see that all the process steps that you have configured int the Build development environment have been completed, this includes the Action Project to trigger the iFlow inside SAP Integration Suite. That means that the iFlow also replicated the order in **SAP S/4HANA** and updated the order in **Shopify.**

<br>![](/exercises/ex4/Images/X34.png)

 

To validate the process completion please reach out to one of the instuctors of the exercise and ask for the admin view of your created order. There you will see that a new ID from the **SAP S/4HANA** system has been added. Please copy that Order ID.
   
<br>![](/exercises/ex4/Images/X126.png)


As an alternative you can also check in the Monitoring of your completed process inside the SAP Build Monitoring application, as you can see in the screenshot below:

<br>![](/exercises/ex4/Images/X130.png)
 

 
Now login to the SAP S/4HANA Cloud instance via this link: https://my427029.s4hana.cloud.sap/ui
Login with the credentials that have been granted to you by the instructors. Open the transaction **VA03 – Display Sales Orders.** Once the app has been opened, search for the Order ID that you have copied in the previous step.

<br>![](/exercises/ex4/Images/X36.png)
 

Here you can see that your order has been successfully replicated to **SAP S/4HANA** with all the data from your order that you created initially in Shopify.

<br>![](/exercises/ex4/Images/X37.png)


# Summary




