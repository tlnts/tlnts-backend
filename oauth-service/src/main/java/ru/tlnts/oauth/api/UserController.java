package ru.tlnts.oauth.api;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tlnts.oauth.api.model.CreateUserRequest;
import ru.tlnts.oauth.api.model.UpdateEmailRequest;
import ru.tlnts.oauth.api.model.UpdatePasswordRequest;
import ru.tlnts.oauth.api.model.UserInfo;
import ru.tlnts.oauth.storage.user.UserService;

/**
 * @author mamedov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

	private final UserService userService;

	@PostMapping(OAutUserApi.PATH_CREATE_USER)
	public void createUser(@RequestBody @Valid CreateUserRequest request) {
		userService.createUser(request);
	}

	@GetMapping(OAutUserApi.PATH_GET_USER)
	public UserInfo getUser(@PathVariable("email") String email) {
		return userService.getUser(email);
	}

	@PutMapping(OAutUserApi.PATH_UPDATE_EMAIL)
	public void updateUserEmail(@PathVariable("email") String email, @RequestBody @Valid UpdateEmailRequest request) {
		userService.updateUserEmail(email, request);
	}

	@PutMapping(OAutUserApi.PATH_UPDATE_PASSWORD)
	public void updateUserPassword(@PathVariable("email") String email, @RequestBody @Valid UpdatePasswordRequest request) {
		userService.updateUserPassword(email, request);
	}

	@PutMapping(OAutUserApi.PATH_ENABLE_USER)
	public void enableUser(@PathVariable("email") String email) {
		userService.enableUser(email);
	}

	@PutMapping(OAutUserApi.PATH_DISABLE_USER)
	public void disableUser(@PathVariable("email") String email) {
		userService.disableUser(email);
	}

}
