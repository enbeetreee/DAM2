package com.aspen.ejemplo.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.aspen.ejemplo.model.entity.Teams;

public interface ITeamsDAO extends CrudRepository<Teams, String>{

}
