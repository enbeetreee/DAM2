package com.aspen.ejemplo.model.service;

import com.aspen.ejemplo.model.entity.StatsId;

public class EntityNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException() {
		super();
	}
	
	public EntityNotFoundException(String message) {
		super(message);
	}
	
	public EntityNotFoundException(String type, int id) {
		super(type+" not found. ID: "+id);
	}
	
	public EntityNotFoundException(String type, String id) {
		super(type+" not found. ID: "+id);
	}
	public EntityNotFoundException(String type, StatsId id) {
		super(type+" not found. ID: "+id);
	}
	

}
