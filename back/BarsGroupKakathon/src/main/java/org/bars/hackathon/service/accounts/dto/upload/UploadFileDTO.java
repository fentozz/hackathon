package org.bars.hackathon.service.accounts.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDTO {

	@NotNull
	private String fileName;

	@NotNull
	private MultipartFile multipartFile;
}
