package org.ddemidiuk.example.images.utils;

import org.ddemidiuk.example.images.entity.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static final String PREVIEW_FILE_PREFIX = "preview_";
    private static final int PREVIEW_WIDTH = 100;
    private static final int PREVIEW_HEIGHT = 100;

    private Image creatPreview(Image image) {
        BufferedImage img = new BufferedImage(PREVIEW_WIDTH, PREVIEW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        try {
            img.createGraphics().drawImage(ImageIO.read(new ByteArrayInputStream(image.getData()))
                    .getScaledInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

            ByteArrayOutputStream previewData = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", previewData);
            return new Image(PREVIEW_FILE_PREFIX.concat(image.getName()), previewData.toByteArray());
        } catch (IOException e) {
            String errMsg = String.format("Can not create preview of image %s", image.getName());
            throw new RuntimeException(errMsg, e);
        }
    }
}
