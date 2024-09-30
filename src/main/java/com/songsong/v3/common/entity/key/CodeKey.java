package com.songsong.v3.common.entity.key;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class CodeKey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String groupCode;
    private String code;
    
	public CodeKey(String groupCode, String code) {
		this.groupCode = groupCode;
		this.code = code;
	}
}
