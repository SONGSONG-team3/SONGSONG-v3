package com.songsong.v3.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "group_code")
public class GroupCode {

	@Id
	@Column(name = "group_code")
	private String groupCode;
	
	@Column(name = "group_code_name")
	private String groupCodeName;
	
	@Column(name = "group_code_desc")
	private String groupCodeDesc;
}
