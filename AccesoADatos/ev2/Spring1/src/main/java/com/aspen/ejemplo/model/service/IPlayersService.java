package com.aspen.ejemplo.model.service;

import java.util.List;

import com.aspen.ejemplo.model.entity.Players;
import com.aspen.ejemplo.model.entity.Stats;

public interface IPlayersService {
	
	public List<Players> findAll();
	public Players findById(int id);
	public void save(Players p);
	public void delete(Players p);
	public Players update(Players p, int id);
	void saveStats(int id, Stats s);
	List<Stats> getStats(int id);
}
