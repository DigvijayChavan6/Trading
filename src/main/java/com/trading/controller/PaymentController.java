package com.trading.controller;

import com.trading.domain.PaymentMethod;
import com.trading.model.PaymentOrder;
import com.trading.model.User;
import com.trading.response.PaymentResponse;
import com.trading.service.PaymentService;
import com.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        User user = userService.findUserByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(
                user, amount, paymentMethod
        );

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentService.createRazorpayPaymentLink(
                    user, amount
            );
        }else{
            paymentResponse = paymentService.createStripePaymentLink(
                    user, amount, order.getId()
            );
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED );
    }

}
