/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-US/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-US/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


def Message processData(Message message) {
    // Parse the incoming payload
    def jsonSlurper = new JsonSlurper()
    def payload = message.getBody(String)
    def order = jsonSlurper.parseText(payload)
    def userid = order.customer.last_name
    def confirmation_number = order.confirmation_number

    // Extract order number
    def orderNumber = order.id
    def currency = order.currency

    // Extract shipping address
    def shippingAddress = order.shipping_address
    def shippingAddressMap = [
        first_name : shippingAddress.first_name,
        last_name  : shippingAddress.last_name,
        address1   : shippingAddress.address1,
        address2   : shippingAddress.address2,
        city       : shippingAddress.city,
        zip        : shippingAddress.zip,
        country    : shippingAddress.country_code
    ]
    
    //extract customer
    // Extract shipping address
    def customer = order.customer
    def customerMap = [
        first_name : customer.first_name,
        last_name  : customer.last_name,
        email   : customer.email

    ]

    // Extract line items details
    def lineItemsList = []
    order.line_items.each { item ->
        def lineItemMap = [
            sku      : item.sku,
            quantity : item.quantity,
            price    : item.price,
            name     :item.name
        ]
        lineItemsList << lineItemMap
    }
    /*def utcNow = ZonedDateTime.now(ZoneOffset.UTC)
    def isoTime = utcNow.format(DateTimeFormatter.ISO_INSTANT)*/
    
    // Create UTC formatter in ISO8601
def sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
sdf.setTimeZone(TimeZone.getTimeZone("UTC"))

// Format current time
String isoTime = sdf.format(new Date())
    // Prepare final output structure
    def output = [
        type: "order.events.created",
        specversion:"1.0",
        source:"/in160/shopify/order",
        id:orderNumber.toString(),
        //datacontenttype:"application/json",
        time: isoTime,
        data : [
        order_number     : orderNumber,
        confirmation_number: confirmation_number,
        currency         : currency,
        customer: customerMap,
        shipping_address : shippingAddressMap,
        line_items       : lineItemsList
        ]
    ]

    // Convert result to JSON string
    def jsonOutput = JsonOutput.toJson(output)
    message.setBody(jsonOutput)
    message.setProperty("userid", userid);
   // def headers = message.getHeaders();

    // Required CloudEvent headers
    //headers.put("content-type", "application/cloudevents+json");
    //message.setHeaders(headers);
   
     return message

}
