package io.github.syakuis.file.support;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-11-11
 */
@Slf4j
public class HttpImageFileLoader {
    public static void save(String uri, String target) {
        try {
            URL url = new URL(uri);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, readBytes);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
