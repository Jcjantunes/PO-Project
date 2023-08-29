package mmt.core;


import java.time.LocalTime;
import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;
import mmt.core.Trainstop;

import java.util.*;

import java.time.Duration;
import java.time.LocalTime;




public class Station implements java.io.Serializable{

	private String _name;
	private List<Trainstop> _trainstopList = new ArrayList<Trainstop>();



	protected Station(String name) {
		_name = name;
	}


	protected void addTrainstop(LocalTime time, int id, double cost) {

		Trainstop trainstop = new Trainstop(time);

		trainstop.addService(id,cost, null);

		_trainstopList.add(trainstop);

	}




	public String toString(){
		return _name;
	}


	protected Collection<Integer> getFilteredServices(LocalTime departureTime) {

		
		List<Integer> filteredServices  = new ArrayList<Integer>();


		for (Trainstop t: _trainstopList) {

			if (!departureTime.isAfter(t.getTime())) {

				int id = t.getServiceId();

				filteredServices.add(id); 
			}

       
    	}

    	return Collections.unmodifiableCollection(filteredServices);
	}



	protected LocalTime[] getFilteredTrainstops(LocalTime departureTime, int arrayLength) {

		
		LocalTime[] trainstops = new LocalTime[arrayLength];
		int j = 0;

		
		for (Trainstop t: _trainstopList) {

			if (!departureTime.isAfter(t.getTime())) {

				trainstops[j++] = t.getTime();

				
 
			}

       
    	}

    	return trainstops;
	}







}