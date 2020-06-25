package com.hausarbeit;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataConnection {
    private final Customer CUSTOMER;

    DataConnection(Customer CUSTOMER){
        this.CUSTOMER = CUSTOMER;
    }

    public void queryTransactions() throws SQLException {
        // query database for the transactions and call setTransactions for the specific CUSTOMER
        CachedRowSet result = executeQuery("SELECT t1.customerid, t1.orderid as orderid, t1.orderdate as date, " +
                "t2.category as category, t2.subcategory as subcategory, " +
                "t2.amount as revenue, t2.quantity as qty, t2.profit as profit FROM orders_list t1 " +
                "INNER JOIN order_details t2 " +
                "ON t1.orderid = t2.orderid " +
                "WHERE t1.customerid = ?;", CUSTOMER.getId());  // the %s stands for String param
        while (result.next()) {
            String orderid = result.getString("orderid");
            String date = result.getString("date");
            String category = result.getString("category");
            String subcategory = result.getString("subcategory");
            String revenue = result.getString("revenue");
            String quantity = result.getString("qty");
            String profit = result.getString("profit");
            CUSTOMER.setTransactions(orderid, date, category, subcategory, revenue, quantity, profit);
        }
    }

    public void querySegment() throws SQLException{
        // query database for the segment and return for the specific CUSTOMER
        String segment = null;
        CachedRowSet result = executeQuery("SELECT segment FROM CUSTOMER WHERE CAST(id as VARCHAR(5)) = ?;",
                CUSTOMER.getId());
        while (result.next()) {
            segment = result.getString("segment");
        }
        CUSTOMER.setSegment(segment);
    }

    public void queryTransactionsPerCategory() throws SQLException{
        // query db for Transactions per Category
        CachedRowSet result = executeQuery("select category, count(DISTINCT t1.orderid) as numbOrders from " +
                        "order_details t1 inner join orders_list t2 ON t1.orderid = t2.orderid where t2.customerid = " +
                        "? group by 1;", CUSTOMER.getId());
        while (result.next()) {
            CUSTOMER.setTransactionsPerCategory(result.getString("category"),
                    result.getInt("numborders"));
        }
    }

    private CachedRowSet executeQuery(String sqlQuery, String id) {
        Connection con = null;
        CachedRowSet crset = null;
        // TODO: ADD ENV DB_PW
        String password = System.getenv("DB_PW");
        try {
            // con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test1", "postgres", password);
            InitialContext initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup("java:comp/env/jdbc/postgres"); //use JNDI
            con = datasource.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(sqlQuery); // prepStatement object for prepared queries
            prepStmt.setString(1, (String) id); // update with the id string (replaces a ?)
            ResultSet rs = prepStmt.executeQuery();
            crset = RowSetProvider.newFactory().createCachedRowSet();
            crset.populate(rs); // store data in the cached-rs to scroll through it without an conn
        } catch (SQLException | NamingException e) {
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
