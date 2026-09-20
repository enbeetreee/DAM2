package com.aspen.ejemplo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aspen.ejemplo.model.entity.Matches;
import com.aspen.ejemplo.model.entity.Players;
import com.aspen.ejemplo.model.entity.Teams;
import com.aspen.ejemplo.model.service.EntityNotFoundException;
import com.aspen.ejemplo.model.service.ITeamsService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/nba/teams")
public class TeamsRestControllers {
	
	@Autowired
	private ITeamsService teamsService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getTeams(){
		List<Teams> res = teamsService.findAll();
		if (!res.isEmpty()) {
			return new ResponseEntity<List<Teams>>(res, HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Teams getTeamById(@PathVariable String id) {
		return teamsService.findById(id);
	}
	
	@GetMapping("/{id}/players")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getPlayersInTeam(@PathVariable String id){
		List<Players> res = teamsService.getPlayers(id);
		if (!res.isEmpty()) {
			return new ResponseEntity<List<Players>>(res, HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{id}/local")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getLocalMatchesById(@PathVariable String id) {
		List<Matches> res = teamsService.getLocalMatches(id);
		if (!res.isEmpty()) {
			return new ResponseEntity<List<Matches>>(res, HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	@GetMapping("/{id}/visitor")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?>getVisitorMatchesById(@PathVariable String id) {
		List<Matches> res = teamsService.getVisitorMatches(id);
		if (!res.isEmpty()) {
			return new ResponseEntity<List<Matches>>(res, HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Teams create(@RequestBody Teams t) {
		teamsService.save(t);
		return t;
	}
	
	@PostMapping("/{id}/players")
	@ResponseStatus(HttpStatus.CREATED)
	public Players createPlayer(@RequestBody Players p, @PathVariable String id) {
		teamsService.savePlayer(id ,p);
		return p;
	}
	
	@PostMapping("/{id_l}/local/{id_v}")
	@ResponseStatus(HttpStatus.CREATED)
	public Matches createLocalMatch(@PathVariable String id_l, @PathVariable String id_v, @RequestBody Matches m) {
		return teamsService.saveLocalMatch(id_l, id_v, m);
	}
	@PostMapping("/{id_v}/visitor/{id_l}")
	@ResponseStatus(HttpStatus.CREATED)
	public Matches createVisitorMatch(@PathVariable String id_v, @PathVariable String id_l, @RequestBody Matches m) {
		return teamsService.saveVisitorMatch(id_l, id_v, m);
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		Teams p = teamsService.findById(id);
		teamsService.delete(p);
	}
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Teams update(@PathVariable String id, @RequestBody Teams t) {
		return teamsService.update(t, id);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> handler(EntityNotFoundException ex){
		Map<String, Object> response = new HashMap<>();
		response.put("message", ex.getMessage());
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(HttpMeddageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> handler(HttpMeddageNotReadableException ex){
		Map<String, Object> response = new HashMap<>();
		response.put("message", ex.getMessage());
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
	}
}
