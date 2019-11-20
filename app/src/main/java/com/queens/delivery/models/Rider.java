package com.queens.delivery.models;


import java.util.Date;

public class Rider {
    private String USERNAME;
    private String FULLNAME;

    private String EMAIL;
    private String PHONE;
    private Date SessionExpiryDate;

    public void setUSERNAME(String username){this.USERNAME=username;}
    public String getUSERNAME(){return this.USERNAME;}

    public void setFULLNAME(String fullname){this.FULLNAME=fullname;}
    public String getFULLNAME(){return this.FULLNAME;}

    public void setEMAIL(String email){this.EMAIL=email;}
    public String getEMAIL(){return this.EMAIL;}

    public void setPHONE(String phone){this.PHONE=phone;}
    public String getPHONE(){return this.PHONE;}

    public void setSessionExpiryDate(Date sessionExpiryDate){this.SessionExpiryDate=sessionExpiryDate;}
    public Date getSessionExpiryDate(){return this.SessionExpiryDate;}

}
