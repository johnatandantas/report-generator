package br.com.johnworks.report.generator.reader.strategy;

import br.com.johnworks.report.generator.domain.FileInformation;
import br.com.johnworks.report.generator.domain.Layout;

public interface FileInformationResultStrategy {

    void execute(FileInformation fileInformation, Layout layout);
}
