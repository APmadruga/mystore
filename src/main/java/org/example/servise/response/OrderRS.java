package org.example.servise.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.model.Client;
import org.example.model.Store;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRS {
    private Long id;
    private LocalDateTime purchaseSubmitTime;
    private Long total;
    private List<Long> productsId;
    private Long clientId;
    private Long storeId;
}
