package com.aurionpro.service;

import java.util.List;

import com.aurionpro.dao.BeneficiaryDAO;
import com.aurionpro.model.Beneficiary;

public class BeneficiaryService {
	private BeneficiaryDAO dao = new BeneficiaryDAO();

    public boolean addBeneficiary(Beneficiary b) throws Exception {
        return dao.addBeneficiary(b);
    }

    public List<Beneficiary> getBeneficiariesByOwner(int ownerAccountId) throws Exception {
        return dao.getBeneficiariesByOwner(ownerAccountId);
    }

    public boolean deleteBeneficiary(int beneficiaryId, int ownerAccountId) throws Exception {
        return dao.deleteBeneficiary(beneficiaryId, ownerAccountId);
    }
    public int getAccountIdByCustomer(int customerId) {
    	return dao.getAccountIdByCustomer(customerId);
    }

}
