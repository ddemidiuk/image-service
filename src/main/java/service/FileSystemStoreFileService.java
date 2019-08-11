package service;

import org.apache.commons.io.FileUtils;
import org.ddemidiuk.example.images.configuration.properties.ApplicationProperties;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

public class FileSystemStoreFileService implements StoreFileService {

    private static final String PREVIEW_CATALOG_NAME = "previews";
    private static final int PREVIEW_WIDTH = 100;
    private static final int PREVIEW_HEIGHT = 100;

    private Path storePath;
    private Path previewPath;
    private int urlConnectTimeout;
    private int urlReadTimeout;

    public FileSystemStoreFileService(ApplicationProperties properties) throws IOException {
        this.storePath = Paths.get(properties.getStorePath());
        this.previewPath = storePath.resolve(PREVIEW_CATALOG_NAME);
        this.urlConnectTimeout = properties.getUrlConnectTimeout();
        this.urlReadTimeout = properties.getUrlReadTimeout();

        File storeFile = storePath.toFile();
        if (!Files.exists(storePath) || !storeFile.isDirectory()) {
            FileUtils.forceMkdir(storeFile);
        }

        File previewFile = previewPath.toFile();
        if (!Files.exists(previewPath) || !previewFile.isDirectory()) {
            FileUtils.forceMkdir(previewFile);
        }
    }

    @Override
    public void save(String fileName, byte[] data, boolean rewrite) throws IOException {
        File newFile = storePath.resolve(fileName).toFile();
        if (newFile.exists() && !rewrite) {
            throw new IOException(String.format("Can't save file %s because it already exist", newFile));
        }
        FileUtils.writeByteArrayToFile(newFile, data);

        savePreview(newFile, rewrite);
    }

    @Override
    public void save(String fileName, String url, boolean rewrite) throws IOException {
        File newFile = storePath.resolve(fileName).toFile();
        if (newFile.exists() && !rewrite) {
            throw new IOException(String.format("Can't save file %s because it already exist", newFile));
        }
        FileUtils.copyURLToFile(
                new URL(url),
                newFile,
                urlConnectTimeout,
                urlReadTimeout);

        savePreview(newFile, rewrite);
    }

    @Override
    public byte[] get(String fileName) throws IOException {
        return FileUtils.readFileToByteArray(storePath.resolve(fileName).toFile());
    }

    @Override
    public Set<String> list() throws IOException {
        return Files.list(storePath)
                .filter(path -> !Files.isDirectory(path))
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toSet());
    }

    private void savePreview(File file, boolean rewrite) throws IOException {
        File newPreviewFile = previewPath.resolve(file.getName()).toFile();
        if (newPreviewFile.exists() && !rewrite) {
            throw new IOException(String.format("Can't save preview file %s because it already exist", newPreviewFile));
        }
        BufferedImage img = new BufferedImage(PREVIEW_WIDTH, PREVIEW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        img.createGraphics().drawImage(ImageIO.read(file)
                .getScaledInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, java.awt.Image.SCALE_SMOOTH),0,0,null);
        ImageIO.write(img, "jpg", newPreviewFile);
    }
}
