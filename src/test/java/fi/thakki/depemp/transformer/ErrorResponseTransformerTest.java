package fi.thakki.depemp.transformer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.validation.Errors;

import fi.thakki.depemp.dto.ErrorResponseDto;
import fi.thakki.depemp.util.StringUtil;

public class ErrorResponseTransformerTest {

    private ErrorResponseTransformer myTransformerUnderTest = new ErrorResponseTransformer();

    @Test(expected = NullPointerException.class)
    public void toErrorResponseDtoHandlesNullErrors() {
        myTransformerUnderTest.toErrorResponseDto((Errors) null);
    }

    @Test
    public void toErrorResponseDtoHandlesNullString() {
        ErrorResponseDto result = myTransformerUnderTest.toErrorResponseDto((String) null);
        assertThat(result.errorMessage).isNull();
    }

    @Test(expected = NullPointerException.class)
    public void toErrorResponseDtoHandlesNulLArguments() {
        myTransformerUnderTest.toErrorResponseDto(null, (String[]) null);
    }

    @Test(expected = NullPointerException.class)
    public void toErrorResponseDtoHandlesNoDetails2() {
        myTransformerUnderTest.toErrorResponseDto(StringUtil.randomString(), (String[]) null);
    }

    @Test
    public void toErrorResponseDtoHandlesNoDetails() {
        String errorMessage = StringUtil.randomString();

        ErrorResponseDto result = myTransformerUnderTest.toErrorResponseDto(errorMessage,
                new String[] {});

        assertThat(result.errorMessage).isEqualTo(errorMessage);
        assertThat(result.details).isEmpty();
    }

    @Test
    public void toErrorResponseDtoHandlesManyDetails() {
        String errorMessage = StringUtil.randomString();
        String detail1 = StringUtil.randomString();
        String detail2 = StringUtil.randomString();

        ErrorResponseDto result = myTransformerUnderTest.toErrorResponseDto(errorMessage, detail1,
                detail2);

        assertThat(result.errorMessage).isEqualTo(errorMessage);
        assertThat(result.details).contains(detail1, detail2);
    }
}
