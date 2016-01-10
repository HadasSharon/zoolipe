package com.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="keywords")
public class Keyword {
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	@Column(name = "KEY_WORD")
	private String keyWord;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	
}
