package com.hausarbeit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //stereotype annotation - this lets spring know that it has to be considered for http-get
public class CustomerController {
    @GetMapping("/customer/transactions")
    public Map transactions(@RequestParam(value = "id") String id) throws SQLException {
        // Request: http://localhost:8080/Transactions?id=String
        Customer customer = new Customer(id);
        Map map = new HashMap<String, List>();
        map.put("transactions",customer.getTransactions());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/customer/transactionspercategory")
    // Request: http://localhost:8080/TransactionsPerCategoryt?id=String
    public Map transactionsPerCategory (@RequestParam(value = "id") String id) throws SQLException {
        Customer customer = new Customer(id);
        Map map = new HashMap<String, Map>();
        map.put("transactionsPerCategory", customer.getTransactionsPerCategory());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/customer/segment")
    // Request: http://localhost:8080/Segment?id=String
    public Map segment(@RequestParam(value = "id") String id)  throws SQLException {
        Customer customer = new Customer(id);
        Map map = new HashMap<String, String>();
        map.put("segment", customer.getSegment());
        Map response = buildResponse(id,map);
        return response;
    }

    @GetMapping("/customer")
    // Request: http://localhost:8080/All?id=String
    public List allData(@RequestParam(value = "id") String id)  throws SQLException{
        Customer customer = new Customer(id);
        ArrayList<Map> responseList = new ArrayList<>();
        // add transactions
        Map tranactions = new HashMap<String, List>();
        tranactions.put("transactions",customer.getTransactions());
        responseList.add(buildResponse(customer.getId(), tranactions));
        // add transactions per Category
        Map tranactionsPerCategory = new HashMap<String, Map>();
        tranactionsPerCategory.put("transactionsPerCategory", customer.getTransactionsPerCategory());
        responseList.add(buildResponse(customer.getId(), tranactionsPerCategory));
        // ad segment
        Map segment = new HashMap<String, String>();
        segment.put("segment", customer.getSegment());
        responseList.add(buildResponse(customer.getId(), segment));
        return responseList;
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

