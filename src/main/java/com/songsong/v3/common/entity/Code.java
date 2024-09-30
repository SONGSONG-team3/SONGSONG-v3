package com.songsong.v3.common.entity;

import com.songsong.v3.common.entity.key.CodeKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
@Entity
public class Code {

	@EmbeddedId
	CodeKey codeKey;
	
	@Column(name = "code_name")
	private String codeName;
	
	@Column(name = "code_name_brief")
	private String codeNameBrief;
	
	@Column(name = "order_no")
	private int orderNo;
}
