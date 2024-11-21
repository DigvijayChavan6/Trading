package com.trading.request;

import com.trading.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private VerificationType verificationType;
    private String sendTo;
}
