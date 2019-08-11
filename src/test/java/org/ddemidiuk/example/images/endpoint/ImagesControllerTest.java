package org.ddemidiuk.example.images.endpoint;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 * Test for {@code HealthCheckEndpoint}.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ImagesController.class)
public class ImagesControllerTest {
    /*@MockBean
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSaveImageInBase64() throws Exception {
        String imageInBase64 = encoder("images/my car.jpg");
        String requestContentJson = createContentJson("my_car", imageInBase64);

        String resultContent = mvc.perform(get("/api/images")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestContentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ImageResponseDto responseDto = mapper.readValue(resultContent, ImageResponseDto.class);
        assertEquals(responseDto., 1);
        assertEquals(responseDto.getImageIds().get(0), "my-car-0");
    }

    public static String encoder(String imagePath) throws IOException {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        }
        return base64Image;
    }

    public static void decoder(String base64Image, String pathFile) throws IOException {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        }
    }

    private String createContentJson(String name, String imageInBase64) {
        return String.format("[" +
                        "{" +
                        "   name:" + "\"%s\"" +
                        "   imageInBase64:" + "\"%s\"" +
                        "}" +
                        "]",
                name, imageInBase64);
    }*/

}
