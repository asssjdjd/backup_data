package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.*;

import model.User;

public class Tour implements Serializable {
	private int id;
	private String nameTour;
	private String depaturePoint;
	private String destination;
	private float price;
	private int maxCapacity;
	private int bookedPeople;
	private float saleOff;
	private String description; 
	private String userId;
	
	private User user;

//	bổ sung
	private LocalDate startDate; 
	private LocalDate endDate;
	private String note;
	
	
	public Tour() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNameTour() {
		return nameTour;
	}


	public void setNameTour(String nameTour) {
		this.nameTour = nameTour;
	}


	public String getDepaturePoint() {
		return depaturePoint;
	}


	public void setDepaturePoint(String depaturePoint) {
		this.depaturePoint = depaturePoint;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getMaxCapacity() {
		return maxCapacity;
	}


	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}


	public int getBookedPeople() {
		return bookedPeople;
	}


	public void setBookedPeople(int bookedPeople) {
		this.bookedPeople = bookedPeople;
	}


	public float getSaleOff() {
		return saleOff;
	}


	public void setSaleOff(float saleOff) {
		this.saleOff = saleOff;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Tour(int id, String nameTour, String depaturePoint, String destination, float price, int maxCapacity,
			int bookedPeople, float saleOff, String description, String userId, LocalDate startDate,
			LocalDate endDate, String note) {
		super();
		this.id = id;
		this.nameTour = nameTour;
		this.depaturePoint = depaturePoint;
		this.destination = destination;
		this.price = price;
		this.maxCapacity = maxCapacity;
		this.bookedPeople = bookedPeople;
		this.saleOff = saleOff;
		this.description = description;
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.note = note;
	}
}
