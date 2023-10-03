package com.apnews.playerservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apnews.playerservice.PlayerServiceApplication;
import com.apnews.playerservice.model.Player;
import com.apnews.playerservice.model.Sport;
import com.apnews.playerservice.service.PlayerService;

import jakarta.validation.Validator;
import jakarta.validation.groups.Default;

@RestController
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	private Validator validator;
	
	@Autowired
	private PlayerService playerService;

	@GetMapping
	public ResponseEntity<List<Player>> getAllPlayers() {

		Optional<List<Player>> playersList = Optional.ofNullable(playerService.getAllPlayers());
		ResponseEntity<List<Player>> responseEntity = ResponseEntity.of(playersList);

		if (CollectionUtils.isEmpty(playersList.get()))
			return new ResponseEntity("No Content Found", HttpStatus.NO_CONTENT);

		return responseEntity;
	}
	
	//view palyer by id
	@GetMapping("/{id}")
	public ResponseEntity<Player> getPlayerById(@PathVariable long id) throws Exception {
		Optional<Player> player = playerService.getPlayerById(id);
		ResponseEntity<Player> responseEntity = ResponseEntity.of(player);

		if (player.isEmpty())
			throw new Exception(String.format("PurchaseOrder Not Found against id: %d", id));//PurchaseNotFoundException(String.format("PurchaseOrder Not Found against id: %d", id));

		return responseEntity;
	}
	
	
	//add a player with sports
	@PostMapping
	public ResponseEntity<Player> addPlayer(@RequestBody Player player) throws Exception {
		Player newPlayer = playerService.savePlayer(player);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newPlayer.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	//player with no sport
	@GetMapping("/playersWithNoSport")
	public ResponseEntity<List<Player>> getAllPlayersWithNoSport() {

		Optional<List<Player>> playersList = Optional.ofNullable(playerService.getPlayersWithNoSport());
		ResponseEntity<List<Player>> responseEntity = ResponseEntity.of(playersList);

		if (CollectionUtils.isEmpty(playersList.get()))
			return new ResponseEntity("No Content Found", HttpStatus.NO_CONTENT);

		return responseEntity;
	}
	
	//update a player sport
	@PatchMapping
	public ResponseEntity<Player> updatePlayerSports(@RequestBody Player player) throws Exception {
		Optional<Player> optional = playerService.updatePlayerSports(player);

		if (optional.isEmpty())
			return new ResponseEntity("No Content Found", HttpStatus.NO_CONTENT);

		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(optional.get().getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/sports")
	public ResponseEntity<List<Sport>> getSportsWithPlayers(@RequestParam List<String> names) {

		List<Sport> sportList = playerService.getSportsWithNames(names);
		
		if(CollectionUtils.isEmpty(sportList))
			return new ResponseEntity("No Content Found", HttpStatus.NO_CONTENT);
		
		Optional<List<Sport>> sportsList = Optional.ofNullable(sportList);
		return ResponseEntity.of(sportsList);
	}
	
	@DeleteMapping("/sports")
	public ResponseEntity<List<Player>> deleteSportsData(@RequestParam String name) {

		Optional<List<Player>> playersList = Optional.ofNullable(playerService.deleteSport(name));
		ResponseEntity<List<Player>> responseEntity = ResponseEntity.of(playersList);

		if (CollectionUtils.isEmpty(playersList.get()))
			return new ResponseEntity("No Content Found", HttpStatus.NO_CONTENT);

		return responseEntity;
	}
	
	//player list with categry sport filter having pagination with page size of 10 
//	@GetMapping("/")
//	public ResponseEntity<List<Player>> retreiveAllProducts() throws Exception {
//		Optional<List<Players>> productList = Optional.ofNullable(productRepository.findAll());
//		ResponseEntity<List<Players>> responseEntity = ResponseEntity.of(productList);
//
//		if (CollectionUtils.isEmpty(productList.get()))
//			return new ResponseEntity("No Content Found", HttpStatus.NO_CONTENT);
//
//		return responseEntity;
//	}


}
