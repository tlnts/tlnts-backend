--liquibase formatted sql

--changeset mamedov:1
CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  uuid UUID NOT NULL,
  email VARCHAR(320) NOT NULL,
  password VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL,
  timestamp(6) without time zone NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS users ADD CONSTRAINT uc_users_email UNIQUE (email);
ALTER TABLE IF EXISTS users ADD CONSTRAINT uc_users_uuid UNIQUE (uuid);

CREATE TABLE IF NOT EXISTS user_role (
  user_id BIGINT NOT NULL,
  role VARCHAR(15) NOT NULL
);

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE user_role ADD CONSTRAINT user_id_role_index UNIQUE (user_id, role);

CREATE TABLE IF NOT EXISTS oauth2_authorization (
    id varchar(100) NOT NULL,
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorization_grant_type varchar(100) NOT NULL,
    authorized_scopes varchar(1000) DEFAULT NULL,
    attributes text DEFAULT NULL,
    state varchar(500) DEFAULT NULL,
    authorization_code_value text DEFAULT NULL,
    authorization_code_issued_at timestamp DEFAULT NULL,
    authorization_code_expires_at timestamp DEFAULT NULL,
    authorization_code_metadata text DEFAULT NULL,
    access_token_value text DEFAULT NULL,
    access_token_issued_at timestamp DEFAULT NULL,
    access_token_expires_at timestamp DEFAULT NULL,
    access_token_metadata text DEFAULT NULL,
    access_token_type varchar(100) DEFAULT NULL,
    access_token_scopes varchar(1000) DEFAULT NULL,
    oidc_id_token_value text DEFAULT NULL,
    oidc_id_token_issued_at timestamp DEFAULT NULL,
    oidc_id_token_expires_at timestamp DEFAULT NULL,
    oidc_id_token_metadata text DEFAULT NULL,
    refresh_token_value text DEFAULT NULL,
    refresh_token_issued_at timestamp DEFAULT NULL,
    refresh_token_expires_at timestamp DEFAULT NULL,
    refresh_token_metadata text DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS oauth2_authorization_principal_name_index ON oauth2_authorization(principal_name);

--rollback ALTER TABLE IF EXISTS users DROP CONSTRAINT IF EXISTS uc_users_email;
--rollback ALTER TABLE IF EXISTS users DROP CONSTRAINT IF EXISTS uc_users_uuid;
--rollback DROP TABLE IF EXISTS users;

--rollback ALTER TABLE IF EXISTS user_role DROP CONSTRAINT IF EXISTS fk_user_role_on_user;
--rollback ALTER TABLE IF EXISTS user_role DROP CONSTRAINT IF EXISTS user_id_role_index;
--rollback DROP TABLE IF EXISTS user_role;

--rollback DROP INDEX IF EXISTS oauth2_authorization_principal_name_index;
--rollback DROP TABLE IF EXISTS oauth2_authorization;
