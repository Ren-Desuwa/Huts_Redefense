
package model;

import java.time.LocalDate;

public class Reading {
	private int reading_id;
	private int user_id;
	private LocalDate date;
	private String type; // "electricity", "water", "gas", "other"
	private double reading;
	private double rate;
	private double total_price;
	
	public Reading() {}
	
	public Reading(int user_id, LocalDate date, String type, double reading, double rate, double total_price) {
		this.user_id = user_id;
		this.date = date;
		this.type = type;
		this.reading = reading;
		this.rate = rate;
		this.total_price = total_price;
	}
	
	public Reading(int reading_id, int user_id, LocalDate date, String type, double reading, double rate, double total_price) {
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
	public void setReading(double reading) {this.reading = reading;}
	public void setRate(double rate) {this.rate = rate;}
	public void setTotal_Price(double total_price) {this.total_price = total_price;}
}
/*
 * File: Reading.java
 *
 * Description:
 * This file defines the `Reading` class, which represents a utility reading (e.g., electricity, water, or gas) for a user. 
 * It encapsulates the details of a reading, including its unique ID, associated user ID, date, type, value, rate, and total price.
 * The class provides getter and setter methods to access and modify the reading's attributes.
 *
 * Variables:
 * - `reading_id` (int): A unique identifier for the reading.
 * - `user_id` (int): The ID of the user associated with the reading.
 * - `date` (LocalDate): The date when the reading was recorded.
 * - `type` (String): The type of the reading (e.g., "electricity", "water", "gas").
 * - `reading` (double): The value of the reading (e.g., kWh for electricity, m³ for water).
 * - `rate` (double): The rate applied to the reading (e.g., cost per unit).
 * - `total_price` (double): The total price calculated based on the reading and rate.
 *
 * Constructors:
 * 1. `Reading()`: Default constructor that initializes an empty reading object.
 * 2. `Reading(int user_id, LocalDate date, String type, double reading, double rate, double total_price)`:
 *    - Initializes a reading object with the specified user ID, date, type, reading value, rate, and total price.
 * 3. `Reading(int reading_id, int user_id, LocalDate date, String type, double reading, double rate, double total_price)`:
 *    - Initializes a reading object with the specified reading ID, user ID, date, type, reading value, rate, and total price.
 *
 * Methods:
 * 1. `getReading_Id()`: Returns the unique ID of the reading.
 * 2. `getUser_Id()`: Returns the ID of the user associated with the reading.
 * 3. `getDate()`: Returns the date of the reading.
 * 4. `getType()`: Returns the type of the reading.
 * 5. `getReading()`: Returns the value of the reading.
 * 6. `getRate()`: Returns the rate applied to the reading.
 * 7. `getTotal_Price()`: Returns the total price of the reading.
 * 8. `setReading_Id(int reading_id)`: Sets the unique ID of the reading.
 * 9. `setUser_Id(int user_id)`: Sets the ID of the user associated with the reading.
 * 10. `setDate(LocalDate date)`: Sets the date of the reading.
 * 11. `setType(String type)`: Sets the type of the reading.
 * 12. `setReading(double reading)`: Sets the value of the reading.
 * 13. `setRate(double rate)`: Sets the rate applied to the reading.
 * 14. `setTotal_Price(double total_price)`: Sets the total price of the reading.
 *
 * Usage:
 * The `Reading` class is used to represent and manage utility readings in the system. 
 * It is commonly used in database operations, utility tracking, and calculations involving utility costs.
 */
