package com.eomaxl.loan.mapper;

import com.eomaxl.loan.dto.LoansDto;
import com.eomaxl.loan.entity.Loans;

public class LoansMapper {
    public static LoansDto mapLoansToLoansDto(Loans loans, LoansDto loansDto){
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loansDto;
    }

    public static Loans mapLoansDtoToLoans(LoansDto loansDto, Loans loans){
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutStandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
