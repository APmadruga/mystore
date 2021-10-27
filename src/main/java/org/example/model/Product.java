package org.example.model;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Long price;
    private String name;
    @Enumerated
    private ProductType productType;
    //many to many products have one order
    @ManyToMany
    @JoinTable(
            name = "products_orders",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    Set<Order> orders;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

}
