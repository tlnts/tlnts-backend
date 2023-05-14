package ru.tlnts.oauth.api.constraint;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

	public static final Pattern EMAIL_PATTERN = Pattern.compile("[\\w-\\.]{1,276}+@([\\w-]{1,30}+\\.)+[\\w-]{2,4}");

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		return StringUtils.isNotBlank(password) && EMAIL_PATTERN.matcher(password).matches();
	}

}