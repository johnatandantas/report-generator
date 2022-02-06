package br.com.johnworks.report.generator.tasklet;

import br.com.johnworks.report.generator.util.SearchFile;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileMoveTasklet implements Tasklet {

    @Value("${app.batch.file-path-in}")
    private String filePath;

    @Value("${app.batch.file-path-finish}")
    private String targetFile;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {

        if (!new File(targetFile).exists()) {
            new File(targetFile).mkdir();
        }

        Resource[] resources = SearchFile.searchFiles(filePath).toArray(size -> new Resource[size]);

        for (Resource r : resources) {

            Files.move(r.getFile().toPath(), Paths.get(targetFile + r.getFilename()));

        }

        return RepeatStatus.FINISHED;
    }

}
