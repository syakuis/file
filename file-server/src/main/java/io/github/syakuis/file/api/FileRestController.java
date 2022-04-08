package io.github.syakuis.file.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-04-06
 */
@Slf4j
@RestController
@RequestMapping("/file/v1/files")
public class FileRestController {

    @Value("${file-server.repository.path}")
    private String fileServerRepositoryPath;

    private void setOctetStreamHeaders(HttpServletResponse response, File file, String filename) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        response.setHeader("Content-Transfer-Encoding", "binary");
        // https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Content-Disposition
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        // https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Cache-Control
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, must-revalidate");
    }

    @GetMapping(path = "/download/{filename}", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public void download(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        log.info("파일 전송 준비 !!!");
        Path path = Paths.get(fileServerRepositoryPath, filename);

        File file = path.toFile();

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        try (InputStream stream = Files.newInputStream(path); OutputStream outputStream = response.getOutputStream()) {
            log.info("파일 전송 전송 시작 !!!");
            long start = System.currentTimeMillis();
            stream.transferTo(outputStream);
            outputStream.flush();
            log.info("전송 완료 {} ms", System.currentTimeMillis() - start);

            setOctetStreamHeaders(response, file, filename);
        }

        log.info("파일 전송 완료 !!!");
    }
}
