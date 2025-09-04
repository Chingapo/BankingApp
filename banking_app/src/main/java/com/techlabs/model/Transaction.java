package com.techlabs.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private Integer fromAccountId;
    private Integer toAccountId;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private String status;
    private Timestamp transactionDate;
    
    // Additional fields for display
    private String fromAccountNumber;
    private String toAccountNumber;
    
    // Constructors
    public Transaction() {}
    
    public Transaction(Integer fromAccountId, Integer toAccountId, String transactionType, 
                      BigDecimal amount, String description) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
    }
    
    // Getters and Setters
    public int getTransactionId() { 
        return transactionId; 
    }
    
    public void setTransactionId(int transactionId) { 
        this.transactionId = transactionId; 
    }
    
    public Integer getFromAccountId() { 
        return fromAccountId; 
    }
    
    public void setFromAccountId(Integer fromAccountId) { 
        this.fromAccountId = fromAccountId; 
    }
    
    public Integer getToAccountId() { 
        return toAccountId; 
    }
    
    public void setToAccountId(Integer toAccountId) { 
        this.toAccountId = toAccountId; 
    }
    
    public String getTransactionType() { 
        return transactionType; 
    }
    
    public void setTransactionType(String transactionType) { 
        this.transactionType = transactionType; 
    }
    
    public BigDecimal getAmount() { 
        return amount; 
    }
    
    public void setAmount(BigDecimal amount) { 
        this.amount = amount; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public Timestamp getTransactionDate() { 
        return transactionDate; 
    }
    
    public void setTransactionDate(Timestamp transactionDate) { 
        this.transactionDate = transactionDate; 
    }
    
    public String getFromAccountNumber() { 
        return fromAccountNumber; 
    }
    
    public void setFromAccountNumber(String fromAccountNumber) { 
        this.fromAccountNumber = fromAccountNumber; 
    }
    
    public String getToAccountNumber() { 
        return toAccountNumber; 
    }
    
    public void setToAccountNumber(String toAccountNumber) { 
        this.toAccountNumber = toAccountNumber; 
    }
}