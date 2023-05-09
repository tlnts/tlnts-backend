package ru.tlnts.oauth.storage.user.security;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

@Value
@JsonAutoDetect
public class GrandAuthorityImpl implements GrantedAuthority {

	String authority;

	@JsonCreator
	public GrandAuthorityImpl(@JsonProperty("authority") String authority) {
		this.authority = authority;
	}

}
