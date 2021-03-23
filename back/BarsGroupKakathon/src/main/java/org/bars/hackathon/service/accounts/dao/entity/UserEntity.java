package org.bars.hackathon.service.accounts.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

/**
 * Учетная запись пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USERS")
public class UserEntity {

    /**
     * Идентификатор учетной записи пользователя
     */
    @Id
    @SequenceGenerator(name = "S_USER_GEN", sequenceName = "S_USER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_USER_GEN")
    @Column(name = "USER_ID", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    /**
     * Идентификатор учетной записи пользователя
     */
    @Column(name = "LOGIN", length = 200, nullable = false)
    private String login;

    /**
     * Пароль учетной записи пользователя
     */
    @Column(name = "PASSWORD", length = 200)
    private String password;

    /**
     * Имя пользователя
     */
    @Column(name = "NAME", length = 200)
    private String name;

    /**
     * Фамилия пользователя
     */
    @Column(name = "SURNAME", length = 200)
    private String surname;

    /**
     * Дата создания учетной записи пользователя
     */
    @CreatedDate
    @Column(name = "CREATE_DT", nullable = false, updatable = false)
    private Instant created;

    /**
     * Дата обновления учетной записи пользователя
     */
    @LastModifiedDate
    @Column(name = "CHANGE_DT", nullable = false)
    private Instant changed;

    /**
     * Роль учетной записи пользователя
     */
    @Column(name = "USER_ROLE")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    /**
     * Дата обновления учетной записи пользователя
     */
    @LastModifiedDate
    @Column(name = "LAST_ACCESS_DT", nullable = false)
    private Instant lastAccessed;

    @Column(name = "FILE_SIZE_STORAGE", columnDefinition = "int default 104857600")
    private Long fileSizeStorage;

    @OneToOne(targetEntity = SettingsEntity.class, mappedBy = "user", cascade = CascadeType.ALL)
    private SettingsEntity settings;

    @OneToMany(targetEntity = FileEntity.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FileEntity> files;
}