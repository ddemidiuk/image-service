package org.ddemidiuk.example.images.service;

import org.ddemidiuk.example.images.configuration.properties.ApplicationProperties;
import org.ddemidiuk.example.images.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RestTemplate restTemplate;
    private final int imageMaxSize;
    private final AsyncImageSaver asyncImageSaver;

    @Autowired
    public ImageServiceImpl(AsyncImageSaver asyncImageSaver, RestTemplate restTemplate, ApplicationProperties properties) {
        this.asyncImageSaver = asyncImageSaver;
        this.restTemplate = restTemplate;
        this.imageMaxSize = properties.getImageMaxSizeKb();
    }

    public void save(String imageName, byte[] data, boolean rewrite, boolean async) {
        CompletableFuture<Image> resultFuture = asyncImageSaver.save(new Image(imageName, data), rewrite);
        if (async) {
            return;
        }
        try {
            resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Image is not saved", e);
        }
    }

    public void save(String imageName, String url, boolean rewrite, boolean async) {
        save(imageName, getImageData(url), rewrite, async);
    }

    private byte[] getImageData(String url) {
        HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
        try {
            validateHeaders(httpHeaders);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            String errMsg = String.format("HTTP status must by OK but getten %s. URL %s", response.getStatusCode(), url);
            throw new RuntimeException(errMsg);
        }
        if (!response.hasBody()) {
            String errMsg = String.format("There is no body in response. URL %s", url);
            throw new RuntimeException(errMsg);
        }
        return response.getBody();
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
