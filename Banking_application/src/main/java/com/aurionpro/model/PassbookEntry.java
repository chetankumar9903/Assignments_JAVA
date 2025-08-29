package com.aurionpro.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PassbookEntry {
    private int txnId;
    private Timestamp txnDate;
    private String type;
    private String status;
    private String narration;
    private BigDecimal debit;
    private BigDecimal credit;
    private int accountId;

    // NEW FIELDS
    private Integer fromAccountId;
    private Integer toAccountId;
    private String toExternalAccount;
    private String toExternalIfsc;

    public int getTxnId() { return txnId; }
    public void setTxnId(int txnId) { this.txnId = txnId; }

    public Timestamp getTxnDate() { return txnDate; }
    public void setTxnDate(Timestamp txnDate) { this.txnDate = txnDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNarration() { return narration; }
    public void setNarration(String narration) { this.narration = narration; }

    public BigDecimal getDebit() { return debit; }
    public void setDebit(BigDecimal debit) { this.debit = debit; }

    public BigDecimal getCredit() { return credit; }
    public void setCredit(BigDecimal credit) { this.credit = credit; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public Integer getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(Integer fromAccountId) { this.fromAccountId = fromAccountId; }

    public Integer getToAccountId() { return toAccountId; }
    public void setToAccountId(Integer toAccountId) { this.toAccountId = toAccountId; }

    public String getToExternalAccount() { return toExternalAccount; }
    public void setToExternalAccount(String toExternalAccount) { this.toExternalAccount = toExternalAccount; }

    public String getToExternalIfsc() { return toExternalIfsc; }
    public void setToExternalIfsc(String toExternalIfsc) { this.toExternalIfsc = toExternalIfsc; }
}
