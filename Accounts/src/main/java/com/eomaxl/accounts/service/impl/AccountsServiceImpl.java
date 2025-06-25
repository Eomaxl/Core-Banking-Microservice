package com.eomaxl.accounts.service.impl;

import com.eomaxl.accounts.constants.AccountsConstants;
import com.eomaxl.accounts.dto.AccountsDto;
import com.eomaxl.accounts.dto.CustomerDto;
import com.eomaxl.accounts.entity.Accounts;
import com.eomaxl.accounts.entity.Customer;
import com.eomaxl.accounts.exception.CustomerAlreadyExistsException;
import com.eomaxl.accounts.exception.ResourceNotFoundException;
import com.eomaxl.accounts.mapper.AccountsMapper;
import com.eomaxl.accounts.mapper.CustomerMapper;
import com.eomaxl.accounts.repository.AccountsRepository;
import com.eomaxl.accounts.repository.CustomerRepository;
import com.eomaxl.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     *
     * @param customerDto - CustomerDto object
     * */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number : "+customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000_000_000L + new Random().nextInt(900_000_000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param mobileNumber - Input mobile number
     * @return Accounts Details based on a given mobileNumber
     * */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
       Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
               ()-> new ResourceNotFoundException("Accounts","customer id", customer.getCustomerId().toString())
       );
       CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
       customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
       return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     * */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated =  false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account","Account Number",accountsDto.getAccountType().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     * */
    @Override
    public boolean deleteAccount(String mobileNumber){
        Optional<Customer> customer = customerRepository.findByMobileNumber(mobileNumber);
        if(!customer.isPresent()) {
            throw new ResourceNotFoundException("Customer", "Customer Mobile Number", mobileNumber);
        };
        accountsRepository.deleteByCustomerId(customer.get().getCustomerId());
        customerRepository.delete(customer.get());
        return true;
    }
}
