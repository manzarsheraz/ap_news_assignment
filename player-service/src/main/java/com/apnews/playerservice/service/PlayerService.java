package com.apnews.playerservice.service;

import java.util.List;
import java.util.Optional;

import com.apnews.playerservice.model.Player;
import com.apnews.playerservice.model.Sport;

public interface PlayerService {

	public List<Player> getAllPlayers();
	public Optional<Player> getPlayerById(long id);
	public Player savePlayer(Player player);
	
	public List<Player> getPlayersWithNoSport();
	public Optional<Player> updatePlayerSports(Player player);
	public List<Player> getPlayersWithSportCategory();
	//player list with categry sport filter having pagination with page size of 10 
	public List<Sport> getSportsWithNames(List<String> names);
	public List<Player> deleteSport(String sportName);
}
