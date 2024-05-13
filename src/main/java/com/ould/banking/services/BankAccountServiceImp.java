package com.ould.banking.services;

import com.ould.banking.dtos.AddCurrentAccountDTO;
import com.ould.banking.dtos.BankAccountDTO;
import com.ould.banking.dtos.CurrentAccountDTO;
import com.ould.banking.dtos.SavingAccountDTO;
import com.ould.banking.entities.BankAccount;
import com.ould.banking.entities.CurrentAccount;
import com.ould.banking.entities.Customer;
import com.ould.banking.entities.SavingAccount;
import com.ould.banking.enums.AccountStatus;
import com.ould.banking.exceptions.BankAccountNotFoundException;
import com.ould.banking.exceptions.CustomerNotFoundException;
import com.ould.banking.mappers.BankAccountMapperImp;
import com.ould.banking.repositories.AccountOperationRepository;
import com.ould.banking.repositories.BankAccountRepository;
import com.ould.banking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankAccountServiceImp implements BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImp bankAccountMapper;

    @Override
    public SavingAccountDTO saveSavingAccount(SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(savingAccountDTO.getCustomerDTO().getId()).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount=bankAccountMapper.fromSavingAccountDTO(savingAccountDTO);
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setDateCreation(LocalDate.now());
        savingAccount.setDateExpiration(LocalDate.now().plusYears(4));
        savingAccount.setBalance(savingAccountDTO.getBalance());
        savingAccount.setInterestRate(savingAccountDTO.getInterestRate());
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCurrency(savingAccountDTO.getCurrency());

        SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(savedBankAccount);
    }


    @Override
    public CurrentAccountDTO saveCurrentAccount(AddCurrentAccountDTO addcurrentAccountDTO) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(addcurrentAccountDTO.getCustomerDTO().getId()).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        //Customer customer= customerMapper.fromCustomerDTO(customerDTO);
        //Customer savedCustomer=customerRepository.save(customer);
        CurrentAccountDTO currentAccountDTO=bankAccountMapper.fromAddCurrentAccountDTO(addcurrentAccountDTO);
        CurrentAccount currentAccount =bankAccountMapper.fromCurrentAccountDTO(currentAccountDTO);
        //CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(currentAccountDTO.getBalance());
        currentAccount.setDateCreation(LocalDate.now());
        currentAccount.setDateExpiration(LocalDate.now().plusYears(4));
        currentAccount.setOverDraft(currentAccountDTO.getOverDraft());
        currentAccount.setCustomer(customer);
        currentAccount.setCurrency(currentAccountDTO.getCurrency());
        currentAccount.setStatus(AccountStatus.CREATED);


        CurrentAccount savedBankAccount= bankAccountRepository.save(currentAccount);
        return bankAccountMapper.fromCurrentAccount(savedBankAccount);

    }


    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                . orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;

            return bankAccountMapper.fromSavingAccount(savingAccount);
        }
        else {
            CurrentAccount currentAccount =(CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentAccount(currentAccount);
        }
    }

    @Override
    public List<BankAccountDTO> customerBankAccountsList(Long id) {

        List<BankAccount> bankAccountList=bankAccountRepository.findBankAccountByCustomerId(id);

        return getBankAccountDTOList(bankAccountList);
    }

    public List<BankAccountDTO> getBankAccountDTOList(List<BankAccount> bankAccountList) {
        List<BankAccountDTO> bankAccountDTOList=bankAccountList.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount){
                SavingAccount savingAccount= (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccount(savingAccount);
            }else
            {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOList;
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
       List<BankAccount> bankAccounts=bankAccountRepository.findAll();
        return getBankAccountDTOList(bankAccounts);
    }

    /*
    public SavingAccountDTO saveSavingAccount(Long customerId,SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not Found"));

        savingAccountDTO.setCustomerDTO(c);
        SavingAccount savingAccount=new SavingAccount();
        savingAccount=bankAccountMapper.fromSavingAccountDTO(savingAccountDTO);
        bankAccountRepository.save(savingAccount);
        return savingAccountDTO;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException {
        log.info("Saving new customer");
        //CustomerDTO customerDTO1=getCustomerById(customerId);
        Customer customer= customerMapper.fromCustomerDTO(customerDTO);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not Found");
        }
        Customer savedCustomer=customerRepository.save(customer);
        return customerMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer= customerMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return customerMapper.fromCustomer(savedCustomer);
    }

     */
}
