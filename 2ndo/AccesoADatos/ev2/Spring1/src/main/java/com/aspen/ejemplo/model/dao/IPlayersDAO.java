package com.aspen.ejemplo.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.aspen.ejemplo.model.entity.Players;

public interface IPlayersDAO extends CrudRepository<Players, Integer> {

}
