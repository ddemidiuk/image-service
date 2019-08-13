package org.ddemidiuk.example.images.repository;

import org.apache.commons.io.FileUtils;
import org.ddemidiuk.example.images.configuration.properties.ApplicationProperties;
import org.ddemidiuk.example.images.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FileSystemImageRepository implements ImageRepository {
    private Path storePath;

    public FileSystemImageRepository(ApplicationProperties properties) throws IOException {
        this.storePath = Paths.get(properties.getFilesystemStorePath());

        File storeFile = storePath.toFile();
        if (!Files.exists(storePath) || !storeFile.isDirectory()) {
            FileUtils.forceMkdir(storeFile);
        }
    }

    @Override
    public synchronized Set<String> list() {
        try {
            return Files.list(storePath)
                    .filter(path -> !Files.isDirectory(path))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            String errMsg = "Can not get list of names of images";
            throw new RuntimeException(errMsg, e);
        }
    }

    @Override
     public synchronized Image save(Image image) {
        File newFile = storePath.resolve(image.getName()).toFile();
        try {
            FileUtils.writeByteArrayToFile(newFile, image.getData());
            return new Image(image.getName(), image.getSize());
        } catch (IOException e) {
            String errMsg = String.format("Can not save image %s", image.getName());
            throw new RuntimeException(errMsg, e);
        }
    }

    @Override
    public synchronized Optional<Image> findById(String imageName) {
        if (existsById(imageName)) {
            return Optional.empty();
        }
        try {
            byte[] data = FileUtils.readFileToByteArray(storePath.resolve(imageName).toFile());
            return Optional.of(new Image(imageName, data));
        } catch (IOException e) {
            String errMsg = String.format("Can not find image %s", imageName);
            throw new RuntimeException(errMsg, e);
        }
    }

    @Override
    public synchronized boolean existsById(String imageName) {
        File file = storePath.resolve(imageName).toFile();
        return file.exists();
    }

    @Override
    public synchronized void deleteById(String imageName) {
        File file = storePath.resolve(imageName).toFile();
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            String errMsg = String.format("Can not delete image %s", imageName);
            throw new RuntimeException(errMsg, e);
        }
    }

    @Override
    public synchronized long count() {
        try {
            return Files.list(storePath)
                    .filter(path -> !Files.isDirectory(path))
                    .count();
        } catch (IOException e) {
            String errMsg = "Can not get count of images";
            throw new RuntimeException(errMsg, e);
        }
    }

    @Override
    public synchronized Iterable<Image> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Page<Image> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized <S extends Image> Iterable<S> saveAll(Iterable<S> images) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Iterable<Image> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Iterable<Image> findAllById(Iterable<String> ImagesNames) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void delete(Image image) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteAll(Iterable<? extends Image> images) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
