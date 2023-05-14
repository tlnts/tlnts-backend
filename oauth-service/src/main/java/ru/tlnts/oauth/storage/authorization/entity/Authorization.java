package ru.tlnts.oauth.storage.authorization.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import ru.tlnts.oauth.api.constraint.CommonConstants;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Create respect to {@link JdbcOAuth2AuthorizationService} for authorizations stores
 * @author mamedov
 */
@Getter
@Setter
@Entity
@Table(name = "oauth2_authorization",
		indexes = @Index(name = "oauth2_authorization_principal_name_index", columnList = "principal_name"))
public class Authorization {

	private static final int COMMON_LENGTH = 100;
	private static final int MEDIUM_LENGTH = 500;
	private static final int BIG_LENGTH = 1000;

	private static final int PRINCIPAL_NAME_LENGTH = CommonConstants.EMAIL_LENGTH;

	@Id
	@Column(length = COMMON_LENGTH, nullable = false, updatable = false, unique = true)
	private String id;

	@Column(name = "registered_client_id", length = COMMON_LENGTH, nullable = false)
	private String registeredClientId;

	@Column(name = "principal_name", length = PRINCIPAL_NAME_LENGTH, nullable = false)
	private String principalName;

	@Column(name = "authorization_grant_type", length = COMMON_LENGTH, nullable = false)
	private String authorizationGrantType;

	@Nullable
	@Column(name = "authorized_scopes", length = BIG_LENGTH)
	private String authorizedScopes;

	@Nullable
	@Column(name = "attributes", columnDefinition = "text")
	private String attributes;

	@Nullable
	@Column(name = "state", length = MEDIUM_LENGTH)
	private String state;

	@Nullable
	@Column(name = "authorization_code_value", columnDefinition = "text")
	private String authorizationCodeValue;

	@Nullable
	@Column(name = "authorization_code_issued_at")
	private LocalDateTime authorizationCodeIssuedAt;

	@Nullable
	@Column(name = "authorization_code_expires_at")
	private LocalDateTime authorizationCodeExpiresAt;

	@Nullable
	@Column(name = "authorization_code_metadata", columnDefinition = "text")
	private String authorizationCodeMetadata;

	@Nullable
	@Column(name = "access_token_value", columnDefinition = "text")
	private String accessTokenValue;

	@Nullable
	@Column(name = "access_token_issued_at")
	private LocalDateTime accessTokenIssuedAt;

	@Nullable
	@Column(name = "access_token_expires_at")
	private LocalDateTime accessTokenExpiresAt;

	@Nullable
	@Column(name = "access_token_metadata", columnDefinition = "text")
	private String accessTokenMetadata;

	@Nullable
	@Column(name = "access_token_type", length = COMMON_LENGTH)
	private String accessTokenType;

	@Nullable
	@Column(name = "access_token_scopes", length = BIG_LENGTH)
	private String accessTokenScopes;

	@Nullable
	@Column(name = "refresh_token_value", columnDefinition = "text")
	private String refreshTokenValue;

	@Nullable
	@Column(name = "refresh_token_issued_at")
	private LocalDateTime refreshTokenIssuedAt;

	@Nullable
	@Column(name = "refresh_token_expires_at")
	private LocalDateTime refreshTokenExpiresAt;

	@Nullable
	@Column(name = "refresh_token_metadata", columnDefinition = "text")
	private String refreshTokenMetadata;

	@Nullable
	@Column(name = "oidc_id_token_value", columnDefinition = "text")
	private String oidcIdTokenValue;

	@Nullable
	@Column(name = "oidc_id_token_issued_at")
	private LocalDateTime oidcIdTokenIssuedAt;

	@Nullable
	@Column(name = "oidc_id_token_expires_at")
	private LocalDateTime oidcIdTokenExpiresAt;

	@Nullable
	@Column(name = "oidc_id_token_metadata", columnDefinition = "text")
	private String oidcIdTokenMetadata;

}
