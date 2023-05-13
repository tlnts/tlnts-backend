package ru.tlnts.oauth.storage.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tlnts.common.exception.TlntsRuntimeException;
import ru.tlnts.oauth.api.model.CreateUserRequest;
import ru.tlnts.oauth.api.model.UpdateEmailRequest;
import ru.tlnts.oauth.api.model.UpdatePasswordRequest;
import ru.tlnts.oauth.api.model.UserInfo;
import ru.tlnts.oauth.mapper.UserMapper;
import ru.tlnts.oauth.storage.authorization.AuthorizationServiceSupport;
import ru.tlnts.oauth.storage.user.dao.UserDao;
import ru.tlnts.oauth.storage.user.entity.User;

import java.util.function.Function;

/**
 * @author mamedov
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final AuthorizationServiceSupport authorizationServiceSupport;

	@Transactional
	public void createUser(@NonNull CreateUserRequest request) {
		if (userDao.existsByEmail(request.getEmail())) {
			throw new TlntsRuntimeException("Пользователь c таким e-mail уже существует", HttpStatus.BAD_REQUEST);
		}

		User user = userMapper.map(request, passwordEncoder.encode(request.getPassword()));
		userDao.save(user);

		log.info("New user has been registered: {}", user.getEmail());
	}

	@Transactional(readOnly = true)
	public UserInfo getUser(@NonNull String email) {
		return findUserAndMap(email, userMapper::map);
	}

	@Transactional
	public void updateUserEmail(@NonNull String email, @NonNull UpdateEmailRequest request) {
		User user = findUser(email);

		String newEmail = request.getEmail();
		if (userDao.existsByEmail(newEmail)) {
			throw new TlntsRuntimeException("Пользователь c таким e-mail уже существует", HttpStatus.BAD_REQUEST);
		}
		user.setEmail(newEmail);

		log.info("User email has been updated from {} to {}", email, newEmail);
	}

	@Transactional
	public void updateUserPassword(String email, UpdatePasswordRequest request) {
		User user = findUser(email);

		String newPassword = request.getPassword();
		if (passwordEncoder.matches(newPassword, user.getPassword())) {
			throw new TlntsRuntimeException("Новый и старый пароль совпадают", HttpStatus.BAD_REQUEST);
		}
		user.setPassword(passwordEncoder.encode(newPassword));

		authorizationServiceSupport.removeAuthorizationsByPrincipalName(email);

		log.info("Password has been updated for user {}", email);
	}

	@Transactional
	public void enableUser(String email) {
		User user = findUser(email);
		user.setActive(Boolean.TRUE);

		log.info("User {} has been enabled", email);
	}

	@Transactional
	public void disableUser(String email) {
		User user = findUser(email);
		user.setActive(Boolean.FALSE);

		authorizationServiceSupport.removeAuthorizationsByPrincipalName(email);

		log.info("User {} has been enabled", email);
	}

	@Transactional
	public void deleteUserSessions(String email) {
		authorizationServiceSupport.removeAuthorizationsByPrincipalName(email);
	}

	private User findUser(@NonNull String email) {
		return findUserAndMap(email, Function.identity());
	}

	private <T> T findUserAndMap(@NonNull String email, @NonNull Function<User, T> map) {
		return userDao.findUserByEmail(email)
				.map(map)
				.orElseThrow(() -> new TlntsRuntimeException("Пользователь не найден", HttpStatus.NOT_FOUND));
	}

}
