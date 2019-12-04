package com.queens.delivery.models;

/* orders table
bill_no
customer_name
customer_address
customer_phone
gross_amount
net_amount
paid_status
user_id
priority
packed
assigned
exchange
shared
location[shop]
type[cod,cash,mpesa]
partial[0,1]
partial_amount
remarks
added_date
*/

/* deliveries table
id
bill_id
rider_id
payment
cash
cod
cash_received
mpesa_code
status
accept
priority
reasons
exchange
rejection
stocked
dt
date
 */


public class Orders {
    private int id;
    private int billId;
    private String customerPhone;
    private String customerAddress;
    private String date;

    public Orders(int id, int billId, String customerPhone, String customerAddress, String date){
        this.id=id;
        this.billId = billId;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.date = date;
    }

    //Getters
    public int getId(){return id;}
    public int getBillId(){return billId;}
    public String getCustomerPhone(){return customerPhone;}
    public String getCustomerAddress(){return customerAddress;}
    public String getDate(){return date;}
}
