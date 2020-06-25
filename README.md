# CustomerRestService
A RESTful Webservice, made with springframework, to work as an information provider for CUSTOMER data from a DWH. 

### Getting Started
TODO: Add Information

### General
The RestService provides a convinient, programmatical Method to
retrieve Cusomter Information from the Datawarehouse. To use it,
just call a Get-Method with a valid Customer ID.

The Service runs on Port 8083 - this can be customized in the <i> application.properties</i> file.

### GET-Methods
* /CUSTOMER?id=String
* /CUSTOMER/transactions?id=String
* /CUSTOMER/transactionspercategory?id=String
* /CUSTOMER/segment?id=String

### Sample Responses:

TODO: add Json here