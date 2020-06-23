package com.hausarbeit;

import javax.sql.rowset.*;
import java.sql.*;

public class DataConnection {
    private final Customer customer;

    DataConnection(Customer customer){
        this.customer = customer;
    }

    public void queryTransactions() throws SQLException {
        // query database for the transactions and call setTransactions for the specific customer
        CachedRowSet result = executeQuery(String.format("SELECT t1.customerid, t1.orderid as orderid, t1.orderdate as date, " +
                "t2.category as category, t2.subcategory as subcategory, " +
                "t2.amount as revenue, t2.quantity as qty, t2.profit as profit FROM orders_list t1 " +
                "INNER JOIN order_details t2 " +
                "ON t1.orderid = t2.orderid " +
                "WHERE t1.customerid = '%s';", customer.getId()));  // the %s stands for String param
        while (result.next()) {
            String orderid = result.getString("orderid");
            String date = result.getString("date");
            String category = result.getString("category");
            String subcategory = result.getString("subcategory");
            String revenue = result.getString("revenue");
            String quantity = result.getString("qty");
            String profit = result.getString("profit");
            customer.setTransactions(orderid, date, category, subcategory, revenue, quantity, profit);
        }
    }

    public void querySegment() throws SQLException{
        // query database for the segment and return for the specific customer
        String segment = null;
        CachedRowSet result = executeQuery(String.format("SELECT segment FROM customer WHERE id = '%s';",
                customer.getId()));
        while (result.next()) {
            segment = result.getString("segment");
        }
        customer.setSegment(segment);
    }

    public void queryTransactionsPerCategory() throws SQLException{
        // query db for Transactions per Category
        CachedRowSet result = executeQuery(String.format("select category, count(DISTINCT t1.orderid) as numbOrders from " +
                        "order_details t1 inner join orders_list t2 ON t1.orderid = t2.orderid where t2.customerid = " +
                        "'%s' group by 1;", customer.getId()));
        while (result.next()) {
            customer.setTransactionsPerCategory(result.getString("category"),
                    result.getInt("numborders"));
        }
    }

    private CachedRowSet executeQuery(String sqlQuery) {
        Connection con = null;
        CachedRowSet crset = null;
        // TODO: ADD ENV DB_PW
        String password = System.getenv("DB_PW");
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test1",
                    "postgres", password);
            Statement stmt = con.createStatement(); // statement object for simple queries
            ResultSet rs = stmt.executeQuery(sqlQuery);
            crset = RowSetProvider.newFactory().createCachedRowSet();
            crset.populate(rs); // store data in the cached-rs to scroll through it without an conn
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return crset;
    }

}
