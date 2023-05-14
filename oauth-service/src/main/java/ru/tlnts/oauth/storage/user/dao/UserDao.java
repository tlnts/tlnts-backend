package ru.tlnts.oauth.storage.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tlnts.oauth.storage.user.entity.User;

import java.util.Optional;

/**
 * @author mamedov
 */
public interface UserDao extends JpaRepository<User, Long> {

	Optional<User> findUserByEmail(String email);

	boolean existsByEmail(String email);

}
