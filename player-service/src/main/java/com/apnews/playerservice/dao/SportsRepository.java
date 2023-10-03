package com.apnews.playerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apnews.playerservice.model.Player;
import com.apnews.playerservice.model.Sport;

public interface SportsRepository extends JpaRepository<Sport, Long> {

	@Query("SELECT s FROM Sport s JOIN FETCH s.associatedPlayers WHERE s.name IN :sportList")
    List<Sport> findSportsWithPlayers(List<String> sportList);
	
	Sport findByName(String name);
	
	//find sport with atleast two players
	@Query("SELECT s FROM Sport s WHERE SIZE(s.associatedPlayers) >= 2")
	List<Sport> findSportWithTwoOrMorePlayers();
	
	//ORM query to return sport no players associated with
	@Query("select s from Sport s left join s.associatedPlayers where s.associatedPlayers IS EMPTY")
    List<Player> findSportsWithNoPlayer();
	
//	Page<Player> findAllBySport(long sportId, Pageable pageable, Sort sort);
}
