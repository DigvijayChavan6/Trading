//package com.trading.model;
//
//import com.trading.domain.WalletTransactionType;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Entity
//@Data
//public class Transaction {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ManyToOne
//    private Wallet wallet;
//
//    private WalletTransactionType type;
//
//    private BigDecimal amount;
//
//    private LocalDate date;
//
//    private String purpose;
//}
