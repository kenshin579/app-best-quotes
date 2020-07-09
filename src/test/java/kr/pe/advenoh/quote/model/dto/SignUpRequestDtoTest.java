package kr.pe.advenoh.quote.model.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SignUpRequestDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private SignUpRequestDto signUpRequestDto;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void 정상요청인_경우에는_오류가_없다() {
        signUpRequestDto = SignUpRequestDto.builder()
                .name("Frank")
                .username("test")
                .email("hello@sdf.com")
                .password("password")
                .build();

        Set<ConstraintViolation<SignUpRequestDto>> violations = validator.validate(signUpRequestDto);
        assertThat(violations.size()).isZero();
    }
}