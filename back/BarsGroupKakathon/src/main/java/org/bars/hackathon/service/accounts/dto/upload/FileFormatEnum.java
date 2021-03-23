package org.bars.hackathon.service.accounts.dto.upload;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum FileFormatEnum {

	JPG(Lists.newArrayList(".jpg")),
	PDF(Lists.newArrayList(".pdf")),
	DOC(Lists.newArrayList(".doc", ".docx")),
	PNG(Lists.newArrayList(".png")),
	XLS(Lists.newArrayList(".xls", ".xlsx")),
	UNDEFINED(Collections.emptyList());

	private final List<String> extensions;
}
