package org.example.controller.request;
import org.example.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRQ {
    @Min(0)
    private Long price;
    private String name;
    private ProductType productType;
    private Long storeId;
}
