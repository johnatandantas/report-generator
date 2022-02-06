package br.com.johnworks.report.generator.job;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GeneratorReportJobConfig {

    public final JobBuilderFactory factory;

    @Bean
    public Job jobGeneratorReport(
            Step stepGeneratorReport,
            Step moveFileStep2) {
        return factory
                .get("jobGeneratorReport")
                .start(stepGeneratorReport)
                .next(moveFileStep2)
                .incrementer(new RunIdIncrementer())
                .build();
    }

}
