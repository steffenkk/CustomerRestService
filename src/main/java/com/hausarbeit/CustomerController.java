package com.hausarbeit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //stereotype annotation - this lets spring know that it has to be considered for http-get
public class CustomerController {
    @GetMapping("/customer/transactions")
    public Map transactions(@RequestParam(value = "id") String id) throws SQLException {
        // Request: http://localhost:8083//customer/transactions?id=String
        Customer customer = new Customer(id);
        Map map = new HashMap<String, List>();
        map.put("transactions",customer.getTransactions());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/customer/transactionspercategory")
    // Request: http://localhost:8083/customer/ransactionspervategoryt?id=String
    public Map transactionsPerCategory (@RequestParam(value = "id") String id) throws SQLException {
        Customer customer = new Customer(id);
        Map map = new HashMap<String, Map>();
        map.put("transactionsPerCategory", customer.getTransactionsPerCategory());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/customer/segment")
    // Request: http://localhost:8083//customer/segment?id=String
    public Map segment(@RequestParam(value = "id") String id)  throws SQLException {
        Customer customer = new Customer(id);
        Map map = new HashMap<String, String>();
        map.put("segment", customer.getSegment());
        Map response = buildResponse(id,map);
        return response;
    }

    @GetMapping("/customer")
    // Request: http://localhost:8083/customer?id=String
    public ResponseEntity<Customer> allData(@RequestParam(value = "id") String id)  throws SQLException{
        Customer customer = new Customer(id);
        // add transactions
        customer.getTransactions();
        // add transactions per Category
        customer.getTransactionsPerCategory();
        // ad segment
        customer.getSegment();
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    private Map buildResponse(String id, Map value){
        // builds a response map in a unified matter
        Map identity = new HashMap<String, String>();
        identity.put("id",id);
        Map response = new HashMap<String, Map>();
        response.put("customer",identity);
        response.put("values",value);
        return response;
    }
}

