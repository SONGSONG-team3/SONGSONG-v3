package com.songsong.v3.common.service;


import com.songsong.v3.common.dto.CodeResultDto;
import com.songsong.v3.common.entity.Code;
import com.songsong.v3.common.entity.key.CodeKey;

public interface CodeService {
	CodeResultDto insertCode(Code code);
	CodeResultDto updateCode(Code code);
	CodeResultDto deleteCode(CodeKey codeKey);
	
	CodeResultDto listCode(String goupCode, int pageNumber, int pageSize);
	CodeResultDto countCode();
	CodeResultDto detailCode(CodeKey codeKey);	
}
