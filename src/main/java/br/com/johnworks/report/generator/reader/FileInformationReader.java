package br.com.johnworks.report.generator.reader;

import br.com.johnworks.report.generator.domain.FileInformation;
import br.com.johnworks.report.generator.domain.Layout;
import br.com.johnworks.report.generator.reader.factory.FileInformationFactory;
import org.springframework.batch.item.*;

import java.util.Objects;

public class FileInformationReader implements ItemStreamReader<FileInformation> {

    private ItemStreamReader<Layout> delegator;

    private Layout layout;


    public FileInformationReader(ItemStreamReader<Layout> delegator) {
        this.delegator = delegator;
    }

    @Override
    public FileInformation read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        FileInformationFactory fileInformationFactory = new FileInformationFactory();

        FileInformation fileInformation = null;

        while ((layout = delegator.read()) != null) {

            if (Objects.isNull(fileInformation))
                fileInformation = new FileInformation();

            fileInformationFactory.factory(layout).execute(fileInformation, layout);

        }

        if (Objects.isNull(fileInformation))
            return null;

        return fileInformation;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegator.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegator.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegator.close();
    }
}
