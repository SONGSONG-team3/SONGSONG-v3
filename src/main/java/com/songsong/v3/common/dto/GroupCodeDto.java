package com.songsong.v3.common.dto;

import com.songsong.v3.common.entity.GroupCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class GroupCodeDto {
	private String groupCode;
	private String groupCodeName;
	private String groupCodeDesc;

	public static GroupCodeDto fromGroupCode(GroupCode groupCode) {
		return GroupCodeDto.builder()
			.groupCode(groupCode.getGroupCode())
			.groupCodeName(groupCode.getGroupCodeName())
			.groupCodeDesc(groupCode.getGroupCodeDesc())
			.build();
	}
}
