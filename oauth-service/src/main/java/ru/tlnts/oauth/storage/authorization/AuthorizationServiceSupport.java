package ru.tlnts.oauth.storage.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.stereotype.Service;
import ru.tlnts.oauth.storage.authorization.dao.AuthorizationDao;
import ru.tlnts.oauth.storage.authorization.entity.Authorization;

import java.util.List;

/**
 * @author mamedov
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationServiceSupport {

	private final AuthorizationDao authorizationDao;

	public void removeAuthorizationsByPrincipalName(String principalName) {
		List<Authorization> authorizations = authorizationDao.findByPrincipalName(principalName);
		if (!authorizations.isEmpty()) {
			authorizationDao.deleteAll(authorizations);
			log.debug("For principal [name={}] all authorizations [count={}] have been removed", principalName, authorizations.size());
		}
	}

}
