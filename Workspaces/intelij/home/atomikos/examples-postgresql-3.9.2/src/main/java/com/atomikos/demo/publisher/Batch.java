package com.atomikos.demo.publisher;

import java.io.Serializable;

import com.atomikos.demo.domain.Order;

public class Batch implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int count;
	private Order order;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
