package com.trading.controller;

import com.trading.domain.WalletTransactionType;
import com.trading.model.User;
import com.trading.model.Wallet;
import com.trading.model.WalletTransaction;
import com.trading.model.Withdrawal;
import com.trading.service.UserService;
import com.trading.service.WalletService;
import com.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

//        WalletTransaction walletTransaction = walletTransactionService.createTransaction(
//                userWallet,
//                WalletTransactionType.WITHDRAWAL,
//                null,
//                "Bank account withdrawal",
//                withdrawal.getAmount()
//        );

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);

        Withdrawal withdrawal = withdrawalService.proceedWithdrawal(id, accept);

        Wallet userWallet = walletService.getUserWallet(user);

        if(!accept){
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);

        List<Withdrawal> withdrawals = withdrawalService.getUsersWithdrawalHistory(user);

        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }

    @GetMapping("/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);

        List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
}
