package org.bars.hackathon.service.accounts.dao.repository;

import org.bars.hackathon.service.accounts.dao.entity.FileEntity;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

	List<FileEntity> findAllByUser(UserEntity user);
}
