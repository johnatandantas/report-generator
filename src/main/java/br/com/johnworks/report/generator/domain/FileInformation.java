package br.com.johnworks.report.generator.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileInformation {

    private long quantityCustomer = 0;
    private long quantitySeller = 0;
    private ItemSale expensiveSale;
    private SaleLayout worstSeller;
}
