package br.com.johnworks.report.generator.reader.strategy;

import br.com.johnworks.report.generator.domain.FileInformation;
import br.com.johnworks.report.generator.domain.Layout;

public class CalculateQtdCustomerStrategy implements FileInformationResultStrategy {

    @Override
    public void execute(FileInformation fileInformation, Layout layout) {
        fileInformation.setQuantityCustomer(fileInformation.getQuantityCustomer() + 1);
    }
}
