package com.hausarbeit;

import java.sql.*;

public class DataConnection {
    private Customer customer;

    DataConnection(Customer customer){
        this.customer = customer;
    }
    void queryTransactions(){
        // query database for the transactions and call setTransactions for the specific customer
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test1",
                    "postgres", "keule");

            String query = "select id, info::VARCHAR from orders;";
            Statement stmt = con.createStatement(); // statement objekt fuer einfache abfragen
            ResultSet rs = stmt.executeQuery( query );
            while ( rs.next() )
                System.out.printf( "%s, %s", rs.getInt(1),
                        rs.getString(2) );
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            if ( con != null )
                try { con.close(); } catch ( SQLException e ) { e.printStackTrace(); }
        }
        customer.setTransactions("2020-01-07", "cl", "100", "5",
                "0.0", "None", "No", "online");
        customer.setTransactions("2020-01-09", "gl", "250", "1",
                "0.0", "None", "No", "branch");
        customer.setTransactions("2020-01-20", "gl", "100", "1",
                "5.0", "None", "No", "branch");
    }

    String querySegment(){
        // query database for the segment and return for the specific customer
        return "ABC";
    }

}
