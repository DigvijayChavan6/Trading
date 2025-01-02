package com.trading.controller;

import com.trading.model.Order;
import com.trading.model.User;
import com.trading.model.Wallet;
import com.trading.model.WalletTransaction;
import com.trading.service.OrderService;
import com.trading.service.UserService;
import com.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Wallet> getUserWallet(
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req
    ) throws Exception {
        User senderUser = userService.findUserByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);

        Wallet senderWallet = walletService.walletToWalletTransfer(
                senderUser,
                receiverWallet,
                req.getAmount());

        return new ResponseEntity<>(senderWallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}