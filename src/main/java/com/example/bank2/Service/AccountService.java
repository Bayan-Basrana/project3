package com.example.bank2.Service;

import com.example.bank2.API.ApiException;
import com.example.bank2.DTO.AccountDTO;
import com.example.bank2.Model.Account;
import com.example.bank2.Model.Customer;
import com.example.bank2.Model.User;
import com.example.bank2.Repository.AccountRepository;
import com.example.bank2.Repository.AuthRepository;
import com.example.bank2.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    //admin
    public List<Account> getAllAccounts(){
      return   accountRepository.findAll();
    }

    //customer
    //2. Create a new bank account
    public void createAccount (Integer user_id , Account account){
        User user = authRepository.findUserById(user_id);
        if(user==null){
            throw new ApiException("user not found");
        }
        Customer customer= customerRepository.findCustomerByUser(user);
        if (customer==null){
            throw new ApiException("no customer associate with this user");
        }
       account.setCustomer(customer);
        account.setIsActive(false);
        accountRepository.save(account);

    }

    public void updateAccount (Integer user_id, Integer account_id, Account account){
        User user = authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Customer customer= customerRepository.findCustomerByUser(user);
        if(customer==null){
            throw new ApiException("no customer associate with this user");}
        Account old= accountRepository.findAccountById(account_id);
        if (old==null){
            throw new ApiException("account not found");}
        if(!old.getCustomer().getId().equals(customer.getId())){
            throw new ApiException("this account is not belong to logged in user ");}
        old.setAccountNumber(account.getAccountNumber());
        old.setBalance(account.getBalance());
        accountRepository.save(old);
    }


    public void deleteAccount (Integer user_id, Integer account_id){
        User user = authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Customer customer= customerRepository.findCustomerByUser(user);
        if(customer==null){
            throw new ApiException("no customer associate with this user");}
        Account account= accountRepository.findAccountById(account_id);
        if (account==null){
            throw new ApiException("account not found");}
        if(!account.getCustomer().getId().equals(customer.getId())){
            throw new ApiException("this account is not belong to logged in user ");}
        accountRepository.delete(account);
    }


    //Admin or employee
    //3. Active a bank account
    public void activeAccount (Integer user_id, Integer account_id){
        User user= authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Account account= accountRepository.findAccountById(account_id);
        if (account==null){
            throw new ApiException("account not found");}
        account.setIsActive(true);
        accountRepository.save(account);
    }

    //customer
    //4. View account details
    public AccountDTO viewAccountDetails (Integer user_id , Integer account_id){
        User user= authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Customer customer= customerRepository.findCustomerByUser(user);
        if(customer==null){
            throw new ApiException("no customer associate with this user");}
        Account account= accountRepository.findAccountById(account_id);
        if (account==null){
            throw new ApiException("account not found");}
        if(!account.getCustomer().getId().equals(customer.getId())){
            throw new ApiException("this account is not belong to logged in user ");}
        return new AccountDTO(account.getAccountNumber(), account.getBalance(), account.getIsActive());
    }

    //customer
    //5. List user's accounts
    public List<Account> getAllMyAccounts (Integer user_id ){
        User user= authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Customer customer= customerRepository.findCustomerByUser(user);
        if(customer==null){
            throw new ApiException("no customer associate with this user");}
        return accountRepository.findAllByCustomer(customer);
    }
    //customer
    //6. Deposit and withdraw money
    public void withdraw (Integer user_id,Integer account_id, Double amount){
        User user= authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Account account= accountRepository.findAccountById(account_id);
        if (account==null){
            throw new ApiException("account not found");}
        if(!account.getCustomer().getId().equals(user.getCustomer().getId())){
            throw new ApiException("this account is not belong to logged in user ");}
        if(!account.getIsActive()){
            throw new ApiException("Account not Active");}
        if(account.getBalance()<amount){
            throw new ApiException("Insufficient balance");}
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);

    }
public void deposit (Integer user_id,Integer account_id, Double amount){
    User user= authRepository.findUserById(user_id);
    if (user==null){
        throw new ApiException("user not found");}
    Account account= accountRepository.findAccountById(account_id);
    if (account==null){
        throw new ApiException("account not found");}
    if(!account.getCustomer().getId().equals(user.getCustomer().getId())){
        throw new ApiException("this account is not belong to logged in user ");}
    if(!account.getIsActive()){
        throw new ApiException("Account not Active");}
  if(amount<=0){
      throw new ApiException("deposit Amount must be grater than zero");}

    account.setBalance(account.getBalance()+ amount);
    accountRepository.save(account);
}

    //customer
    // 7. Transfer funds between accounts
    public void transfer (Integer user_id,Integer fromAccount_id,Integer toAccount_id, Double amount){
        User user= authRepository.findUserById(user_id);
        Account fromAccount= accountRepository.findAccountById(fromAccount_id);
        if (fromAccount==null){
            throw new ApiException("source account not found");}
        if(!fromAccount.getCustomer().getId().equals(user.getCustomer().getId())){
            throw new ApiException("this account is not belong to logged in user ");}
        if(!fromAccount.getIsActive()){
            throw new ApiException("source Account not Active, Transfer not allowed");}
        if(amount<=0){
            throw new ApiException(" Amount must be grater than zero");}
        if(fromAccount.getBalance()<amount){
            throw new ApiException("Insufficient balance");}
        Account toAccount = accountRepository.findAccountById(toAccount_id);
        if (toAccount==null){
            throw new ApiException("target account not found");}
        if(!toAccount.getIsActive()){
            throw new ApiException("target Account not Active, Transfer not allowed");}
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    //Admin or employee
    //8. Block bank account
    public void blockAccount (Integer user_id,Integer account_id){
        User user= authRepository.findUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Account account= accountRepository.findAccountById(account_id);
        if (account==null){
            throw new ApiException("account not found");}
        account.setIsActive(false);
        accountRepository.save(account);
    }

}
