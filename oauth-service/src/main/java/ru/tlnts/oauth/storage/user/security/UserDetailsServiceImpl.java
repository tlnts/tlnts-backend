package ru.tlnts.oauth.storage.user.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.tlnts.oauth.storage.user.dao.UserDao;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
		return userDao.findUserByEmail(username)
				.map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
	}

}
