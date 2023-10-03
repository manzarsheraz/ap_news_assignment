package com.apnews.playerservice.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sport {

//	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// Name is not null
	@NotBlank
	private String name;

	@ManyToMany(mappedBy = "playerSports")
	private Set<Player> associatedPlayers;

	public Sport() {

	}

	public Sport(@NotBlank String name) {
		super();
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Player> getAssociatedPlayers() {
		if (this.associatedPlayers == null) {
			this.associatedPlayers = new HashSet<>();
		}
		return associatedPlayers;
	}

	public void setAssociatedPlayers(Set<Player> associatedPlayers) {
		this.associatedPlayers = associatedPlayers;
	}

}
