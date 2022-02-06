package br.com.johnworks.report.generator.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerLayout implements Layout {

    private String document;
    private String name;
    private String businessArea;

    @Override
    public String getCode() {
        return "002";
    }
}
