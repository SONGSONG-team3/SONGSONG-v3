package com.songsong.v3.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CodeResultDto {
	private String result;
	private CodeDto codeDto;
	private GroupCodeDto groupCodeDto;
	private List<CodeDto> codeDtoList;
	private List<GroupCodeDto> groupCodeDtoList;
	private long count;
}
