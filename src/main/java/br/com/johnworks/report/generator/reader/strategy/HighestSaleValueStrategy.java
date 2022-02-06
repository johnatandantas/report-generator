package br.com.johnworks.report.generator.reader.strategy;

import br.com.johnworks.report.generator.domain.FileInformation;
import br.com.johnworks.report.generator.domain.Layout;
import br.com.johnworks.report.generator.domain.SaleLayout;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;

public class HighestSaleValueStrategy implements FileInformationResultStrategy {

    @Override
    public void execute(FileInformation fileInformation, Layout layout) {
        SaleLayout saleLayout = (SaleLayout) layout;

        expensiveSale(fileInformation, saleLayout);
        worstSeller(fileInformation, saleLayout);

    }

    private void worstSeller(FileInformation fileInformation, SaleLayout saleLayout) {

        if (fileInformation.getWorstSeller() == null) {
            fileInformation.setWorstSeller(saleLayout);
            return;
        }

        double previousSaleItem = fileInformation.getWorstSeller().getItemSale().stream().mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity()).sum();
        double currentSaleItem = saleLayout.getItemSale().stream().mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity()).sum();

        if (previousSaleItem > currentSaleItem) {
            fileInformation.setWorstSeller(saleLayout);
        }

    }

    private void expensiveSale(FileInformation fileInformation, SaleLayout saleLayout) {
        final var maxSaleItem = Collections.max(saleLayout.getItemSale(), Comparator.comparing(itemSale -> itemSale.getPrice().multiply(new BigDecimal(itemSale.getQuantity()))));

        if (fileInformation.getExpensiveSale() == null) {
            fileInformation.setExpensiveSale(maxSaleItem);
            return;
        }

        BigDecimal previousSaleItem = fileInformation.getExpensiveSale().getPrice().multiply(new BigDecimal(fileInformation.getExpensiveSale().getQuantity()));
        BigDecimal currentSaleItem = maxSaleItem.getPrice().multiply(new BigDecimal(maxSaleItem.getQuantity()));


        if (currentSaleItem.compareTo(previousSaleItem) > 0) {
            fileInformation.setExpensiveSale(maxSaleItem);
        }
    }
}
