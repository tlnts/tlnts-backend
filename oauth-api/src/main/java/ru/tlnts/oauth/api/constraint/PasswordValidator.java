package ru.tlnts.oauth.api.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

	public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		return StringUtils.isNotBlank(password) && PASSWORD_PATTERN.matcher(password).matches();
	}

}