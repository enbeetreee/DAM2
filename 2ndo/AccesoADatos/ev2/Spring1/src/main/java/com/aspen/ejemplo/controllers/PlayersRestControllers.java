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

import com.aspen.ejemplo.model.entity.Players;
import com.aspen.ejemplo.model.entity.Stats;
import com.aspen.ejemplo.model.service.EntityNotFoundException;
import com.aspen.ejemplo.model.service.IPlayersService;
import com.aspen.ejemplo.model.service.IStatsService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/nba/players")
public class PlayersRestControllers {
	
	@Autowired
	private IPlayersService playersService;
	@Autowired
	private IStatsService statsService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAllPlayers(){
		List<Players> res = playersService.findAll();
		if (!res.isEmpty()) {
			return new ResponseEntity<List<Players>>(res, HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
	}


	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Players getPlayersById(@PathVariable int id) {
		return playersService.findById(id);
	}
	

	@GetMapping("/{id}/stats")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getStatsById(@PathVariable int id){
		List<Stats> res = playersService.getStats(id);
		if (!res.isEmpty()) {
			return new ResponseEntity<List<Stats>>(res, HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Players create(@RequestBody Players p) {
		playersService.save(p);
		return p;
	}
	
	@PostMapping("/{id}/stats")
	@ResponseStatus(HttpStatus.CREATED)
	public Stats createStats(@PathVariable int id, @RequestBody Stats s) {
		playersService.saveStats(id, s);
		return s;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		Players p = playersService.findById(id);
		//TODO delete stats
		p.getStatses().forEach(s -> statsService.delete(s));
		playersService.delete(p);
	}
	

	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Players update(@PathVariable int id, @RequestBody Players p) {
		return playersService.update(p, id);
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
