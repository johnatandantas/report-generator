package br.com.johnworks.report.generator.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"itemSaleText"})
public class SaleLayout implements Layout {

    private String saleId;
    private List<ItemSale> itemSale = new ArrayList<>();
    private String sellerName;
    private String itemSaleText;

    public String getItemSaleText() {
        return itemSaleText;
    }

    public SaleLayout setItemSaleText(String itemSaleText) {
        String[] items = itemSaleText
                .replace("[", "")
                .replace("]", "")
                .split(",");

        Arrays.stream(items).forEach(item -> {
            final String[] itemAux = item.split("-");
            itemSale.add(new ItemSale(itemAux[0], Long.valueOf(itemAux[1]), new BigDecimal(itemAux[2])));
        });
        this.itemSaleText = itemSaleText;
        return this;
    }

    @Override
    public String getCode() {
        return "003";
    }
}
