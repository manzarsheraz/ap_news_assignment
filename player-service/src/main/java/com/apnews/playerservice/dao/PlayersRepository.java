package com.apnews.playerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apnews.playerservice.enums.Gender;
import com.apnews.playerservice.model.Player;

public interface PlayersRepository extends JpaRepository<Player, Long> {

	//find by given gender, level and age
	Player findByGenderAndLevelAndAge(Gender gender, byte level, byte age);
	
	@Query("select p from Player p left join p.playerSports where p.playerSports IS EMPTY")
    List<Player> findPlayersWithNoSports();
	
	Player findByEmail(String email);
	
//	Page<Player> findAllBySport(long sportId, Pageable pageable, Sort sort);
}
