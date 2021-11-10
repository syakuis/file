package io.github.syakuis;

import io.github.syakuis.file.support.HttpImageFileLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-11-09
 */
@Slf4j
class HttpFileLoader {
    private long beforeTime = 0;
    private final String uri = "https://image.news1.kr/system/photos/2021/10/20/5024865/article.jpg/dims/optimize";


    @BeforeEach
    public void init() {
        this.beforeTime = System.currentTimeMillis();
    }

    @AfterEach
    public void exit() {
        long afterTime = System.currentTimeMillis();
        log.debug("{} = {} - {}", afterTime - beforeTime, afterTime, beforeTime);
    }

    @Test
    void test() throws Exception {
        HttpImageFileLoader.save(uri, "./temp/image.jpeg");
    }
}
