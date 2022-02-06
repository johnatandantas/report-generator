package br.com.johnworks.report.generator.writer;

import br.com.johnworks.report.generator.domain.FileInformation;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@Configuration
public class FileWriteConfig {

    @Value("${app.batch.file-path-out}")
    private String filePath;

    @Bean
    public FlatFileItemWriter itemWriter() {
        return new FlatFileItemWriterBuilder()
                .name("itemWriter")
                .resource(new FileSystemResource(filePath + Path.of(now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".done.dat")))
                .lineAggregator(lineAggregator())
                .build();
    }

    private LineAggregator lineAggregator() {
        return new LineAggregator() {
            @Override
            public String aggregate(Object o) {
                FileInformation information = (FileInformation) o;
                String line = String.format("%d, %d, %s, %s",
                        information.getQuantityCustomer(),
                        information.getQuantitySeller(),
                        information.getExpensiveSale().getSaleItemId(),
                        information.getWorstSeller().getSellerName()
                );
                return line;
            }
        };
    }
}
