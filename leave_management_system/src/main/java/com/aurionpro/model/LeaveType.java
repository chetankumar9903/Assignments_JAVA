package com.aurionpro.model;
public class LeaveType {
    private int typeId;
    private String typeName;
    private int monthlyLimit;

    
    public LeaveType() {}

    
    public LeaveType(int typeId, String typeName, int monthlyLimit) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.monthlyLimit = monthlyLimit;
    }

   
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(int monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    @Override
    public String toString() {
        return "LeaveType [typeId=" + typeId + ", typeName=" + typeName + ", monthlyLimit=" + monthlyLimit + "]";
    }
}