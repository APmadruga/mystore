package org.example.servise.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRS {
    private Long id;
    private String name;
    private String address;
    private List<Long> ordersIds;
    private Long storeId ;
}
