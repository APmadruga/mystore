package org.example.servise;

import org.example.controller.request.OrderRQ;
import org.example.exceptions.ResourceNotFound;
import org.example.model.Client;
import org.example.model.Order;
import org.example.model.Product;
import org.example.model.Store;
import org.example.repository.ClientRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StoreRepository;
import org.example.servise.response.OrderRS;
import org.example.servise.response.ProductRS;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final StoreRepository storeRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, ClientRepository clientRepository, StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.storeRepository = storeRepository;
    }

    public List<OrderRS> getOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderRS> orderRSList = new ArrayList<>();
        for (Order order : orderList) {
            final OrderRS orderRS = new OrderRS(
                    order.getId(),
                    order.getPurchaseSubmitTime(),
                    order.getTotal(),
                    order.getProductList().stream().map(x -> x.getId()).collect(Collectors.toList()),
                    order.getClient().getId(),
                    order.getStore().getId()
            );
            orderRSList.add(orderRS);
        }
        return orderRSList;
    }

    public OrderRS findById(Long id) {
        try {
            Order order = orderRepository.findById(id).get();
            return  new OrderRS(
                    order.getId(),
                    order.getPurchaseSubmitTime(),
                    order.getTotal(),
                    order.getProductList().stream().map(x -> x.getId()).collect(Collectors.toList()),
                    order.getClient().getId(),
                    order.getStore().getId()
            );
        }catch (Exception e){
            throw new ResourceNotFound("Order Not Found");
        }
    }

    public OrderRS createOrder(OrderRQ orderRQ) {
        if(!clientRepository.existsById(orderRQ.getClientId()))
            throw new ResourceNotFound("Need to an Existing Client");
        Client client = clientRepository.getById(orderRQ.getClientId());
        if(!storeRepository.existsById(client.getStore().getId()))
            throw new ResourceNotFound("Client needs to be in a Existing Store");
        Store store = clientRepository.getById(orderRQ.getClientId()).getStore();
        Order order = new Order(
                LocalDateTime.now(),
                orderRQ.getProductListId().stream().map((x) -> {
                    if(productRepository.existsById(x))
                        throw new ResourceNotFound("You need to add Existing ProductsId's");
                    return productRepository.getById(x);
                }).collect(Collectors.toSet()),
                client,
                store
        );
        orderRepository.save(order);
        return  new OrderRS(
                order.getId(),
                order.getPurchaseSubmitTime(),
                order.getTotal(),
                orderRQ.getProductListId().stream().collect(Collectors.toList()),
                client.getId(),
                store.getId()
        );
    }
    
    public void deleteById(Long id) {
        try {
            orderRepository.deleteById(id);
        }catch (Exception e){
            throw new ResourceNotFound("Order Not Found");
        }
    }
}
