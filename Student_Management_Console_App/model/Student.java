package com.aurionpro.model;

public class Student {
    private int studentId;
    private int rollNo;
    private String studentName;
    private int age;
    private double percentage;
    private boolean isActive;
 
 
    private String city;
    private String mobileNo;
 
    public Student() {
    }
    
    public Student(int studentId, int rollNo, String studentName, int age, double percentage,
            boolean isActive, String city, String mobileNo) {
 this.studentId = studentId;
 this.rollNo = rollNo;
 this.studentName = studentName;
 this.age = age;
 this.percentage = percentage;
 this.isActive = isActive;
 this.city = city;
 this.mobileNo = mobileNo;
}
    
    public Student(int studentId, int rollNo, String studentName, int age, double percentage,int studentProfileId, String city, String mobileNo) {
 this.studentId = studentId;
 this.rollNo = rollNo;
 this.studentName = studentName;
 this.age = age;
 this.percentage = percentage;

 this.city = city;
 this.mobileNo = mobileNo;
}
 
    public Student(int studentId, int rollNo, String studentName, int age, double percentage,
                   boolean isActive, int studentProfileId, String city, String mobileNo) {
        this.studentId = studentId;
        this.rollNo = rollNo;
        this.studentName = studentName;
        this.age = age;
        this.percentage = percentage;
        this.isActive = isActive;
        this.city = city;
        this.mobileNo = mobileNo;
    }
 
    public int getStudentId() {
        return studentId;
    }
 
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
 
    public int getRollNo() {
        return rollNo;
    }
 
    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
 
    public String getStudentName() {
        return studentName;
    }
 
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
 
    public double getPercentage() {
        return percentage;
    }
 
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
 
    public boolean isActive() {
        return isActive;
    }
 
    public void setActive(boolean active) {
        isActive = active;
    }
 
 
    public String getCity() {
        return city;
    }
 
    public void setCity(String city) {
        this.city = city;
    }
 
    public String getMobileNo() {
        return mobileNo;
    }
 
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
 
    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", rollNo=" + rollNo + ", studentName=" + studentName +
                ", age=" + age + ", percentage=" + percentage + ", isActive=" + isActive +
                ", studentProfileId=" + ", city=" + city + ", mobileNo=" + mobileNo + "]";
    }
}