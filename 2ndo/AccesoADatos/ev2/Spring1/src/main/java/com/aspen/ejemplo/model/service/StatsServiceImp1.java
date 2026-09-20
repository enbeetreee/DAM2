package com.aspen.ejemplo.model.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspen.ejemplo.model.dao.IStatsDAO;

import com.aspen.ejemplo.model.entity.Stats;
import com.aspen.ejemplo.model.entity.StatsId;

@Service
public class StatsServiceImp1 implements IStatsService{
	
	@Autowired
	private IStatsDAO statsDAO;	


	@Override
	public Stats findById(StatsId id) {
		return statsDAO.findById(id).orElseThrow(()->new EntityNotFoundException("Stat",id));
	}

	@Override
	public void delete(Stats s) {
		statsDAO.delete(s);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stats update(Stats s, StatsId id) {
		Stats currentStat = findById(id);
		currentStat.setAssistancesPerMatch(s.getAssistancesPerMatch());
		currentStat.setBlocksPerMatch(s.getBlocksPerMatch());
		currentStat.setPointsPerMatch(s.getPointsPerMatch());
		currentStat.setReboundPerMatch(s.getReboundPerMatch());
		return currentStat;
	}

}
