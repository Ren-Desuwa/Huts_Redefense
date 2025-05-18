package model;

import java.time.LocalDate;

public class Reading {
	private int reading_id;
	private int user_id;
	private LocalDate date;
	private String type; // "Electricity", "Water", "Gas"
	private double reading;
	private double rate;
	private double total_price;
	
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
	
	public Reading(int reading_id, int user_id, LocalDate date, String type, int reading, int rate, int total_price) {
		this.reading_id = reading_id;
		this.user_id = user_id;
		this.date = date;
		this.type = type;
		this.reading = reading;
		this.rate = rate;
		this.total_price = total_price;
	}
	
	public int getReading_Id() {return reading_id;}
	public int getUser_Id() {return user_id;}
	public LocalDate getDate() {return date;}
	public String getType() {return type;}
	public double getReading() {return reading;}
	public double getRate() {return rate;}
	public double getTotal_Price() {return total_price;}
	
	public void setReading_Id(int reading_id) {this.reading_id = reading_id;}
	public void setUser_Id(int user_id) {this.user_id = user_id;}
	public void setDate(LocalDate date) {this.date = date;}
	public void setType(String type) {this.type = type;}
	public void setReading(int reading) {this.reading = reading;}
	public void setRate(int rate) {this.rate = rate;}
	public void setTotal_Price(int total_price) {this.total_price = total_price;}
}
