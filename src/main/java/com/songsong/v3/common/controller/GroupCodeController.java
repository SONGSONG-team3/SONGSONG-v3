package com.songsong.v3.common.controller;

import com.songsong.v3.common.dto.CodeResultDto;
import com.songsong.v3.common.entity.GroupCode;
import com.songsong.v3.common.service.GroupCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GroupCodeController {
	
	private final GroupCodeService groupCodeService;
		
	@GetMapping("/groupcodes")
	public CodeResultDto listGroupCode(
			@RequestParam("pageNumber") int pageNumber, 
			@RequestParam("pageSize") int pageSize
			) {
		return groupCodeService.listGroupCode( pageNumber, pageSize);
	}	
	
	@GetMapping("/groupcodes/{groupCode}")
	public CodeResultDto detailGroupCode(@PathVariable("groupCode") String groupCode	) {
        return groupCodeService.detailGroupCode(groupCode);
	}
	
	@PostMapping("/groupcodes")
	public CodeResultDto insertGroupCode(GroupCode groupCode){
		System.out.println(groupCode);
		return groupCodeService.insertGroupCode(groupCode);
	}
	
	@PutMapping("/groupcodes")
	public CodeResultDto updateGroupCode(GroupCode groupCode){
		return groupCodeService.updateGroupCode(groupCode);
	}
	
	@DeleteMapping("/groupcodes/{groupCode}")
	public CodeResultDto deleteGroupCode(@PathVariable("groupCode") String groupCode){
		return groupCodeService.deleteGroupCode(groupCode);
	}
}
