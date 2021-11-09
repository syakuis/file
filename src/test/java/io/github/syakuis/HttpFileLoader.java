package io.github.syakuis;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-11-09
 */
class HttpFileLoader {
    private final String uri = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fimage.news1.kr%2Fsystem%2Fphotos%2F2021%2F10%2F20%2F5024865%2Farticle.jpg%2Fdims%2Foptimize&imgrefurl=https%3A%2F%2Fwww.news1.kr%2Farticles%2F%3F4480071&tbnid=acbEAa-xcXByBM&vet=12ahUKEwi54J-Qnov0AhVUAKYKHYogAJIQMygDegUIARDKAQ..i&docid=ggeAVhsyQi1aTM&w=560&h=840&itg=1&q=%ED%95%9C%EC%86%8C%ED%9D%AC&ved=2ahUKEwi54J-Qnov0AhVUAKYKHYogAJIQMygDegUIARDKAQ";

    @Test
    void test() throws Exception {
        URL url = new URL(uri);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("./image.jpeg");
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, readBytes);
        }

    }
}
