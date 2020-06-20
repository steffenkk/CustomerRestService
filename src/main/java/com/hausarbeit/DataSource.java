package com.hausarbeit;

public class DataSource {
    private Customer customer;

    DataSource(Customer customer){
        this.customer = customer;
    }
    void queryTransactions(){
        // query database for the transactions and call setTransactions for the specific customer
        customer.setTransactions("2020-01-07", "cl", "100", "5",
                "0.0", "None", "No", "online");
        customer.setTransactions("2020-01-09", "gl", "250", "1",
                "0.0", "None", "No", "branch");
        customer.setTransactions("2020-01-20", "gl", "100", "1",
                "5.0", "None", "No", "branch");
    }

    String querySegment(){
        // query database for the segment and return for the specific customer
        String segment = "ABC";
        return segment;
    }

}
