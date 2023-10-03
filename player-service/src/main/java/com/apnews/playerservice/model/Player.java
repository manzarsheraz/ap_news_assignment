package com.apnews.playerservice.model;

import java.util.HashSet;
import java.util.Set;

import com.apnews.playerservice.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Player {

//	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// Email is not null
	@NotBlank
	private String email;

	// Level is not null and in the range of 1~10
	@NotNull(message = "Level is a required field!")
	@Positive(message = "Level can't have a negative value!")
	@DecimalMax(value = "10", message = "Level can't be greater than 10!")
	private byte level;

	// As per requirement age is not null, but in common sense age >= 1
	@NotNull(message = "Age is a required field!")
	@Positive(message = "Age value can't be negative!")
	private byte age;

	private Gender gender;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "player_sport", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "sport_id"))
	private Set<Sport> playerSports;

	public Player() {
	}

	public Player(@NotEmpty String email, byte level, byte age, Gender gender) {
		super();
		this.email = email;
		this.level = level;
		this.age = age;
		this.gender = gender;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Set<Sport> getPlayerSports() {
		if (this.playerSports == null) {
			this.playerSports = new HashSet<>();
		}
		return playerSports;
	}

	public void setPlayerSports(Set<Sport> playerSports) {
		this.playerSports = playerSports;
	}

}
