package com.songsong.v3.common.controller;


import com.songsong.v3.common.dto.CommonCodeResultDto;
import com.songsong.v3.common.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommonCodeController {
    private final CommonCodeService commonCodeService;

    @PostMapping("/commoncodes")
    public CommonCodeResultDto getCommonCodeList(@RequestBody List<String> groupCodes) {
        return commonCodeService.getCommonCodeList(groupCodes);
    }
}