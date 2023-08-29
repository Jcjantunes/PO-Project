package mmt.core;

import mmt.core.Segment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;

import java.util.*;

import java.time.Duration;


public class Itinerary implements java.io.Serializable {
 
	private double _itineraryCost;
	private LocalTime _itineraryTime;
	private LocalDate _date;
	//private int _passengerId;
	private List<Segment> _segmentList = new ArrayList<Segment>();
	private int _id;
	private double _cost;
	private int _segmentCont;
	private Duration _duration;
	private long _time = 0;
	private LocalTime _departureTime;
	private LocalTime _arrivalTime;
	private int _printId;
	private int contador;



	protected Itinerary(LocalDate date, int id) {
		_date = date;
		_id = id;
	}



	protected void addSegment(String departingStation, LocalTime departingTrainstop,String arrivalStation,  LocalTime arrivalTrainstop,int serviceId, double serviceCost, Duration serviceDuration, int arrayLength) {
		Segment segment = new Segment(departingStation, departingTrainstop, arrivalStation, arrivalTrainstop, serviceId, serviceCost, serviceDuration); 
		_segmentList.add(segment);

		if (_segmentCont == 0) {
			_departureTime = segment.getDepartingTrainstopTime();
		}

		if (_segmentCont == arrayLength) {
			_arrivalTime = segment.getArrivalTrainstopTime();
		}

		_segmentCont++;
		_cost += segment.getCost();


	}


	protected void addReverseSegment(String departingStation, LocalTime departingTrainstop,String arrivalStation,  LocalTime arrivalTrainstop,int serviceId, double serviceCost, Duration serviceDuration, int arrayLength) {
		Segment segment = new Segment(departingStation, departingTrainstop, arrivalStation, arrivalTrainstop, serviceId, serviceCost, serviceDuration); 
		_segmentList.add(segment);

		
		
		if (_segmentCont == 0) {
			_arrivalTime = segment.getArrivalTrainstopTime();
		}

		if (_segmentCont == arrayLength) {
			_departureTime = segment.getDepartingTrainstopTime();
		}

		_segmentCont++;

		_cost += segment.getCost();


	}

	protected int getId(){
		return _id;
	}

	public String toString(int id){
		return "Itinerário " + id + " para " + _date + " @ " + String.format("%.2f", _cost);
	}



	protected int getSegmentCont() {
		return _segmentCont;
	}


	
	protected int[] getServiceId() {

		int[] id = new int[_segmentCont];
		int i = 0;



		for (Segment s: _segmentList) {

			id[i++] = s.getId();
		}

	return id;



	}




	protected String[] getDepartingStation() {

		String[] station = new String[_segmentCont];
		int i = 0;



		for (Segment s: _segmentList) {

			station[i++] = s.getDepartingTrainStop();
		}


		return station;

	}

	protected String[] getArrivalStation() {

		String[] station = new String[_segmentCont];
		int i = 0;



		for (Segment s: _segmentList) {

			station[i++] = s.getArrivalTrainStop();
		}


		return station;

	}


	protected double[] getSegmentCost() {

		double[] segmentCost = new double[_segmentCont];
		int i = 0;



		for (Segment s: _segmentList) {

			segmentCost[i++] = s.getCost();
		}


		return segmentCost;
	}



	protected Duration getDuration(){
		return _duration;
	}


	protected void setTime() {
		Duration durationTime = Duration.between(_departureTime,_arrivalTime);
		_time += durationTime.toMinutes();
	}

	protected long getTime(){
		return _time;
	}


	protected double getCost(){
		return _cost;
	}


	protected LocalDate getDate() {
		return _date;
	}


	protected LocalTime getDepartingTime() {
		return _departureTime;
	}

	protected LocalTime getArrivalTime() {
		return _arrivalTime;
	}



}
