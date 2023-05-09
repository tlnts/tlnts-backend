package ru.tlnts.oauth.api.model;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Value
public class UserInfo {

	@NotNull
	String email;

	@NotNull
	Boolean active;

	@NotEmpty
	Set<UserRole> roles;

	@NotNull
	LocalDateTime dateTimeCreate;

}
