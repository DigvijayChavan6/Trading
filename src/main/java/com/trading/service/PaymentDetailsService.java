package com.trading.service;

import com.trading.model.PaymentDetails;
import com.trading.model.User;

public interface PaymentDetailsService {
    PaymentDetails addPaymentDetails(
            String accountNumber,
            String accountHolderName,
            String ifsc,
            String bankName,
            User user
    );

    PaymentDetails getUserPaymentDetails(User user);
}
