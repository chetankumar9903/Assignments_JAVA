package com.aurionpro.model;
public class LeaveSummary {
    private String typeName;
    private int used;
    private int allowed;

    public LeaveSummary(String typeName, int used, int allowed) {
        this.typeName = typeName;
        this.used = used;
        this.allowed = allowed;
    }

    public String getTypeName() { return typeName; }
    public int getUsed() { return used; }
    public int getAllowed() { return allowed; }
}

