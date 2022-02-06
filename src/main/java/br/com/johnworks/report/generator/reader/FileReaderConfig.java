package br.com.johnworks.report.generator.reader;

import br.com.johnworks.report.generator.domain.CustomerLayout;
import br.com.johnworks.report.generator.domain.Layout;
import br.com.johnworks.report.generator.domain.SaleLayout;
import br.com.johnworks.report.generator.domain.SellerLayout;
import br.com.johnworks.report.generator.util.SearchFile;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FileReaderConfig {

    @Value("${app.batch.file-path-in}")
    private String filePath;


    private final ResourceLoader resourceLoader;


    public FileReaderConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @StepScope
    @Bean
    public MultiResourceItemReader<Layout> multiResourceItemReader() throws IOException {

        final var resources = SearchFile.searchFiles(filePath);

        MultiResourceItemReader<Layout> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(resources.toArray(size -> new Resource[size]));
        resourceItemReader.setDelegate(reader());

        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader reader() throws IOException {
        return new FlatFileItemReaderBuilder<Layout>()
                .name("flatFileItemReader")
                .strict(false)
                .lineMapper(lineMapper())
                .build();
    }

    @Bean
    public PatternMatchingCompositeLineMapper lineMapper() {
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        lineMapper.setTokenizers(tokenizers());
        lineMapper.setFieldSetMappers(fieldSetMappers());
        return lineMapper;
    }

    private Map<String, FieldSetMapper> fieldSetMappers() {
        Map<String, FieldSetMapper> fieldSetMapperMap = new HashMap<>();
        fieldSetMapperMap.put("001*", fieldSetMapper(SellerLayout.class));
        fieldSetMapperMap.put("002*", fieldSetMapper(CustomerLayout.class));
        fieldSetMapperMap.put("003*", fieldSetMapper(SaleLayout.class));
        return fieldSetMapperMap;
    }

    private FieldSetMapper fieldSetMapper(Class clazz) {
        BeanWrapperFieldSetMapper mapper = new BeanWrapperFieldSetMapper();
        mapper.setTargetType(clazz);
        return mapper;
    }

    private Map<String, LineTokenizer> tokenizers() {
        Map<String, LineTokenizer> tokenizers = new HashMap<>();
        tokenizers.put("001*", tokenizersSellerLayout());
        tokenizers.put("002*", tokenizersCustomerLayout());
        tokenizers.put("003*", tokenizersSaleLayout());
        return tokenizers;
    }

    private LineTokenizer tokenizersSaleLayout() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("saleId", "itemSaleText", "sellerName");
        delimitedLineTokenizer.setIncludedFields(1, 2, 3);
        delimitedLineTokenizer.setDelimiter("ç");
        return delimitedLineTokenizer;
    }

    private LineTokenizer tokenizersSellerLayout() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("document", "name", "salary");
        delimitedLineTokenizer.setIncludedFields(1, 2, 3);
        delimitedLineTokenizer.setDelimiter("ç");
        return delimitedLineTokenizer;
    }

    private LineTokenizer tokenizersCustomerLayout() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("document", "name", "businessArea");
        delimitedLineTokenizer.setIncludedFields(1, 2, 3);
        delimitedLineTokenizer.setDelimiter("ç");
        return delimitedLineTokenizer;
    }

}
