package com.hausarbeit;

import java.util.*;

public class Customer {
    private String custId;
    private List<Map> transactionsList;
    private DataSource db = new DataSource(this);

    public Customer(String custId){
        this.custId = custId;
        this.transactionsList = new ArrayList<Map>();
    }
    void setTransactions(String date, String item, String revenue, String quantity,
                         String discountValue, String discountType, String returnYesNo, String channel){
        // safe transaction details in a map which will be stored in a list
        // this method will be called from the datasource
        Map<String, String> transactions = new HashMap<String, String>();
        transactions.put("date", date);
        transactions.put("item", item);
        transactions.put("revenue", revenue);
        transactions.put("quantity", quantity);
        transactions.put("discountValue", discountValue);
        transactions.put("discountType", discountType);
        transactions.put("return", returnYesNo);
        transactions.put("channel", channel);
        transactionsList.add(transactions);
    }

    List getTransactions(){
        // check if transactions are present, if not set them and return
        if (transactionsList.size() == 0) {
            db.queryTransactions();
        }
       return transactionsList;
    }

    Map getNumbTransactions(){
        if (transactionsList.size() == 0) {
            db.queryTransactions();
        }
        Map<String, Integer> numbTransactions = new HashMap<String, Integer>();
        for (Map transaction: transactionsList ) {
            String item = (String) transaction.get("item");
            // check first if the item of the transaction is already in the numbTransactions Map
            if (numbTransactions.containsKey(item)){
                Integer qty = (Integer) numbTransactions.get(item) + 1;
                numbTransactions.put(item, qty);
            } else {
                numbTransactions.put((String) transaction.get("item"),1);
            }
        }
        return numbTransactions;
    }

    String getSegment(){
        // call datasource
        String segment = db.querySegment();
        return segment;
    }


}
