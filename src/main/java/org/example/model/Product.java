package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long price;
    private String name;
    @Enumerated
    private ProductType productType;
    //many to many products have one order
    @ManyToMany
    private List<Order> orders;
    @ManyToOne
    private Store store;

}
