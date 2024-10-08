package com.songsong.v3.common.service;


import com.songsong.v3.common.dto.CodeResultDto;
import com.songsong.v3.common.dto.GroupCodeDto;
import com.songsong.v3.common.entity.GroupCode;
import com.songsong.v3.common.repository.GroupCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupCodeServiceImpl implements GroupCodeService{

	private final GroupCodeRepository groupCodeRepository;
	@Override
	public CodeResultDto insertGroupCode(GroupCode groupCode) {
		CodeResultDto codeResultDto = new CodeResultDto();
		try {
			groupCodeRepository.save(groupCode);
			codeResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			codeResultDto.setResult("fail");
		}
		return codeResultDto;
	}

	@Override
	public CodeResultDto updateGroupCode(GroupCode groupCode) {
		CodeResultDto codeResultDto = new CodeResultDto();
		try {
			// select 후 update
			groupCodeRepository.save(groupCode);
			codeResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			codeResultDto.setResult("fail");
		}
		return codeResultDto;
	}

	@Override
	public CodeResultDto deleteGroupCode(String groupCode) {
		CodeResultDto codeResultDto = new CodeResultDto();
		try {
			groupCodeRepository.deleteById(groupCode);
			codeResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			codeResultDto.setResult("fail");
		}
		return codeResultDto;		
	}

	@Override
	public CodeResultDto listGroupCode(int pageNumber, int pageSize) {
		CodeResultDto codeResultDto = new CodeResultDto();
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			Page<GroupCode> page = groupCodeRepository.findAll(pageable);
			List<GroupCodeDto> groupCodeDtoList = new ArrayList<>();
			page.toList().forEach( groupCode -> groupCodeDtoList.add(GroupCodeDto.fromGroupCode(groupCode)) );
			codeResultDto.setGroupCodeDtoList(groupCodeDtoList);
			
			Long count = groupCodeRepository.count(); // int -> Long
			codeResultDto.setCount(count);
			
			codeResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			codeResultDto.setResult("fail");
		}
		return codeResultDto;
	}

	@Override
	public CodeResultDto countGroupCode() {
		CodeResultDto codeResultDto = new CodeResultDto();
		long count = groupCodeRepository.count();
		codeResultDto.setCount(count);
		return codeResultDto;
	}

	@Override
	public CodeResultDto detailGroupCode(String groupCode) {
		CodeResultDto codeResultDto = new CodeResultDto();
		Optional<GroupCode> optionalGroupCode = groupCodeRepository.findById(groupCode);
		optionalGroupCode.ifPresentOrElse(
				detailGroupCode -> {
					codeResultDto.setGroupCodeDto( GroupCodeDto.fromGroupCode(detailGroupCode) );
					codeResultDto.setResult("success");
				},
				() -> {
					codeResultDto.setResult("fail");
				});
		return codeResultDto;
	}
}
