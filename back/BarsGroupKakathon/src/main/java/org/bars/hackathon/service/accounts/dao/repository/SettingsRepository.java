package org.bars.hackathon.service.accounts.dao.repository;

import org.bars.hackathon.service.accounts.dao.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для {@link SettingsEntity}
 */
public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
}
