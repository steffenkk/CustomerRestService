package com.hausarbeit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController //stereotype annotation - this lets spring know that it has to be considered,
// when handling incomming web requests
public class CustomerController {
    private static final String template = "Customer: %s!";
    private final AtomicLong counter = new AtomicLong();
    // TODO: change DataSource to interact with a real db
    @GetMapping("/Transactions") // Map Http-Get with the Transactions param
    public Map transactions(@RequestParam(value = "id") String id){
        // Request: http://localhost:8080/Transactions?id=String
        Customer customer = new Customer(id);
        Map map = new HashMap<String, List>();
        map.put("transactions",customer.getTransactions());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/TransactionsPerSegment")
    // Request: http://localhost:8080/TransactionsPerSegment?id=String
    public Map transactionsPerSegment(@RequestParam(value = "id") String id){
        Customer customer = new Customer(id);
        Map map = new HashMap<String, Map>();
        map.put("TransactionsPerSegment", customer.getNumbTransactions());
        Map response = buildResponse(id,map);
        return response;
    }
    @GetMapping("/Segment")
    // Request: http://localhost:8080/Segment?id=String
    public Map segment(@RequestParam(value = "id") String id){
        Customer customer = new Customer(id);
        Map map = new HashMap<String, String>();
        map.put("segment", customer.getSegment());
        Map response = buildResponse(id,map);
        return response;
    }

    public Map buildResponse(String id, Map value){
        Map identity = new HashMap<String, String>();
        identity.put("id",id);

        Map response = new HashMap<String, Map>();
        response.put("customer",identity);
        response.put("values",value);
        return response;
    }
}

