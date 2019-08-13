package org.ddemidiuk.example.images.repository;

import org.ddemidiuk.example.images.entity.Image;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface ImageRepository extends KeyValueRepository<Image, String> {
    Iterable<String> list();
}
