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

@RestController
public class CustomerController {
    @GetMapping("/customers/transactions")
    public Map transactions(@RequestParam(value = "id") String id) throws SQLException {
        // Request: http://localhost:8083/customers/transactions?id=String
        Customer customer = new Customer(id);
        Map map = new HashMap<String, List>();
        map.put("transactions",customer.getTransactions());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/customers/transactionspercategory")
    // Request: http://localhost:8083/customers/transactionspercategory?id=String
    public Map transactionsPerCategory (@RequestParam(value = "id") String id) throws SQLException {
        Customer customer = new Customer(id);
        Map map = new HashMap<String, Map>();
        map.put("transactionsPerCategory", customer.getTransactionsPerCategory());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/customers/segment")
    // Request: http://localhost:8083/customers/segment?id=String
    public Map segment(@RequestParam(value = "id") String id)  throws SQLException {
        Customer customer = new Customer(id);
        Map map = new HashMap<String, String>();
        map.put("segment", customer.getSegment());
        Map response = buildResponse(id,map);
        return response;
    }

    @GetMapping("/customers")
    // Request: http://localhost:8083/customers?id=String
    public ResponseEntity<Customer> allData(@RequestParam(value = "id") String id)  throws SQLException{
        Customer customer = new Customer(id);
        customer.getTransactions();
        customer.getTransactionsPerCategory();
        customer.getSegment();
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    private Map buildResponse(String id, Map value){
        Map identity = new HashMap<String, String>();
        identity.put("id",id);
        Map response = new HashMap<String, Map>();
        response.put("customer",identity);
        response.put("values",value);
        return response;
    }
}

