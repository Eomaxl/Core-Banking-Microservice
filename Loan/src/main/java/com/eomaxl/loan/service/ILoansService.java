package com.eomaxl.loan.service;

import com.eomaxl.loan.dto.LoansDto;

public interface ILoansService {
    /**
     * @param mobileNumber - Mobile number of the customer
     * */
    void createLoan(String mobileNumber);
    /**
     * @param mobileNumber - Mobile Number of the customer
     * @return LoansDto - Loan details based on the mobile number
     * */
    LoansDto fetchLoan(String mobileNumber);
    /**
     * @param loansDto - LoansDto object
     * @return boolean - indicates if the update of card details is successfull or not
     * */
    boolean updateLoan(LoansDto loansDto);
    /**
     * @param mobileNumber - Mobile number of the customer
     * @return boolean - indicates if the delete operation is successfull or not
     * */
    boolean deleteLoan(String mobileNumber);
}
