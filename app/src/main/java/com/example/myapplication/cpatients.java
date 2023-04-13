package com.example.myapplication;

public class cpatients {
    String Patient_name, Email,Phone_no,Address,State,City,Pin,Aadhar_card_number, Pan_card_number,Medical_Insurance, Patient_id;

    cpatients(){

    }
    public cpatients(String Patient_name,String Email,String Phone_no,String Address,String State,String City,String Pin,String Aadhar_card_number,String Pan_card_number,String Medical_Insurance,String Patient_id)
    {
        this.Patient_name = Patient_name;
        this.Email = Email;
        this.Phone_no = Phone_no;
        this.Address = Address;
        this.State = State;
        this.City = City;
        this.Pin = Pin;
        this.Aadhar_card_number = Aadhar_card_number;
        this.Pan_card_number = Pan_card_number;
        this.Medical_Insurance=Medical_Insurance;
        this.Patient_id = Patient_id;
    }
    public String getPatient_name() {
        return Patient_name;
    }
    public String getEmail() {
        return Email;
    }
    public String getPhone_no() {
        return Phone_no;
    }
    public String getAddress() {
        return Address;
    }
    public String getState() {
        return State;
    }
    public String getCity() {
        return City;
    }
    public String getPin() {
        return Pin;
    }
    public String getAadhar_card_number() {
        return Aadhar_card_number;
    }
    public String getPan_card_number() {
        return Pan_card_number;
    }
    public String getMedical_Insurance() {
        return Medical_Insurance;
    }

    public String getPatient_id() {
        return Patient_id;
    }

    public void setPatient_id(String Patient_id) {
        this.Patient_id = Patient_id;
    }
}
