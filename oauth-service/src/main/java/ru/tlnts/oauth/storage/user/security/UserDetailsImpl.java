package ru.tlnts.oauth.storage.user.security;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.tlnts.oauth.api.model.UserRole;
import ru.tlnts.oauth.storage.user.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonAutoDetect
public class UserDetailsImpl implements UserDetails {

	private final String password;
	private final String username;
	private final boolean isEnabled;
	private final Collection<GrantedAuthority> authorities;


	public UserDetailsImpl(User user) {
		this.password = user.getPassword();
		this.username = user.getEmail();
		this.isEnabled = user.getActive();
		this.authorities = user.getRoles().stream()
				.map(UserRole::name)
				.map(GrandAuthorityImpl::new)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@JsonCreator
	public UserDetailsImpl(@JsonProperty("password") String password,
						   @JsonProperty("username") String username,
						   @JsonProperty("isEnabled") boolean isEnabled,
						   @JsonProperty("authorities") Collection<GrantedAuthority> authorities) {
		this.password = password;
		this.username = username;
		this.isEnabled = isEnabled;
		this.authorities = authorities;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonProperty("isEnabled")
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
