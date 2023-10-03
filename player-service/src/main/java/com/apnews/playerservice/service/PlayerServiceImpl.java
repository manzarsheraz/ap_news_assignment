package com.apnews.playerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apnews.playerservice.dao.PlayersRepository;
import com.apnews.playerservice.dao.SportsRepository;
import com.apnews.playerservice.model.Player;
import com.apnews.playerservice.model.Sport;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayersRepository playersRepository;
	
	@Autowired 
	private SportsRepository sportsRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Player> getAllPlayers() {
		return playersRepository.findAll();
	}

	@Override
	public Optional<Player> getPlayerById(long id) {
		return playersRepository.findById(id); 
	}

	@Override
	public Player savePlayer(Player player) {
		playersRepository.save(player);
		return player;
	}

	@Override
	public List<Player> getPlayersWithNoSport() {
		return playersRepository.findPlayersWithNoSports();
	}

	@Override
	public Optional<Player> updatePlayerSports(Player player) {
		Optional<Player> optional = Optional.empty();
		Player existingPlayer = playersRepository.findByEmail(player.getEmail());
		
		if(existingPlayer != null) {
			existingPlayer.getPlayerSports().addAll(player.getPlayerSports());
			playersRepository.save(existingPlayer);
			optional = Optional.of(existingPlayer);
		}
		
		return optional;
	}

	@Override
	public List<Player> getPlayersWithSportCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sport> getSportsWithNames(List<String> names) {
		return sportsRepository.findSportsWithPlayers(names);
	}

	@Override
	@Transactional
	public List<Player> deleteSport(String sportName) {
		
		List<Player> playerList = new ArrayList<Player>();
		
	    // Load the child entity you want to delete
	    Sport sportToDelete = sportsRepository.findByName(sportName);

	    if (sportToDelete != null) {
	        // Remove the child from its parent(s)
	    	sportToDelete.getAssociatedPlayers().stream().forEach(player -> player.getPlayerSports().remove(sportToDelete));
	        
	    	playerList.addAll(sportToDelete.getAssociatedPlayers());
	    	
	        // Delete the child entity from the database
	        entityManager.remove(sportToDelete);
	    }
		
	    return playerList;
	}

}
