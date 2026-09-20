package com.aspen.ejemplo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspen.ejemplo.model.dao.ITeamsDAO;
import com.aspen.ejemplo.model.entity.Matches;
import com.aspen.ejemplo.model.entity.Players;
import com.aspen.ejemplo.model.entity.Teams;

@Service
public class TeamsServiceImp1 implements ITeamsService {
	@Autowired
	private ITeamsDAO teamsDAO;

	@Override
	public List<Teams> findAll() {
		return (List<Teams>) teamsDAO.findAll();
	}

	@Override
	public List<Matches> getLocalMatches(String id) {
		List<Matches> m = null;
		Teams t = findById(id);
		if (t != null) 
			m = List.copyOf(t.getMatchesesForLocalTeam());
		return m;
	}

	@Override
	public Teams findById(String id) {
		return teamsDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Team", id));
	}

	@Override
	public void save(Teams t) {
		teamsDAO.save(t);

	}

	@Override
	public void delete(Teams t) {
		t.getPlayerses().forEach(p -> p.setTeams(null));
		teamsDAO.delete(t);

	}

	@Override
	public Teams update(Teams t, String id) {
		Teams currentT = findById(id);
		currentT.setCity(t.getCity());
		currentT.setConference(t.getConference());
		currentT.setDivision(t.getDivision());
		currentT.setName(t.getName());
		teamsDAO.save(currentT);
		return currentT;
	}

	@Override
	public List<Matches> getVisitorMatches(String id) {
		List<Matches> m = null;
		Teams t = findById(id);
		if (t != null) 
			m = List.copyOf(t.getMatchesesForVisitorTeam());
			return m;

	}

	@Override
	public Matches saveLocalMatch(String id_l, String id_v, Matches m) {
		Teams local = findById(id_l);

		Teams visitor = findById(id_v);

		local.getMatchesesForLocalTeam().add(m);
		visitor.getMatchesesForVisitorTeam().add(m);
		teamsDAO.save(local);
		teamsDAO.save(visitor);
		return m;
	}

	@Override
	public Matches saveVisitorMatch(String id_v, String id_l, Matches m) {
		Teams local = findById(id_l);
		Teams visitor = findById(id_v);
		local.getMatchesesForLocalTeam().add(m);
		visitor.getMatchesesForVisitorTeam().add(m);
		teamsDAO.save(local);
		teamsDAO.save(visitor);
		return m;
	}

	@Override
	public List<Players> getPlayers(String id) {
		List<Players> p = null;
		Teams t = findById(id);
		if (t != null) 
			p = List.copyOf(t.getPlayerses());
		return p;
		
	}

	@Override
	public void savePlayer(String id, Players p) {
		Teams t = findById(id);
		t.getPlayerses().add(p);
		teamsDAO.save(t);
	}

}
