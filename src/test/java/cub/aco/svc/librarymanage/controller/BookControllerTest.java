package cub.aco.svc.librarymanage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cub.aco.svc.librarymanage.dto.ActionDetail;
import cub.aco.svc.librarymanage.dto.ActionResponse;
import cub.aco.svc.librarymanage.enums.ErrorCode;
import cub.aco.svc.librarymanage.exception.ErrorException;
import cub.aco.svc.librarymanage.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void testAddBook() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:add-book.json");

        testData.forEach(data -> {
            try {
                final String input = objectMapper.writeValueAsString(data.get("input"));
                final String expect = objectMapper.writeValueAsString(data.get("expect"));

                final MockHttpServletResponse response = mockMvc.perform(post("/book/add")
                                .content(input).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testAddBook_returnDataExistsFail() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:add-book.json");
        final String input = objectMapper.writeValueAsString(testData.get(0).get("input"));
        final String expect = objectMapper.writeValueAsString(ActionResponse.builder()
                        .mwHeader(ActionDetail.builder()
                                .returnCode("E001")
                                .returnDesc("交易失敗, 資料已存在")
                                .build())
                .build());
        doThrow(new ErrorException(ErrorCode.DATA_EXISTS)).when(bookService).create(any());

        final MockHttpServletResponse response = mockMvc.perform(post("/book/add")
                        .content(input).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
    }

    @Test
    void testModifyBook() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:modify-book.json");
        testData.forEach(data -> {
            try {
                final String input = objectMapper.writeValueAsString(data.get("input"));
                final String expect = objectMapper.writeValueAsString(data.get("expect"));

                final MockHttpServletResponse response = mockMvc.perform(post("/book/modify")
                                .content(input).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testModifyBook_returnDataNotExistsFail() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:modify-book.json");
        final String input = objectMapper.writeValueAsString(testData.get(0).get("input"));
        final String expect = objectMapper.writeValueAsString(ActionResponse.builder()
                .mwHeader(ActionDetail.builder()
                        .returnCode("E001")
                        .returnDesc("交易失敗, 資料不存在")
                        .build())
                .build());
        doThrow(new ErrorException(ErrorCode.DATA_NOT_FOUND)).when(bookService).update(any());

        final MockHttpServletResponse response = mockMvc.perform(post("/book/modify")
                        .content(input).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
    }

    @Test
    void testDeleteBook() throws Exception {
        List<Map<String, Object>> testData = getTestData("classpath:delete-book.json");
        testData.forEach(data -> {
            try {
                final String input = objectMapper.writeValueAsString(data.get("input"));
                final String expect = objectMapper.writeValueAsString(data.get("expect"));

                final MockHttpServletResponse response = mockMvc.perform(post("/book/delete")
                                .content(input).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testQueryBook() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:query-book.json");
        testData.forEach(data -> {
            try {
                final String input = objectMapper.writeValueAsString(data.get("input"));
                final String expect = objectMapper.writeValueAsString(data.get("expect"));

                final MockHttpServletResponse response = mockMvc.perform(post("/book/query")
                                .content(input).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testBorrowBook() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:borrow-book.json");

        testData.forEach(data -> {
            try {
                final String input = objectMapper.writeValueAsString(data.get("input"));
                final String expect = objectMapper.writeValueAsString(data.get("expect"));

                final MockHttpServletResponse response = mockMvc.perform(post("/book/borrow")
                                .content(input).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testReturnBook() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:return-book.json");
        testData.forEach(data -> {
            try {
                final String input = objectMapper.writeValueAsString(data.get("input"));
                final String expect = objectMapper.writeValueAsString(data.get("expect"));

                final MockHttpServletResponse response = mockMvc.perform(post("/book/return")
                                .content(input).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testQueryOverdueBook() throws Exception {
        final List<Map<String, Object>> testData = getTestData("classpath:query-overdue-book.json");
        testData.forEach(data -> {
            try {
                final String input = objectMapper.writeValueAsString(data.get("input"));
                final String expect = objectMapper.writeValueAsString(data.get("expect"));

                final MockHttpServletResponse response = mockMvc.perform(post("/book/overdue")
                                .content(input).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString(StandardCharsets.UTF_8)).isEqualTo(expect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<Map<String, Object>> getTestData(String filePath) throws Exception {
        final File file = ResourceUtils.getFile(filePath);
        return objectMapper.readValue(file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
    }


}
