package org.example.controller;

import org.example.controller.request.ClientRQ;
import org.example.controller.request.OrderRQ;
import org.example.servise.OrderService;
import org.example.servise.response.ClientRS;
import org.example.servise.response.OrderRS;
import org.example.servise.response.ProductRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderRS>> getOrders(){
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderRS> getOrderById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderRS> createOrder(@RequestBody OrderRQ orderRQ) {
        return ResponseEntity.ok(orderService.createOrder(orderRQ));
    }

    @DeleteMapping(path = "/delete-order/{id}")
    public ResponseEntity deleteOrder(@PathVariable(value = "id") Long id) {
        orderService.deleteById(id);
        return ResponseEntity.created(URI.create("/order" + id)).body("Order was deleted");
    }
}
