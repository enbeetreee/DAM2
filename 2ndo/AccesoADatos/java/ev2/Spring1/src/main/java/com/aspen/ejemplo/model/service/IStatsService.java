package com.aspen.ejemplo.model.service;


import com.aspen.ejemplo.model.entity.Stats;
import com.aspen.ejemplo.model.entity.StatsId;


public interface IStatsService {
	public Stats findById(StatsId id);
	public void delete(Stats p);
	public Stats update(Stats p, StatsId id);
}
