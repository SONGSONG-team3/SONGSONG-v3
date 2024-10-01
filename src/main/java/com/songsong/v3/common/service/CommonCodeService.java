package com.songsong.v3.common.service;


import com.songsong.v3.common.dto.CommonCodeResultDto;

import java.util.List;

public interface CommonCodeService {
    CommonCodeResultDto getCommonCodeList(List<String> goupCodes);
}
