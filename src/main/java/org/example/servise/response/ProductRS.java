package org.example.servise.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.model.Order;
import org.example.model.ProductType;
import org.example.model.Store;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRS {
    private Long id;
    private Long price;
    private String name;
    private ProductType productType;
    private List<Long> ordersId;
    private Long storeId;
}
