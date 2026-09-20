package com.aspen.ejemplo.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.aspen.ejemplo.model.entity.Stats;
import com.aspen.ejemplo.model.entity.StatsId;

public interface IStatsDAO extends CrudRepository<Stats, StatsId>{

}
