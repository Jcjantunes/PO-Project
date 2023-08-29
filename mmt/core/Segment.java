package mmt.core;

import java.time.LocalTime;

import java.util.*;


import java.time.Duration;



public class Segment implements java.io.Serializable {

	private double _segmentCost;
	private Trainstop _departingTrainStop;
	private Trainstop _arrivalTrainStop;
	private int _serviceId;
	private long _segmentTime;
	private Duration _segmentDuration;


	
	protected Segment(String departingStation, LocalTime departingTrainstop,String arrivalStation,  LocalTime arrivalTrainstop, int serviceId, double serviceCost, Duration serviceDuration) {
		
		_departingTrainStop = new Trainstop(departingTrainstop);
		_departingTrainStop.addStation(departingStation);
		_arrivalTrainStop = new Trainstop(arrivalTrainstop);
		_arrivalTrainStop.addStation(arrivalStation);
		_serviceId = serviceId;
		
		_segmentDuration = Duration.between(departingTrainstop,arrivalTrainstop);
		_segmentTime = _segmentDuration.toMinutes();
		long serviceTime = serviceDuration.toMinutes(); 

		//System.out.println("service cost:" + serviceCost);

		_segmentCost = (serviceCost * _segmentTime) / serviceTime;



	}   




	public double getCost() {
		return _segmentCost;
	}


	protected Duration getDuration(){
		return _segmentDuration;
	}


	protected long getTime(){
		return _segmentTime;
	
	}

	protected int getId() {
		return _serviceId;
	}


	protected String getDepartingTrainStop(){
		return _departingTrainStop.getStation();

	}


	protected String getArrivalTrainStop(){
		return _arrivalTrainStop.getStation();

	}


	protected LocalTime getDepartingTrainstopTime() {
		return _departingTrainStop.getTime();
	}

	protected LocalTime getArrivalTrainstopTime() {
		return _arrivalTrainStop.getTime();
	}

}