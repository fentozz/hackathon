package org.bars.hackathon.service.accounts.validators;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.consts.Message;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class RegistrationRequestDTOValidator implements Validator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\S+?@sberbank\\.ru");

    private static final int PASSWORD_MIN_LENGTH = 8;

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RegistrationRequestDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationRequestDTO request = ((RegistrationRequestDTO) target);
        checkLogin(request.getLogin(), errors);
        checkPassword(request.getPassword(), request.getPasswordConfirm(), errors);
    }

    private void checkLogin(String login, Errors errors) {
        checkLoginNonNull(login, errors);
//        checkLoginValue(login, errors);
        checkLoginExist(login, errors);
    }

    private void checkLoginNonNull(String login, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", Message.LOGIN_INVALID.getMessage());
    }

    private void checkLoginValue(String login, Errors errors) {
        Matcher matcher = EMAIL_PATTERN.matcher(login);
        if (!matcher.matches()) {
            errors.rejectValue("login", Message.LOGIN_INVALID.getMessage());
        }
    }

    private void checkLoginExist(String login, Errors errors) {
        if (userRepository.findOneByLogin(login).isPresent()) {
            errors.rejectValue("login", Message.LOGIN_EXISTS.getMessage());
        }
    }

    private void checkPassword(String password, String repeatPassword, Errors errors) {
        checkPasswordNonNull(errors);
        checkPasswordValue(password, errors);
        checkEqualPasswords(password, repeatPassword, errors);
    }

    private void checkPasswordNonNull(Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", Message.PASSWORD_IS_EMPTY.getMessage());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", Message.PASSWORD_IS_EMPTY.getMessage());
    }

    private void checkPasswordValue(String password, Errors errors) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            errors.rejectValue("password", Message.PASSWORD_TOO_SHORT.getMessage());
        }
    }

    private void checkEqualPasswords(String password, String repeatPassword, Errors errors) {
        if (!password.equals(repeatPassword)) {
            errors.rejectValue("passwordConfirm", Message.PASSWORD_NOT_EQUAL.getMessage());
        }
    }
}