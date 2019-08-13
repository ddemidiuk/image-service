package org.ddemidiuk.example.images.service;

import org.ddemidiuk.example.images.configuration.properties.ApplicationProperties;
import org.ddemidiuk.example.images.entity.Image;
import org.ddemidiuk.example.images.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Service
class AsyncImageSaver {
    private final ImageRepository imageRepository;
    private final long imageBufferMaxSize;
    private final AtomicLong imageBufferSize = new AtomicLong(0);

    public AsyncImageSaver(ImageRepository imageRepository, ApplicationProperties properties) {
        this.imageRepository = imageRepository;
        this.imageBufferMaxSize = properties.getImageBufferMaxSizeMb();
    }

    public CompletableFuture<Image> save(Image image, boolean rewrite) {
        if (imageBufferSize.get() > imageBufferMaxSize) {
            String errMsg = String.format("Can not save image %s because the image queue is full", image.getName());
            throw new RuntimeException(errMsg);
        }
        if (!rewrite && imageRepository.existsById(image.getName())) {
            String errMsg = String.format("Can not save image %s because it already exist", image.getName());
            throw new RuntimeException(errMsg);
        }

        imageBufferSize.addAndGet(image.getSize());

        return CompletableFuture.supplyAsync(() -> imageRepository.save(image))
                .whenComplete((resultImage, e) -> imageBufferSize.addAndGet(-image.getSize()))
                .exceptionally(e -> {
                    String errMsg = String.format("Error during saving image %s", image.getName());
                    throw new RuntimeException(errMsg, e);
                });
    }
}
