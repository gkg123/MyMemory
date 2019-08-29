package com.taotao.utils;

import java.io.Serializable;
import java.util.List;

public class EasyUIResult<TdTtem> implements Serializable{
	private Long total;
	private List<TdTtem> rows;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<TdTtem> getRows() {
		return rows;
	}
	public void setRows(List<TdTtem> rows) {
		this.rows = rows;
	}

	

}
