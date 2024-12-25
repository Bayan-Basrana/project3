package com.example.bank2.Controller;

import com.example.bank2.API.ApiResponse;
import com.example.bank2.Model.Account;
import com.example.bank2.Model.User;
import com.example.bank2.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //admin
    @GetMapping("/getAllAccounts")
    public ResponseEntity getAllAccounts (){
        return ResponseEntity.status(200).body(accountService.getAllAccounts());
    }

    //customer
    @PostMapping("/create")
    public ResponseEntity createAccount (@AuthenticationPrincipal User user ,@RequestBody @Valid Account account){
        accountService.createAccount(user.getId(), account);
        return ResponseEntity.status(200).body(new ApiResponse("create Account successfully"));
    }

    //customer
    @PutMapping("/update/{account_id}")
    public ResponseEntity updateAccount (@AuthenticationPrincipal User user ,@PathVariable Integer account_id, @RequestBody @Valid Account account){
        accountService.updateAccount(user.getId(), account_id,account);
        return ResponseEntity.status(200).body(new ApiResponse("update Account successfully"));
    }

    //customer
    @DeleteMapping("/delete/{account_id}")
    public ResponseEntity deleteAccount (@AuthenticationPrincipal User user ,@PathVariable Integer account_id){
        accountService.deleteAccount(user.getId(), account_id);
        return ResponseEntity.status(200).body(new ApiResponse("delete Account successfully"));
    }

    //Admin or employee
    @PutMapping("/active-account/{account_id}")
    public ResponseEntity activeAccount (@AuthenticationPrincipal User user,@PathVariable Integer account_id){
        accountService.activeAccount(user.getId() ,account_id);
        return ResponseEntity.status(200).body(new ApiResponse("activated Account successfully"));
    }

    //customer
    @GetMapping("/view-account-details/{account_id}")
    public ResponseEntity viewAccountDetails (@AuthenticationPrincipal User user ,@PathVariable Integer account_id){
        return ResponseEntity.status(200).body(accountService.viewAccountDetails(user.getId(), account_id));
    }

    //customer
    @GetMapping("/myAccounts")
    public ResponseEntity getAllMyAccounts (@AuthenticationPrincipal User user ){
        return ResponseEntity.status(200).body(accountService.getAllMyAccounts(user.getId()));
    }

    //customer
    @PutMapping("/withdraw/{account_id}")
    public ResponseEntity withdraw (@AuthenticationPrincipal User user ,@PathVariable Integer account_id, @RequestBody Double amount){
        accountService.withdraw(user.getId(), account_id,amount);
        return ResponseEntity.status(200).body(new ApiResponse("Amount withdraw successfully"));
    }

    //customer
    @PutMapping("/deposit/{account_id}")
    public ResponseEntity deposit (@AuthenticationPrincipal User user ,@PathVariable Integer account_id, @RequestBody Double amount){
        accountService.deposit(user.getId(), account_id,amount);
        return ResponseEntity.status(200).body(new ApiResponse("Amount deposit successfully"));
    }

    //customer
    @PutMapping("/transfer/{fromAccount_id}/{toAccount_id}")
    public ResponseEntity transfer (@AuthenticationPrincipal User user ,@PathVariable Integer fromAccount_id,@PathVariable Integer toAccount_id, @RequestBody Double amount){
        accountService.transfer(user.getId(), fromAccount_id,toAccount_id,amount);
        return ResponseEntity.status(200).body(new ApiResponse("Amount transfer successfully"));
    }

    //Admin or employee
    @PutMapping("/block-account/{account_id}")
    public ResponseEntity blockAccount (@AuthenticationPrincipal User user ,@PathVariable Integer account_id){
        accountService.blockAccount(user.getId(), account_id);
        return ResponseEntity.status(200).body(new ApiResponse("blocked Account successfully"));
    }


}
