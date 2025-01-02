package com.trading.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.trading.domain.PaymentMethod;
import com.trading.model.PaymentOrder;
import com.trading.model.User;
import com.trading.response.PaymentResponse;

public interface PaymentService {
    PaymentOrder createOrder(User user,
                             Long amount,
                             PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder,
                                String paymentId) throws RazorpayException;

    PaymentResponse createRazorpayPaymentLink(User user,
                                              Long amount) throws RazorpayException;

    PaymentResponse createStripePaymentLink(User user,
                                            Long amount,
                                            Long orderId) throws StripeException;

}
