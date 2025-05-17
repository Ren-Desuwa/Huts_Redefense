package model;

import java.time.LocalDate;

public class Reading {
	private int user_id;
	private LocalDate date;
	private String type; // "Electricity", "Water", "Gas"
	private int reading;
	private int rate;
	private int total_price;
	
	public Reading(int user_id, LocalDate date, String type, int reading, int rate, int total_price) {
		this.user_id = user_id;
		this.date = date;
		this.type = type;
		this.reading = reading;
		this.rate = rate;
		this.total_price = total_price;
	}
	
	public Reading(int user_id, LocalDate date, String type, int reading, int total_price) {
		this.user_id = user_id;
		this.date = date;
		this.type = type;
		this.reading = reading;
		this.total_price = total_price;
	}
	
	
	public int getUser_id() {return user_id;}
	public LocalDate getDate() {return date;}
	public String getType() {return type;}
	public int getReading() {return reading;}
	public int getRate() {return rate;}
	public int getTotal_price() {return total_price;}
	
	public void setUser_id(int user_id) {this.user_id = user_id;}
	public void setDate(LocalDate date) {this.date = date;}
	public void setType(String type) {this.type = type;}
	public void setReading(int reading) {this.reading = reading;}
	public void setRate(int rate) {this.rate = rate;}
	public void setTotal_price(int total_price) {this.total_price = total_price;}
}
