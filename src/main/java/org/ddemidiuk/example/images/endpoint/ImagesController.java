package org.ddemidiuk.example.images.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.ddemidiuk.example.images.endpoint.dto.ImageInBase64Dto;
import org.ddemidiuk.example.images.endpoint.dto.ImageInUrlDto;
import org.ddemidiuk.example.images.endpoint.dto.ImageResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ddemidiuk.example.images.service.StoreFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

/**
 * Images controller
 */
@Api(value = "Availability checker.")
@RestController
@RequestMapping("/api/image")
public class ImagesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);
    private StoreFileService storeFileService;

    @Autowired
    public ImagesController(StoreFileService storeFileService) {
        this.storeFileService = storeFileService;
    }

    /**
     * Saves BASE64 encoded images
     *
     * @return list of statuses of saving operations.
     */
    @ApiOperation(value = "Save BASE64 encoded images", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageResponseDto> saveImageInBase64(@ApiParam(value = "JSON with BASE64 encoded images")
                                                    @RequestBody List<ImageInBase64Dto> imageInBase64Dtos) {

        List<ImageResponseDto> responseDtos = new ArrayList<>();
        imageInBase64Dtos.forEach(image -> {
            ImageResponseDto responseDto = new ImageResponseDto();
            responseDto.setImageFileName(image.getFileName());
            responseDtos.add(responseDto);

            byte[] data = Base64.getDecoder().decode(image.getImageInBase64());
            try {
                storeFileService.save(image.getFileName(), data, image.isRewrite());
                responseDto.setSuccessfullySaved(true);
            } catch (IOException e) {
                LOGGER.error("Error of saving file", e);
            }
        });

        return responseDtos;
    }

    /**
     * Saves images from urls
     *
     * @return list of statuses of saving images.
     */
    @ApiOperation(value = "Save images from urls", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/url", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageResponseDto> saveImageFromUrl(@ApiParam(value = "URLs to image")
                                                   @RequestBody List<ImageInUrlDto> imageUrls) {

        List<ImageResponseDto> responseDtos = new ArrayList<>();

        imageUrls.forEach(urlDto -> {
            ImageResponseDto responseDto = new ImageResponseDto();
            responseDto.setImageFileName(urlDto.getFileName());
            responseDtos.add(responseDto);
            try {
                storeFileService.save(urlDto.getFileName(), urlDto.getUrl(), urlDto.isRewrite());
                responseDto.setSuccessfullySaved(true);
            } catch (IOException e) {
                LOGGER.error("Error of saving image from url {}", urlDto, e);
            }
        });

        return responseDtos;
    }

    /**
     * Saves multi files of images
     *
     * @return list of statuses of saving images.
     */
    @ApiOperation(value = "Save images from urls", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "/saveMultiFile", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ImageResponseDto> saveMultiFile(@ApiParam(value = "files", required = true, allowMultiple = true)
                                                @RequestPart MultipartFile file) {

        List<ImageResponseDto> responseDtos = new ArrayList<>();

        ImageResponseDto responseDto = new ImageResponseDto();
        responseDto.setImageFileName(file.getOriginalFilename());
        responseDtos.add(responseDto);
        try {
            storeFileService.save(file.getOriginalFilename(), file.getBytes(), true);
            responseDto.setSuccessfullySaved(true);
        } catch (IOException e) {
            LOGGER.error("Error of saving image from url {}", file.getOriginalFilename(), e);
        }

        return responseDtos;
    }

    /**
     * gets list of names of images
     *
     * @return list of names of images.
     */
    @ApiOperation(value = "get list of stored images", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> getStoredImages() {
        try {
            return storeFileService.list();
        } catch (IOException e) {
            LOGGER.error("Can't get list of stored images", e);
        }
        return null;
    }

    /**
     * gets image by name
     *
     * @return image.
     */
    @ApiOperation(value = "get image", produces = MediaType.IMAGE_JPEG_VALUE)
    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String fileName) {
        try {
            return storeFileService.get(fileName);
        } catch (IOException e) {
            LOGGER.error("Can't get stored images {}", fileName, e);
        }
        return null;
    }
}
