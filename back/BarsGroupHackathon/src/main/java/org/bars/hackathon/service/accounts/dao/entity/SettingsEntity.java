package org.bars.hackathon.service.accounts.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Настройки для пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SETTINGS")
public class SettingsEntity {

    /**
     * Идентификатор настроек
     */
    @Id
    @SequenceGenerator(name = "S_SETTINGS_GEN", sequenceName = "S_SETTINGS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_SETTINGS_GEN")
    @Column(name = "SETTINGS_ID", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    @JoinColumn(name = "USER_ID")
    @OneToOne(targetEntity = UserEntity.class)
    private UserEntity user;
}
