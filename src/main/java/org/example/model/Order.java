package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime purchaseSubmitTime;
    private Long total;
    //    //ManyToMany with products
    @ManyToMany(mappedBy = "orders")
    private List<Product> products;
//    //a client has many orders
    @ManyToOne
    private Client client;

    @ManyToOne
    private Store store;
}


