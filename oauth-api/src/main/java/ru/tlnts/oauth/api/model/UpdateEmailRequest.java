package ru.tlnts.oauth.api.model;

import lombok.Value;
import ru.tlnts.oauth.api.constraint.CommonConstants;
import ru.tlnts.oauth.api.constraint.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class UpdateEmailRequest {

	@Email
	String email;

}
