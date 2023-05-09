package ru.tlnts.oauth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tlnts.common.api.ApiResponse;
import ru.tlnts.oauth.api.model.CreateUserRequest;
import ru.tlnts.oauth.api.model.UpdatePasswordRequest;
import ru.tlnts.oauth.api.model.UserInfo;
import ru.tlnts.oauth.api.model.UpdateEmailRequest;

/**
 * @author mamedov
 */
public interface OAutUserApi {

	String PATH_PREFIX = "/api/user";
	String PATH_USER_EMAIL = "/api/user/{email}";

	String PATH_CREATE_USER = PATH_PREFIX;
	String PATH_GET_USER = PATH_USER_EMAIL;
	String PATH_UPDATE_EMAIL = PATH_USER_EMAIL + "/update-email";
	String PATH_UPDATE_PASSWORD = PATH_USER_EMAIL + "/update-password";
	String PATH_ENABLE_USER = PATH_USER_EMAIL + "/enable";
	String PATH_DISABLE_USER = PATH_USER_EMAIL + "/disable";

	@PostMapping(PATH_CREATE_USER)
	ApiResponse<Void> createUser(@RequestBody CreateUserRequest request);

	@GetMapping(PATH_GET_USER)
	ApiResponse<UserInfo> getUser(@PathVariable("email") String email);

	@PutMapping(PATH_UPDATE_EMAIL)
	ApiResponse<Void> updateUserEmail(@PathVariable("email") String email, @RequestBody UpdateEmailRequest request);

	@PutMapping(PATH_UPDATE_PASSWORD)
	ApiResponse<Void> updateUserPassword(@PathVariable("email") String email, @RequestBody UpdatePasswordRequest request);

	@PutMapping(PATH_ENABLE_USER)
	ApiResponse<Void> enableUser(@PathVariable("email") String email);

	@PutMapping(PATH_DISABLE_USER)
	ApiResponse<Void> disableUser(@PathVariable("email") String email);

}
