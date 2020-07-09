package com.hausarbeit;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class DataConnection {
    // TODO: should the class be static or should the Customer not be static?
    // TODO: remenber to store the db PW in the env
    private final Customer customer;

    DataConnection(Customer customer){
        this.customer = customer;
    }

    public void queryTransactions() throws SQLException {
        // query database for the transactions and call setTransactions for the specific customer
        CachedRowSet result = executeQuery("SELECT t1.customerid, t1.orderid as orderid, t1.orderdate as date, " +
                "t2.category as category, t2.subcategory as subcategory, " +
                "t2.amount as revenue, t2.quantity as qty, t2.profit as profit FROM orders_list t1 " +
                "INNER JOIN order_details t2 " +
                "ON t1.orderid = t2.orderid " +
                "WHERE t1.customerid = ?;", customer.getId());  // the %s stands for String param
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
        CachedRowSet result = executeQuery("SELECT segment FROM customer WHERE CAST(id as VARCHAR(5)) = ?;",
                customer.getId());
        while (result.next()) {
            segment = result.getString("segment");
        }
        customer.setSegment(segment);
    }

    public void queryTransactionsPerCategory() throws SQLException{
        // query database for the queryTransactionsPerCategory and call setTransactionsPerCategory for the specific customer
        CachedRowSet result = executeQuery("select category, count(DISTINCT t1.orderid) as numbOrders from " +
                        "order_details t1 inner join orders_list t2 ON t1.orderid = t2.orderid where t2.customerid = " +
                        "? group by 1;", customer.getId());
        while (result.next()) {
            customer.setTransactionsPerCategory(result.getString("category"),
                    result.getInt("numborders"));
        }
    }

    private CachedRowSet executeQuery(String sqlQuery, String id) {
        Connection con = null;
        CachedRowSet crset = null;
        String password = System.getenv("POSTGRES_PASSWORD");
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test1", "postgres", password);
            PreparedStatement prepStmt = con.prepareStatement(sqlQuery);
            prepStmt.setString(1, (String) id);
            ResultSet rs = prepStmt.executeQuery();
            crset = RowSetProvider.newFactory().createCachedRowSet();
            crset.populate(rs);
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
