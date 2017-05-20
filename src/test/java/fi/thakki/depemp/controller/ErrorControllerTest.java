package fi.thakki.depemp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.dao.GenericDao;
import fi.thakki.depemp.dto.ErrorResponseDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.util.StringUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ErrorControllerTest {

    @TestConfiguration
    public static class MyConfiguration {

        @Bean
        @Primary
        public GenericDao genericDao() {
            return Mockito.mock(GenericDao.class);
        }
    }
    
    @Autowired
    private GenericDao myMockedGenericDao;

    @Autowired
    private TestRestTemplate myRestTemplate;

    @Test
    public void runtimeExceptionIsCaughtByErrorController() throws Exception {
        String errorMessage = StringUtil.randomString(30);
        Mockito.when(myMockedGenericDao.findAll(Department.class))
                .thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<ErrorResponseDto> result = myRestTemplate.getForEntity("/departments",
                ErrorResponseDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        ErrorResponseDto errorResult = result.getBody();
        assertThat(errorResult.errorMessage).isEqualTo(ErrorController.ERROR_MESSAGE_FORMAT);
        assertThat(errorResult.details.size()).isEqualTo(1);
        assertThat(errorResult.details).contains(errorMessage);
    }
}
