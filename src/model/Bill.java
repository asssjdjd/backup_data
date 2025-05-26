package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.*;

public class Bill implements Serializable {
	private int id;
	private int clientId;
	private int tourId;
	private float total;
	private String note;
	private LocalDate paymentDate;
	private float saleOff;
	private int bookedPeople;
	
	
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Bill(int id, int clientId, int tourId, float total, String note, LocalDate paymentDate, float saleOff,
			int bookedPeople) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.tourId = tourId;
		this.total = total;
		this.note = note;
		this.paymentDate = paymentDate;
		this.saleOff = saleOff;
		this.bookedPeople = bookedPeople;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getClientId() {
		return clientId;
	}


	public void setClientId(int clientId) {
		this.clientId = clientId;
	}


	public int getTourId() {
		return tourId;
	}


	public void setTourId(int tourId) {
		this.tourId = tourId;
	}


	public float getTotal() {
		return total;
	}


	public void setTotal(float total) {
		this.total = total;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public LocalDate getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}


	public float getSaleOff() {
		return saleOff;
	}


	public void setSaleOff(float saleOff) {
		this.saleOff = saleOff;
	}


	public int getBookedPeople() {
		return bookedPeople;
	}


	public void setBookedPeople(int bookedPeople) {
		this.bookedPeople = bookedPeople;
	}
}
