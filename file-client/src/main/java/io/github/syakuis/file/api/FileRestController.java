package io.github.syakuis.file.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/file/v1/files")
public class FileRestController {

    @Value("${file-server.uri}")
    private String fileServerUri;

    @GetMapping("/download")
    public void download(String filename) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = fileServerUri + "/file/v1/files/download/{filename}";

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class, filename);
    }
}
