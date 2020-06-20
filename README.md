# CustomerRestService
A RESTful Webservice, made with springframework, to work as an information provider for customer data from a DWH. 

### Getting Started
TODO: Add Information

### General
The RestService provides a convinient, programmatical Method to
retrieve Cusomter Information from the Datawarehouse. To use it,
just call a Get-Method with a valid Customer ID.

The Service runs on Port 8083 - <i>this can be customized in the application.properties file</i>.

### GET-Methods
* /Transactions?id=String
* /TransactionsPerSegment?id=String
* /Segment?id=String

### Sample Responses:

TODO: add Json here