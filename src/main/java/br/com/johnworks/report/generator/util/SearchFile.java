package br.com.johnworks.report.generator.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SearchFile {

    public static List<Resource> searchFiles(String filePath) throws IOException {
        List<Resource> resources = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(filePath))) {

            paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().toLowerCase())
                    .filter(path -> path.endsWith("dat"))
                    .forEach(path -> {
                        resources.add(new FileSystemResource(path));
                    });
        }

        return resources;
    }
}
