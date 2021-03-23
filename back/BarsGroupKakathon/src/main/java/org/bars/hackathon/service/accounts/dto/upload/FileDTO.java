package org.bars.hackathon.service.accounts.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

	private String fileName;

	private FileFormatEnum fileExtension;

	private Long fileSize;

	private Long id;
}
