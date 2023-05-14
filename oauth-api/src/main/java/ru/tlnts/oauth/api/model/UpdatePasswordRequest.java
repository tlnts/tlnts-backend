package ru.tlnts.oauth.api.model;

import lombok.Value;
import ru.tlnts.oauth.api.constraint.Password;

@Value
public class UpdatePasswordRequest {

	@Password
	String password;

}
