package org.example.model;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Store")
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "")
    private String name;
    @Column(columnDefinition = "")
    private Long profit;
    //one Store has many Clientes
    @OneToMany(mappedBy = "store")
    private List<Client> clients;
    //one Stores has many Products
    @OneToMany(mappedBy = "store")
    private List<Product> products;
    //one Store has many Order
    @OneToMany(mappedBy = "store")
    private List<Order> orders;

    /*public Store(String name, List<Client> clients, List<Product> products, List<Order> orders) {
        this.name = name;
        this.profit = orders.stream().map(x -> x.getTotal()).reduce((a,b) -> (Long) a +  (Long) b).stream().count();
        this.clients = clients;
        this.products = products;
        this.orders = orders;
    }*/

}
