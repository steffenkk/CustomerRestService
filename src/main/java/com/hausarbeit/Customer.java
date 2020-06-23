package com.hausarbeit;

import java.sql.SQLException;
import java.util.*;

public class Customer {
    private String custId;
    private List<Map> transactionsList;
    private Map<String, Integer> transactionsPerCategory;
    private DataConnection db;
    private String segment;


    public Customer(String custId){
        this.custId = custId;
        this.transactionsList = new ArrayList<Map>();
        this.transactionsPerCategory = new HashMap<String, Integer>();
        this.db = new DataConnection(this);
        this.segment = null;
    }

    public void setTransactions(String orderid, String date, String category, String subcategory, String revenue,
                         String quantity, String profit){
        // safe transaction details in a map which will be stored in a list
        // this method will be called from the DataConnection
        Map<String, String> transactions = new HashMap<>();
        transactions.put("orderid", orderid);
        transactions.put("date", date);
        transactions.put("category", category);
        transactions.put("subcategory", subcategory);
        transactions.put("revenue", revenue);
        transactions.put("quantity", quantity);
        transactions.put("profit", profit);
        transactionsList.add(transactions);
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
        // check if transactions are present, if not set them and return
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
        // call datasource for segment
        if (segment == null) {
            db.querySegment();
        }
        return segment;
    }


}
