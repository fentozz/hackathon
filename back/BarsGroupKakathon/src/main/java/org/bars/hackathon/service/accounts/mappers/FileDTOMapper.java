package org.bars.hackathon.service.accounts.mappers;

import org.bars.hackathon.service.accounts.dao.entity.FileEntity;
import org.bars.hackathon.service.accounts.dto.upload.FileDTO;
import org.springframework.stereotype.Component;

@Component
public class FileDTOMapper {

	public FileDTO map(FileEntity file) {
		return FileDTO.builder()
				.fileExtension(file.getFileFormat())
				.fileName(file.getName())
				.fileSize(file.getFileSize())
				.id(file.getId())
				.build();
	}
}
