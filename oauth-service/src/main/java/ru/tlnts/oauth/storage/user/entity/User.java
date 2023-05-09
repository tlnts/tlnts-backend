package ru.tlnts.oauth.storage.user.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.tlnts.oauth.api.constraint.CommonConstants;
import ru.tlnts.oauth.api.model.UserRole;
import ru.tlnts.oauth.storage.AbstractModel;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author mamedov
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractModel {

    public static final int PASSWORD_LENGTH = 100;
    public static final int ROLE_LENGTH = 15;

    @Column(nullable = false, length = CommonConstants.EMAIL_LENGTH, unique = true)
    private String email;

    @Column(nullable = false, length = PASSWORD_LENGTH)
    private String password;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "datetime_create", nullable = false)
    private LocalDateTime dateTimeCreate;

    @Column(name = "role", nullable = false, length = ROLE_LENGTH)
    @Enumerated(EnumType.STRING)
    @BatchSize(size = BATCH_SIZE)
    @ElementCollection
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            indexes = @Index(name = "user_id_role_index", columnList = "user_id,role", unique = true))
    private Set<UserRole> roles = EnumSet.noneOf(UserRole.class);

}
