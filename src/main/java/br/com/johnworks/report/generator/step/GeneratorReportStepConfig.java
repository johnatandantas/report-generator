package br.com.johnworks.report.generator.step;

import br.com.johnworks.report.generator.reader.FileInformationReader;
import br.com.johnworks.report.generator.tasklet.FileMoveTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorReportStepConfig {

    @Autowired
    public StepBuilderFactory factory;

    @Autowired
    private FileMoveTasklet task;


    @Value("${app.batch.file-path-in}")
    private String filePath;

    @Bean
    public Step stepGeneratorReport(
            MultiResourceItemReader multiResourceItemReader,
            FlatFileItemWriter itemWriter
    ) {
        return factory
                .get("stepGeneratorReport")
                .chunk(1)
                .reader(new FileInformationReader(multiResourceItemReader))
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step moveFileStep2() {
        return factory.get("moveFileStep2")
                .tasklet(task)
                .build();
    }
}
