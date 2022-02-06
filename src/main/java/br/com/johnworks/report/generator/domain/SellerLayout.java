package br.com.johnworks.report.generator.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class SellerLayout implements Layout {

    private String document;
    private String name;
    private BigDecimal salary;

    @Override
    public String getCode() {
        return "001";
    }
}
