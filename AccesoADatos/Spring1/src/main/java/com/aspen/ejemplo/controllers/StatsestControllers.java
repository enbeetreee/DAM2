package com.aspen.ejemplo.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aspen.ejemplo.model.entity.Stats;
import com.aspen.ejemplo.model.entity.StatsId;
import com.aspen.ejemplo.model.service.EntityNotFoundException;
import com.aspen.ejemplo.model.service.IStatsService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/nba/players/")
public class StatsestControllers {
	
	@Autowired
	private IStatsService statsService;

	
	@GetMapping("/{id_p}/stats/{id_s}")
	public Stats getStatsById(@PathVariable int id_p, @PathVariable String id_s) {
		return statsService.findById(new StatsId(id_s.replace('-', '/'), id_p));
	}

	
	@DeleteMapping("/{id_p}/stats/{id_s}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id_p, @PathVariable String id_s) {
		Stats s = statsService.findById(new StatsId(id_s.replace('-', '/'), id_p));
		statsService.delete(s);
	}
	
	@PutMapping("/{id_p}/stats/{id_s}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id_p, @PathVariable String id_s, @RequestBody Stats s) {
		statsService.update(s, new StatsId(id_s.replace('-', '/'), id_p));
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
