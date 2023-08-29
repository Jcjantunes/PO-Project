package mmt.core;

import java.time.LocalTime;
import java.time.LocalDate;

import java.util.*;

import java.time.Duration;




public class Trainstop implements java.io.Serializable{

	private LocalTime _time;
	private Station _station;
	private Service _service;

	 

	protected Trainstop(LocalTime time){
		_time = time;
	}

	
	public String toString(){
		return "" + _time + " " + _station.toString();

	}


	protected void addStation(String name) {
		_station = new Station(name);

	}

	protected void addService(int id, double cost, Duration duration) {
		_service = new Service(id,cost, duration);


	}


	protected String getStation() {
		return _station.toString();
	}


	protected LocalTime getTime() {
		return _time;
	}


	protected int getServiceId(){
		return _service.getId();
	}






}

