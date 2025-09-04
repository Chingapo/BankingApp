package com.techlabs.model;

import java.sql.Timestamp;

public class Beneficiary {
    private int beneficiaryId;
    private int userId;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private String nickname;
    private boolean isActive;
    private Timestamp addedAt;
    
    // Constructors
    public Beneficiary() {}
    
    public Beneficiary(int userId, String beneficiaryAccountNumber, String beneficiaryName) {
        this.userId = userId;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.beneficiaryName = beneficiaryName;
    }
    
    // Getters and Setters
    public int getBeneficiaryId() { 
        return beneficiaryId; 
    }
    
    public void setBeneficiaryId(int beneficiaryId) { 
        this.beneficiaryId = beneficiaryId; 
    }
    
    public int getUserId() { 
        return userId; 
    }
    
    public void setUserId(int userId) { 
        this.userId = userId; 
    }
    
    public String getBeneficiaryAccountNumber() { 
        return beneficiaryAccountNumber; 
    }
    
    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) { 
        this.beneficiaryAccountNumber = beneficiaryAccountNumber; 
    }
    
    public String getBeneficiaryName() { 
        return beneficiaryName; 
    }
    
    public void setBeneficiaryName(String beneficiaryName) { 
        this.beneficiaryName = beneficiaryName; 
    }
    
    public String getNickname() { 
        return nickname; 
    }
    
    public void setNickname(String nickname) { 
        this.nickname = nickname; 
    }
    
    public boolean isActive() { 
        return isActive; 
    }
    
    public void setActive(boolean active) { 
        isActive = active; 
    }
    
    public Timestamp getAddedAt() { 
        return addedAt; 
    }
    
    public void setAddedAt(Timestamp addedAt) { 
        this.addedAt = addedAt; 
    }
}