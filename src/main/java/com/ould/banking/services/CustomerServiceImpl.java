package com.ould.banking.services;

import com.ould.banking.dtos.CustomerDTO;
import com.ould.banking.entities.*;
import com.ould.banking.exceptions.CustomerNotFoundException;
import com.ould.banking.mappers.CustomerMapperImp;
import com.ould.banking.repositories.AccountOperationRepository;
import com.ould.banking.repositories.BankAccountRepository;
import com.ould.banking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private CustomerMapperImp customerMapper;
    private BankAccountService bankAccountService;
    //Logger log=LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer= customerMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return customerMapper.fromCustomer(savedCustomer);
    }







    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customerList=customerRepository.findAll();
        List<CustomerDTO> customerDTOList=customerList.stream()
                .map(customer -> customerMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        /*
        List<CustomerDTO> customerDTOList=new ArrayList<>();
        for (Customer customer:customerList){
            CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
            customerDTOList.add(customerDTO);
        }
         */
        return customerDTOList;
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not Found"));
        return customerMapper.fromCustomer(customer);
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
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
        return customers.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());

    }
}
