package com.example.myapplication;

public class patients
{

    String Patient_name, Gender,Patient_age_yrs,Patient_height_ft,Patient_weight_kg,Symptom1,Symptom2,Symptom3, Symptom4, Symptom5, Symptom6, Symptom7, Symptom8, Symptom9, Symptom10, Additional_details, Patient_id;

    patients(){

    }
    public patients(String Patient_name,String Gender,String Patient_age_yrs,String Patient_height_ft,String Patient_weight_kg,String Symptom1,String Symptom2,String Symptom3,String Symptom4,String Symptom5,String Symptom6,String Symptom7,String Symptom8,String Symptom9,String Symptom10,String Additional_details,String Patient_id)
    {
        this.Patient_name = Patient_name;
        this.Gender = Gender;
        this.Patient_age_yrs = Patient_age_yrs;
        this.Patient_height_ft = Patient_height_ft;
        this.Patient_weight_kg = Patient_weight_kg;
        this.Symptom1 = Symptom1;
        this.Symptom2 = Symptom2;
        this.Symptom3 = Symptom3;
        this.Symptom4 = Symptom4;
        this.Symptom5 = Symptom5;
        this.Symptom6 = Symptom6;
        this.Symptom7 = Symptom7;
        this.Symptom8 = Symptom8;
        this.Symptom9 = Symptom9;
        this.Symptom10 = Symptom10;
        this.Additional_details = Additional_details;
        this.Patient_id = Patient_id;
    }
    public String getPatient_name() {
        return Patient_name;
    }
    public String getGender() {
        return Gender;
    }
    public String getPatient_age_yrs() {
        return Patient_age_yrs;
    }
    public String getPatient_height_ft() {
        return Patient_height_ft;
    }
    public String getPatient_weight_kg() {
        return Patient_weight_kg;
    }
    public String getSymptom1() {
        return Symptom1;
    }
    public String getSymptom2() {
        return Symptom2;
    }
    public String getSymptom3() {
        return Symptom3;
    }
    public String getSymptom4() {
        return Symptom4;
    }
    public String getSymptom5() {
        return Symptom5;
    }
    public String getSymptom6() {
        return Symptom6;
    }
    public String getSymptom7() {
        return Symptom7;
    }
    public String getSymptom8() {
        return Symptom8;
    } public String getSymptom9() {
    return Symptom9;
}
    public String getSymptom10() {
        return Symptom10;
    }
    public String getAdditional_details() {
        return Additional_details;
    }

    public String getPatient_id() {
        return Patient_id;
    }

    public void setPatient_id(String Patient_id) {
        this.Patient_id = Patient_id;
    }
}