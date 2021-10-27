package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Order")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private LocalDateTime purchaseSubmitTime;
    private Long total;
    //ManyToMany with products
    @ManyToMany(mappedBy = "orders")
    Set<Product> productList;
    //a client has many orders
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public Order(LocalDateTime purchaseSubmitTime, Set<Product> productList, Client client, Store store) {
        this.purchaseSubmitTime = purchaseSubmitTime;
        this.total = productList.stream().map(x -> x.getPrice()).reduce((a,b) -> (Long) a +  (Long) b).stream().count();
        this.productList = productList;
        this.client = client;
        this.store = store;
    }
}


