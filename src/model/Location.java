package model;

import java.io.Serializable;

public class Location implements Serializable {
	private int id;
	private int tourId;
	private String location;
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTourId() {
		return tourId;
	}
	public void setTourId(int tourId) {
		this.tourId = tourId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
