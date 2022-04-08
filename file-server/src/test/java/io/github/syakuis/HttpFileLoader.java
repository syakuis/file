package io.github.syakuis;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.junit.jupiter.api.Test;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-11-09
 */
class HttpFileLoader {
    private final String uri = "https://image.news1.kr/system/photos/2021/10/20/5024865/article.jpg/dims/optimize";

    @Test
    void test() throws Exception {
        URL url = new URL(uri);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("./temp/image.jpeg");
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, readBytes);
        }
    }
}
