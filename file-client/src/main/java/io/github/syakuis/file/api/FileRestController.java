package io.github.syakuis.file.api;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-04-06
 */
@Slf4j
@RestController
@RequestMapping("/file/v1/files")
public class FileRestController {

    @Value("${file-server.uri}")
    private String fileServerUri;

    @Value("${file-client.repository.path}")
    private String fileClientRepositoryPath;

    @GetMapping("/download/{filename}")
    public void download(@PathVariable("filename") String filename) {
        long start = System.currentTimeMillis();
        log.info("파일 다운 준비");
        RestTemplate restTemplate = new RestTemplate();
        String uri = fileServerUri + "/file/v1/files/download/{filename}";
        ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class, filename);
        byte[] data = response.getBody();
        log.info("파일 다운 완료 : {} ms", System.currentTimeMillis() - start);

        log.info("파일 생성 준비");
        Path path = Paths.get(fileClientRepositoryPath, filename);
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            int len = data.length;
            int rem = len;
            while (rem > 0) {
                int n = Math.min(rem, BUFFER_SIZE);
                outputStream.write(data, (len-rem), n);
                rem -= n;
                log.debug("len : {}, rem : {}, n: {}", len, rem, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("파일 생성 완료 : {} ms", System.currentTimeMillis() - start);
    }
}
