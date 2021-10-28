package org.example.model;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    private String address;
    //one to many with order
    @OneToMany(mappedBy = "client")
    private List<Order> orders_submitted;
    @ManyToOne
    private Store store;
}

