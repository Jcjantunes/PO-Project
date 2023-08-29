package mmt.core;     

import java.time.LocalTime;
import java.time.LocalDate;


import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;

import java.util.*;

import java.time.Duration;

import java.time.format.DateTimeFormatter;




public class Service implements java.io.Serializable{

	private int _id;            
	private double _totalCost;
	private List<Trainstop> _trainstopList = new ArrayList<Trainstop>();
	private Trainstop _departingTrainstop;
	private Trainstop _arrivingTrainstop;
	private Duration _duration;


	protected Service(int id, double totalCost, Duration serviceDuration) {
		_id = id;
		_totalCost = totalCost;
		_duration = serviceDuration;

	}


	public String toString() {
		return "Serviço #" + _id + " @ " + String.format("%.2f", _totalCost);
	}

	protected String segmentString(double segmentCost) {
		return "Serviço #" + _id + " @ " + String.format("%.2f", segmentCost);

	}


	protected void addTrainstop(LocalTime time, String name) {

		Trainstop trainstop = new Trainstop(time);

		trainstop.addStation(name);

		_trainstopList.add(trainstop);


	}



	protected Collection<String> getTrainstops(){


		List<String> trainstops = new ArrayList<String>();


    	for (Trainstop t: _trainstopList) {

    		trainstops.add(t.toString());
    	}

    	return trainstops;

	}


	protected Collection<String> getItineraryTrainstops(String departingTrainstop, String arrivalTrainstop){


		List<String> trainstops = new ArrayList<String>();
		int flag = 0;


    	for (Trainstop t: _trainstopList) {
    		

    		if (departingTrainstop.equals(t.getStation())) {
    			flag = 1;
    		}

   			if (flag == 1) {
   				trainstops.add(t.toString());
   			}

   			if (arrivalTrainstop.equals(t.getStation())) {
    			flag = 0;
    		}

    	} 

    	return trainstops;

	}



	protected void addDepartingTrainstop(LocalTime time, String name) {


		_departingTrainstop = new Trainstop(time);

		_departingTrainstop.addStation(name);

	}

	protected void addArrivingTrainstop(LocalTime time, String name) {


		_arrivingTrainstop = new Trainstop(time);

		_arrivingTrainstop.addStation(name);

	}



	protected String getDepartingStation(){
		return _departingTrainstop.getStation();

	}


	
	protected LocalTime getDepartingTime() {

		return _departingTrainstop.getTime();

	}

	protected String getArrivingStation(){
		return _arrivingTrainstop.getStation();

	}

	protected LocalTime getArrivingTime() {

		return _arrivingTrainstop.getTime();

	}


	protected LocalTime[] getTrainstop(String name){

		//LocalTime trainstop;
		//LocalTime trainstopTime;
		LocalTime[] trainstop = new LocalTime[1];



    	for (Trainstop t: _trainstopList) {

    		if(name.equals(t.getStation())) {          //melhorar ciclo

    			trainstop[0] = t.getTime();   			

    		}


    	
    	}

    	//trainstopTime = trainstop;


    	return trainstop;

 
	}


	protected String getNextTrainstop( String station) {

		String nextTrainstop = "";

		for (Trainstop t: _trainstopList) {

    		if(station.equals(t.getStation())) {          //melhorar ciclo

    			LocalTime trainstop = t.getTime();  

    			DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm"); 

    			nextTrainstop = trainstop.format(formater); 			

    		}


    	
    	}

    	return nextTrainstop;

	}


	protected double getCost(){
		return _totalCost;
	}


	protected Duration getDuration(){
		return _duration;

	}


	protected int getId(){
		return _id;
	}

	protected String getNextStation(String station){

		String nextStation = "";
		int flag = 0;

		for (Trainstop t: _trainstopList) {

			if(flag == 1) {
				nextStation = t.getStation();
			}

    		if(station.equals(t.getStation())) {          //melhorar ciclo
    			flag=1;


    		}	
    	}

    	return nextStation;

	}

	protected LocalTime getTrainstopAt( String arrivalStation, LocalTime departingTrainstop) {


		LocalTime arrivalTrainstop = null;


		for (Trainstop t: _trainstopList) {

    		if(arrivalStation.equals(t.getStation()) && t.getTime().isAfter(departingTrainstop)) {          //melhorar ciclo

    			arrivalTrainstop = t.getTime();
  			   			

    		}


    	
    	}

    	return arrivalTrainstop;



	}

	protected Collection<LocalTime> getAllNextTrainStops(String firstStation) {

		List<LocalTime> stations = new ArrayList<LocalTime>();
		int flag = 0;

		for(Trainstop t: _trainstopList) {

			if(flag==1) {
				stations.add(t.getTime());
			}

			if(t.getStation().equals(firstStation)) {
				flag = 1;
			}

		}

		return stations;
	}

	protected String getStationName(LocalTime time) {

		for(Trainstop t : _trainstopList) {
			if(time.compareTo(t.getTime()) == 0)
				return t.getStation();

		}
		return null;

	}

	protected LocalTime getStationTime(String station) {
		for(Trainstop t : _trainstopList) {
			if(station.equals(t.getStation()))
				return t.getTime();

		}
		return null;
	}


	protected Collection<LocalTime> getAllTrainstops() {
		List<LocalTime> trainstops = new ArrayList<LocalTime>();

		for(Trainstop t : _trainstopList) {
			trainstops.add(t.getTime());

		}

		return trainstops;
	}


	
}