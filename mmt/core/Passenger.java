package mmt.core;

import java.time.LocalTime;

import java.util.List;

import java.util.*;


import java.time.Duration;

import java.time.LocalDate;


public class Passenger implements java.io.Serializable{

	private Category _category;
	private int _id;
	private String _name;
	private int _numItineraries;
	private double _balance;
	private String _accumulatedTime;
	private Map<Integer, Itinerary> _itineraryMap = new HashMap<Integer, Itinerary>();
	private Map<LocalDate, Itinerary> _itineraryTree = new TreeMap<LocalDate, Itinerary>();
	private Duration _travelDuration;
	private long _passangerTime;


	protected Passenger(int id, String name) {
		_name = name;
		_id = id;
		_category = new Normal();
		_accumulatedTime = "00:00"; 

	}


	protected int getId() {           
    	return _id;
  	}

  	protected void setName(String name) {
  		_name = name;
  	}

  	protected void addItinerary(int id, Itinerary itinerary) {
  		_itineraryTree.put(itinerary.getDate(),itinerary);
  		_itineraryMap.put(id, itinerary);
  		_numItineraries++;

  	}

  	protected int getNumItineraries(){
  		return _numItineraries;
  	}


  	public String toString(){
  		return "" + _id + "|" +_name + "|"+_category.getCategory() + "|" +_numItineraries + "|"  + String.format("%.2f", _balance) + "|" +  _accumulatedTime;
  	}

  	protected String itineraryString(){

  		return "== Passageiro " + _id + ": " + _name + " ==" ;
	}


	protected String getItineraryString(int id){

		Itinerary i = _itineraryMap.get(id);

		return i.toString();

	}


	protected int getSegmentCont(int itineraryId){

		Itinerary i = _itineraryMap.get(itineraryId);
		
		return i.getSegmentCont();
	}


	protected int[] getServiceId(int id) {

		Itinerary i = _itineraryMap.get(id);

		return i.getServiceId();


	}

	protected String[] getDepartingStation(int id) {
		Itinerary i = _itineraryMap.get(id);

		return i.getDepartingStation();
	}

	protected String[] getArrivalStation(int id) {
		Itinerary i = _itineraryMap.get(id);

		return i.getArrivalStation();
	}


	protected double[] getSegmentCost(int id) {

		Itinerary i = _itineraryMap.get(id);

		return i.getSegmentCost();

	}



	protected void setAccumulatedTime( long itineraryTime) {


		_passangerTime += itineraryTime;


		int travelHours = (int)_passangerTime/60;

		int travelMinutes = (int)_passangerTime%60;


		_accumulatedTime = String.format("%02d:%02d",travelHours,travelMinutes);



	}


	protected void setBalance(double itineraryCost) {


		double last10ItineraryCost = 0;
		int i;


		if (_numItineraries > 10) {                                        

			for (i = _numItineraries; i > (_numItineraries-10) ; i--) {

				Itinerary itinerary = _itineraryMap.get(i);

				last10ItineraryCost += itinerary.getCost();    
			}
		}

		else {
			for (i = 1; i <= (_numItineraries) ; i++) {
				Itinerary itinerary = _itineraryMap.get(i);

				last10ItineraryCost += itinerary.getCost();
			}
		}


		_balance += itineraryCost - (itineraryCost * _category.getDiscount());


		if( last10ItineraryCost > 2500) {
			_category = new Special();

		}


		if( last10ItineraryCost > 250 && last10ItineraryCost <= 2500) {

			_category = new Frequent();

		}

		if(last10ItineraryCost < 250) {

			_category = new Normal();

		}


	}


	protected Collection<Itinerary> getItineraries() {

		Iterator<Map.Entry<Integer, Itinerary>> iterator = _itineraryMap.entrySet().iterator();

		List<Itinerary> itineraryList  = new ArrayList<Itinerary>();



    

    
    	while (iterator.hasNext()) {  

      		Map.Entry<Integer, Itinerary> entry = iterator.next(); 
      
      		Itinerary itinerary = entry.getValue();

      		itineraryList.add(itinerary); 

    	}

    	
 

    	return Collections.unmodifiableCollection(itineraryList);


	}


}
