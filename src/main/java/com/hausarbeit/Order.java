package com.hausarbeit;

public class Order {
    private String id;
    private String date;
    private String category;
    private String subcategory;
    private double revenue;
    private int quantity;
    private double profit;

    public Order(String id, String date, String category, String subcategory, double revenue,
                 int quantity, double profit){
        this.id = id;
        this.date = date;
        this.category = category;
        this.subcategory = subcategory;
        this.revenue=revenue;
        this.quantity = quantity;
        this.profit = profit;
    }

    // in order to parse the object as json we need to implement all getters
    public String getDate(){
        return this.date;
    }
    public String getId(){
        return this.id;
    }
    public String getCategory(){
        return this.category;
    }
    public String getSubcategory(){
        return this.subcategory;
    }
    public double getRevenue(){
        return this.revenue;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public double getProfit(){
        return this.profit;
    }
}
