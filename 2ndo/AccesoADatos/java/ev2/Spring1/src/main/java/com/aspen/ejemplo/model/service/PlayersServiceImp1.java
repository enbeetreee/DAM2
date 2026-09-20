package com.aspen.ejemplo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspen.ejemplo.model.dao.IPlayersDAO;
import com.aspen.ejemplo.model.dao.IStatsDAO;
import com.aspen.ejemplo.model.entity.Players;
import com.aspen.ejemplo.model.entity.Stats;

@Service
public class PlayersServiceImp1 implements IPlayersService{
	@Autowired
	private IPlayersDAO playersDAO;
	@Autowired
	private IStatsDAO statsDAO;	
	
	@Override
	public List<Players> findAll() {
		return (List<Players>)playersDAO.findAll();
	}

	@Override
	public Players findById(int id) {
		return playersDAO.findById(id).orElseThrow(()->new EntityNotFoundException("Player",id));
		
	}
	@Override
	public List<Stats> getStats(int id) {
		List<Stats> s = null;
		Players p = findById(id);
		if (p!=null) {
			s = List.copyOf(p.getStatses());
		}
		return s;
	}

	@Override
	public void save(Players p) {
		playersDAO.save(p);

	}
	@Override
	public void saveStats(int id, Stats s) {
		Players p = findById(id);
		p.getStatses().add(s);
		s.setPlayers(p);
		statsDAO.save(s);
		playersDAO.save(p);

	}

	@Override
	public void delete(Players p) {
		playersDAO.delete(p);
	}

	@Override
	public Players update(Players p, int id) {
		Players currentPlayer = findById(id);
		currentPlayer.setHeight(p.getHeight());
		currentPlayer.setName(p.getName());
		currentPlayer.setOrigin(p.getOrigin());
		currentPlayer.setPosition(p.getPosition());
		currentPlayer.setSalary(p.getSalary());
		currentPlayer.setWeight(p.getWeight());
		return currentPlayer;
	}

}
