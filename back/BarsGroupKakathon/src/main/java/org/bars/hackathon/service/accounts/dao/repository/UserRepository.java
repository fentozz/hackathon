package org.bars.hackathon.service.accounts.dao.repository;

import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления {@link UserEntity}
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByLogin(String login);

    List<UserEntity> findAllByIdIn(Iterable<Long> ids, Sort sort);

    UserEntity getOneByLogin(String login);
}