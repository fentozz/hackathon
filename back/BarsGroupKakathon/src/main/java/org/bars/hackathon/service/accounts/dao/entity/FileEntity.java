package org.bars.hackathon.service.accounts.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bars.hackathon.service.accounts.dto.upload.FileFormatEnum;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "FILES")
public class FileEntity {

	@Id
	@SequenceGenerator(name = "S_FILE_GEN", sequenceName = "S_FILE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_FILE_GEN")
	@Column(name = "FILE_ID", unique = true, nullable = false, insertable = false, updatable = false)
	private Long id;

	@Column(name = "filePath", length = 500, nullable = false)
	private String filePath;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "FILE_FORMAT")
	private FileFormatEnum fileFormat;

	@Column(name = "name", length = 500, nullable = false)
	private String name;

	@Column(name = "fileSize", nullable = false)
	private Long fileSize;

	@JoinColumn(name = "USER_ID")
	@ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY, optional = false)
	private UserEntity user;
}
