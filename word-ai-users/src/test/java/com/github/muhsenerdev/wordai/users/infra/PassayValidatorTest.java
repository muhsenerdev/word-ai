package com.github.muhsenerdev.wordai.users.infra;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.github.muhsenerdev.wordai.users.domain.PasswordValidationException;

@ContextConfiguration(classes = {
        PassayPasswordValidator.class,
        PassayConfig.class
})
@EnableConfigurationProperties(PasswordProps.class)
@SpringBootTest
@TestPropertySource(properties = {
        "validation.password.allowWhitespace=true",
        "validation.password.minLength=8",
        "validation.password.maxLength=100",
        "validation.password.minLowercase=1",
        "validation.password.minDigit=1",
        "validation.password.minSpecial=1",
})
public class PassayValidatorTest {

    @Autowired
    private PassayPasswordValidator validator;

    @Autowired
    private PasswordProps props;

    @Test
    public void should_setup_is_ok() {
        assertNotNull(validator);
        assertNotNull(props);
        assertEquals(8, props.getMinLength(), "minLength must be 8");
        assertEquals(100, props.getMaxLength(), "maxLength must be 100");
        assertEquals(1, props.getMinLowercase(), "minLowercase must be 1");
        assertEquals(1, props.getMinDigit(), "minDigit must be 1");
        assertEquals(1, props.getMinSpecial(), "minSpecial must be 1");
        assertEquals(true, props.isAllowWhitespace(), "allowWhitespace must be true");
    }

    @Test
    @DisplayName("when null argument, should throw password-validation-exception")
    void all_whenNull_thenThrowsPasswordValidationException() {
        assertThrows(PasswordValidationException.class, () -> validator.isValid(null));
        assertThrows(PasswordValidationException.class, () -> validator.validate(null));
        assertThrows(PasswordValidationException.class, () -> validator.validateOrThrow(null));
    }

    static Stream<String> invalidPasswordsStream() {
        return Stream.of(
                "short", // Too short (minLength=8)
                "12345", // Too short
                "abc", // Too short
                "NOLOWERCASE1!", // No lowercase (minLowercase=1)
                "UPPERCASEANDDIGITS123!", // No lowercase
                "nodigit!", // No digit (minDigit=1)
                "NoDigitsHere!", // No digit
                "nospecial1", // No special character (minSpecial=1)
                "NoSpecialChars123", // No special character
                "thisisalongpasswordthatiswaytoolongandshouldfailthemaxlengthrulebecauseitexceedsonehundredcharactersinlengthanditshouldbeinvaliasdasdasdasdasdasdasdasd", // Too
                // long
                // (maxLength=100)
                "thispasswordisdefinitelywaytoolongtobeconsideredvalidaccordingtothemaximumlengthrequirementofonehundredcharacters" // Too
                                                                                                                                    // long
        );
    }

    static Stream<String> validPasswordsStream() {
        return Stream.of(
                "Password1!",
                "aB1!cDeF",
                "P@ssw0rd!!",
                "white space is allowedéD4!");
    }

    // isValid()

    @ParameterizedTest
    @MethodSource("invalidPasswordsStream")
    void isValid_whenInvalid_shouldReturnFalse(String pass) {

        boolean isValid = validator.isValid(pass);

        assertFalse(isValid);

    }

    @ParameterizedTest
    @MethodSource("validPasswordsStream")
    void isValid_whenValid_shouldReturnTrue(String pass) {
        boolean valid = validator.isValid(pass);
        assertTrue(valid);
    }

    @ParameterizedTest
    @MethodSource("invalidPasswordsStream")
    void validate_whenInvalid_shouldReturnNonEmptyList(String pass) {

        List<String> messages = validator.validate(pass);
        assertFalse(messages.isEmpty());

    }

    @ParameterizedTest
    @MethodSource("validPasswordsStream")
    void validate_whenValid_shouldReturnmptyList(String pass) {

        List<String> messages = validator.validate(pass);
        assertTrue(messages.isEmpty());

    }

    @ParameterizedTest
    @MethodSource("validPasswordsStream")
    void validateOrThrow_whenValid_nothingToThrow(String pass) {
        assertDoesNotThrow(() -> validator.validateOrThrow(pass));
    }

    @ParameterizedTest
    @MethodSource("invalidPasswordsStream")
    void validateOrThrow_whenInvalid_thenThrows(String pass) {
        assertThrows(PasswordValidationException.class, () -> validator.validateOrThrow(pass));

    }

}
