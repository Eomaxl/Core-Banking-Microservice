package com.eomaxl.loan.service.impl;

import com.eomaxl.loan.dto.LoansDto;
import com.eomaxl.loan.entity.Loans;
import com.eomaxl.loan.constants.LoanConstants;
import com.eomaxl.loan.mapper.LoansMapper;
import com.eomaxl.loan.repository.LoansRepository;
import com.eomaxl.loan.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ILoansServiceImpl implements ILoansService {
    private LoansRepository loansRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber){
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber){
        Loans loans = new Loans();
        long randomLoanNumber = 100_000_000_000L + new Random().nextInt(900_000_000);
        loans.setLoanNumber(Long.toString(randomLoanNumber));
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType(LoanConstants.HOME_LOAN);
        loans.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutStandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return loans;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber){
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","mobileNumber", mobileNumber)
        );
        return LoansMapper.mapLoansToLoansDto(loans, new LoansDto());
    }

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan","loanNumber", loansDto.getLoanNumber())
        );
        LoansMapper.mapLoansDtoToLoans(loansDto,loans);
        loansRepository.save(loans);
        return true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber){
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","mobileNumber", mobileNumber)
        );
        loansRepository.deleteById((long) loans.getLoanId());
        return true;
    }
}
