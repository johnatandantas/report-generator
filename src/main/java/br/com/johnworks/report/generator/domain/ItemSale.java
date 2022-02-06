package br.com.johnworks.report.generator.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemSale {

    private String saleItemId;
    private Long quantity;
    private BigDecimal price;

}
