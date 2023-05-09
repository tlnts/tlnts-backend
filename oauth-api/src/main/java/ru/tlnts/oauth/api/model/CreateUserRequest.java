package ru.tlnts.oauth.api.model;

import lombok.Value;
import ru.tlnts.oauth.api.constraint.Email;
import ru.tlnts.oauth.api.constraint.Password;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Value
public class CreateUserRequest {

	@Email
	String email;

	@Password
	String password;

	@NotNull
	Boolean active;

	@NotEmpty
	Set<UserRole> roles;

}
