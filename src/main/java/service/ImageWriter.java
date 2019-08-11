package service;

import org.ddemidiuk.example.images.configuration.properties.ApplicationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ImageWriter {

    private final Queue<Image> imageQueue = new LinkedBlockingQueue<>();
    private final RestTemplate restTemplate;
    private final int imageMaxSize;

    public ImageWriter(RestTemplate restTemplate, ApplicationProperties properties) {
        this.restTemplate = restTemplate;
        this.imageMaxSize = properties.getImageMaxSize();
    }

    public void addToQueue(String imageName, String url, boolean rewrite) throws IOException, URISyntaxException {
        URI uri = new URI(url);
        HttpHeaders httpHeaders = restTemplate.headForHeaders(uri);
        validateHeaders(httpHeaders);
        ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
            Image image = new Image(imageName, response.getBody(), rewrite);
            boolean isAdded = imageQueue.offer(image);
            if (!isAdded) {
                String errMsg = String.format("Can't save image %s because the image buffer is full", imageName);
                throw new IOException(errMsg);
            }
        }
    }

    private void validateHeaders(HttpHeaders httpHeaders) throws IOException {
        if (httpHeaders.getContentLength() > imageMaxSize) {
            String errMsg = String.format("Image is too big. Max image size is  %d.", imageMaxSize);
            throw new IOException(errMsg);
        }

        if (httpHeaders.getContentType() == null || !MediaType.IMAGE_JPEG_VALUE.equals(httpHeaders.getContentType().getType())) {
            String errMsg = String.format("Unsupported media type %s.", httpHeaders.getContentType());
            throw new IOException(errMsg);
        }
    }
}
