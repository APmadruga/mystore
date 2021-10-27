package org.example.servise.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRS {
    private Long id;
    private String name;
    private Long profit;
    private Long numClients;
    private Long numProducts;
    private Long numOrders;
}
