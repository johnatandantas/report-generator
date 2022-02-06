package br.com.johnworks.report.generator.reader.factory;

import br.com.johnworks.report.generator.domain.Layout;
import br.com.johnworks.report.generator.reader.strategy.CalculateQtdCustomerStrategy;
import br.com.johnworks.report.generator.reader.strategy.CalculateQtdSellerStrategy;
import br.com.johnworks.report.generator.reader.strategy.FileInformationResultStrategy;
import br.com.johnworks.report.generator.reader.strategy.HighestSaleValueStrategy;

import java.util.HashMap;
import java.util.Map;

public class FileInformationFactory {

    public FileInformationResultStrategy factory(Layout layout) {
        Map<String, FileInformationResultStrategy> mapper = new HashMap<>();
        mapper.put("001", new CalculateQtdSellerStrategy());
        mapper.put("002", new CalculateQtdCustomerStrategy());
        mapper.put("003", new HighestSaleValueStrategy());
        return mapper.get(layout.getCode());
    }
}
