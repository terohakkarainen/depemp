package fi.thakki.depemp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;
import fi.thakki.depemp.dao.GenericDao;
import fi.thakki.depemp.dto.ErrorResponseDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.util.StringUtil;

@ActiveProfiles("errorControllerTest")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ErrorControllerTest {

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
        assertThat(errorResult.getErrorMessage())
                .isEqualTo(String.format(ErrorController.ERROR_MESSAGE_FORMAT, errorMessage));
    }
}
