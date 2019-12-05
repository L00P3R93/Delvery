package com.queens.delivery.models;

public class Products {
    private int product_id;
    private String product_name;
    private String product_image;
    private String quantity;
    private String price;
    private String sub_total;


    public Products(int product_id, String product_name, String product_image, String quantity, String price, String sub_total){
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.quantity = quantity;
        this.price = price;
        this.sub_total = sub_total;
    }

    //Getters
    public int getProductID(){return product_id;}
    public String getProduct_name(){return product_name;}
    public String getProduct_image(){return product_image;}
    public String getQuantity(){return quantity;}
    public String getPrice(){return price;}
    public String getSub_total(){return sub_total;}

}
