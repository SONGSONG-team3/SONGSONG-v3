package com.songsong.v3.common.service;

import com.songsong.v3.common.dto.CodeResultDto;
import com.songsong.v3.common.entity.GroupCode;

;

public interface GroupCodeService {
	CodeResultDto insertGroupCode(GroupCode groupCode);
	CodeResultDto updateGroupCode(GroupCode groupCode);
	CodeResultDto deleteGroupCode(String groupCode);	
	
	CodeResultDto listGroupCode(int pageNumber, int pageSize);
	CodeResultDto countGroupCode();
	CodeResultDto detailGroupCode(String groupCode);
}
