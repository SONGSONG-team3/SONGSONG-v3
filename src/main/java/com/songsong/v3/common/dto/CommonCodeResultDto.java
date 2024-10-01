package com.songsong.v3.common.dto;

// 공통 코드를 사용하능 App

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CommonCodeResultDto {
    private String result;
    private Map<String, List<CodeDto>> commonCodeDtoListMap;
}