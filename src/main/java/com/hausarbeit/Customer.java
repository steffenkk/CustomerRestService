package com.hausarbeit;

import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.*;

public class Customer {
    private String custId;
    private List<Order> transactionsList;
    private Map<String, Integer> transactionsPerCategory;
    private DataConnection db;
    private String segment;

    public Customer(String custId){
        this.custId = custId;
        this.transactionsList = new ArrayList<Order>();
        this.transactionsPerCategory = new HashMap<String, Integer>();
        this.db = new DataConnection(this);
        this.segment = null;
    }

    public void setTransactions(Order order){
        transactionsList.add(order);
    }

    public void setTransactionsPerCategory(String category, Integer frequency){
        transactionsPerCategory.put(category, frequency);
    }

    public void setSegment(String segment){
        this.segment = segment;
    }

    public String getId(){
        return this.custId;
    }

    public List getTransactions() throws SQLException {
        if (transactionsList.size() == 0) {
            db.queryTransactions();
        }
       return transactionsList;
    }

    public Map getTransactionsPerCategory() throws SQLException {
        if (transactionsPerCategory.size() == 0) {
            db.queryTransactionsPerCategory();
        }
        return transactionsPerCategory;
    }

    public String getSegment() throws SQLException {
        if (segment == null) {
            db.querySegment();
        }
        return segment;
    }


}
