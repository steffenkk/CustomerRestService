# CustomerRestService
A RESTful Webservice, made with springframework, to work as an information provider for customer data from a DWH. 

### Getting Started

* Docker: `docker run -p 8083:8083 steffenkk/restservice`

### Architecture

#### UML Class Diagram

![Alt text](./customer.png?raw=true "Class Diagram")

### General
The RestService provides a convinient, programmatical Method to
retrieve Cusomter Information from the Datawarehouse. To use it,
just call a Get-Method with a valid Customer ID.

The Service runs on Port 8083 - this can be customized in the <i> application.properties</i> file.

### GET-Methods
* /customer?id=String
* /customer/transactions?id=String
* /customer/transactionspercategory?id=String
* /customer/segment?id=String

### Sample Responses:

TODO: add Json here

