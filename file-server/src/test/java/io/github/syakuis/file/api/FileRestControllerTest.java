package io.github.syakuis.file.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Seok Kyun. Choi.
 * @since 2022-04-06
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(
    classes = {FileRestController.class},
    properties = {
        "file-server.repository.path=/Users/sgchoi2/develop/github/file/file-server/repository"
    }
)
class FileRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void download() throws Exception {
        mvc.perform(get("/file/v1/files/download/{filename}", "exceljs-4.3.0.tar.gz"))
            .andExpect(status().isOk())
        ;
    }
}