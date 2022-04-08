package io.github.syakuis.file.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-04-06
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FileRestClientTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String DOWNLOAD_PATH = "/Users/sgchoi2/develop/github/file/file-server/download";

    @Test
    void download() throws Exception {
        final String filename = "exceljs-4.3.0.tar.gz";

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        ResponseEntity<byte[]> response = restTemplate.exchange(
            "/file/v1/files/download/{filename}",
            HttpMethod.GET, request, byte[].class, filename);

        byte[] body = response.getBody();

        assertNotNull(response.getBody());
        assertEquals(16160566, response.getBody().length);

        assertTrue(fileSave(body, filename).toFile().exists());
    }

    private Path fileSave(byte[] data, String filename) throws IOException {
        Path path = Path.of(DOWNLOAD_PATH, filename);
        Files.write(path, data);
        return path;
    }
}
