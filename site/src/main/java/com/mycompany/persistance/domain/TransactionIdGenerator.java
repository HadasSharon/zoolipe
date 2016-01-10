package com.mycompany.persistance.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TRANS_ID_GENERATOR", uniqueConstraints = { @UniqueConstraint(columnNames = "TRANS_ID") })
public class TransactionIdGenerator {
	@Id
	@GeneratedValue
	@Column(name = "TRANS_ID")
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
