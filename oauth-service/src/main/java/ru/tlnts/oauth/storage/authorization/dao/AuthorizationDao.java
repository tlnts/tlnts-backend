package ru.tlnts.oauth.storage.authorization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tlnts.oauth.storage.authorization.entity.Authorization;

import java.util.List;

/**
 * @author mamedov
 */
public interface AuthorizationDao extends JpaRepository<Authorization, Long> {

	List<Authorization> findByPrincipalName(String principalName);

}
