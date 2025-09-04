package com.techlabs.service;

import com.techlabs.dao.BeneficiaryDAO;
import com.techlabs.model.Beneficiary;

import java.util.List;

public class BeneficiaryService {
    private BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
    
    public List<Beneficiary> getBeneficiariesByUserId(int userId) {
        return beneficiaryDAO.getBeneficiariesByUserId(userId);
    }
    
    public boolean addBeneficiary(Beneficiary beneficiary) {
        if (beneficiary == null || beneficiary.getBeneficiaryAccountNumber() == null) {
            return false;
        }
        return beneficiaryDAO.addBeneficiary(beneficiary);
    }
    
    public boolean removeBeneficiary(int beneficiaryId) {
        return beneficiaryDAO.removeBeneficiary(beneficiaryId);
    }
    
    public boolean isBeneficiary(int userId, String accountNumber) {
        return beneficiaryDAO.isBeneficiary(userId, accountNumber);
    }
}