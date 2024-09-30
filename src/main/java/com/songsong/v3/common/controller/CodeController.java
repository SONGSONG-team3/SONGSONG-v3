package com.songsong.v3.common.controller;


import com.songsong.v3.common.dto.CodeResultDto;
import com.songsong.v3.common.entity.Code;
import com.songsong.v3.common.entity.key.CodeKey;
import com.songsong.v3.common.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CodeController {
	
	private final CodeService codeService;
		
	@GetMapping("/codes")
	public CodeResultDto listCode(
			@RequestParam("groupCode") String groupCode, 
			@RequestParam("pageNumber") int pageNumber, 
			@RequestParam("pageSize") int pageSize
			) {
		return codeService.listCode(groupCode, pageNumber, pageSize);
	}	
	
	@GetMapping("/codes/{groupCode}/{code}")
	public CodeResultDto detailCode(
			@PathVariable("groupCode") String groupCode,
			@PathVariable("code") String code
			) {
		CodeKey codeKey = new CodeKey(groupCode, code);
        return codeService.detailCode(codeKey);
	}


	@PostMapping("/codes")
	public CodeResultDto insertCode(
			@RequestParam("groupCode") String groupCode, 
			@RequestParam("code") String code, 
			Code codeParam){
		System.out.println(codeParam);
		CodeKey codeKey = new CodeKey(groupCode, code);
		codeParam.setCodeKey(codeKey);
		return codeService.insertCode(codeParam);
	}	

	@PutMapping("/codes")
	public CodeResultDto updateCode(
			@RequestParam("groupCode") String groupCode, 
			@RequestParam("code") String code, 
			Code codeParam){			

		CodeKey codeKey = new CodeKey(groupCode, code);
		codeParam.setCodeKey(codeKey);				
		return codeService.updateCode(codeParam);
	}
	
	@DeleteMapping("/codes/{groupCode}/{code}")
	public CodeResultDto deleteCode(
			@PathVariable("groupCode") String groupCode,
			@PathVariable("code") String code
	){
		CodeKey codeKey = new CodeKey(groupCode, code);
		return codeService.deleteCode(codeKey);
	}
}
