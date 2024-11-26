package com.trading.controller;

import com.trading.domain.OrderType;
import com.trading.model.Coin;
import com.trading.model.Order;
import com.trading.model.User;
import com.trading.request.CreateOrderRequest;
import com.trading.service.CoinService;
import com.trading.service.OrderService;
import com.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization")String jwt,
            @RequestBody CreateOrderRequest req
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(
                coin, req.getQuantity(), req.getOrderType(), user
        );

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long orderId
    ) throws Exception {

        User user = userService.findUserByEmail(jwt);

        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        }
        throw new Exception("Invalid User or You don't have access");
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(
            @RequestHeader("Authorization")String jwt,
            @RequestParam(required = false) OrderType orderType,
            @RequestParam(required = false)String assetSymbol
    ) throws Exception {
        Long userId = userService.findUserByJwt(jwt).getId();

        List<Order> userOrders = orderService.getAllOrdersOfUser(userId, orderType, assetSymbol);
        return ResponseEntity.ok(userOrders);
    }

}
