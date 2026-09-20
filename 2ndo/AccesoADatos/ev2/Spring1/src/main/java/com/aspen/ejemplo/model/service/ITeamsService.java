package com.aspen.ejemplo.model.service;

import java.util.List;

import com.aspen.ejemplo.model.entity.Matches;
import com.aspen.ejemplo.model.entity.Players;
import com.aspen.ejemplo.model.entity.Teams;

public interface ITeamsService {

	public List<Teams> findAll();
	public Teams findById(String id);
	public List<Players> getPlayers(String id);
	public List<Matches> getLocalMatches(String id);
	public List<Matches> getVisitorMatches(String id);
	public void save(Teams t);
	public void delete(Teams t);
	public Teams update(Teams t, String id);
	public Matches saveLocalMatch(String id_l, String id_v, Matches m);
	public Matches saveVisitorMatch(String id_v, String id_l, Matches m);
	public void savePlayer(String id, Players p);
}
