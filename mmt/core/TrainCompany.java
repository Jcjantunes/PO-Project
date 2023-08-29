package mmt.core;

import mmt.core.exceptions.BadDateSpecificationException;
import mmt.core.exceptions.BadEntryException;
import mmt.core.exceptions.BadTimeSpecificationException;
import mmt.core.exceptions.InvalidPassengerNameException;
import mmt.core.exceptions.NoSuchDepartureException;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.core.exceptions.NoSuchServiceIdException;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.core.exceptions.NoSuchItineraryChoiceException;
import mmt.core.exceptions.NonUniquePassengerNameException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.app.exceptions.NoSuchServiceException;
import mmt.app.exceptions.NoSuchStationException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;





import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;
import java.util.Comparator;

import java.time.LocalTime;
import java.time.LocalDate;


import mmt.core.Passenger;
import mmt.core.Service;

import java.time.Duration;




/**
 * A train company has schedules (services) for its trains and passengers that
 * acquire itineraries based on those schedules.
 */



public class TrainCompany implements java.io.Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201708301010L;

  private int _nextPassengerId;

  private Map<Integer, Passenger> _passengersMap = new HashMap<Integer, Passenger>();

  private Map<Integer, Service> _serviceMap = new TreeMap<Integer, Service>();

  private Map<String,Station> _stationsMap = new HashMap<String,Station>();

  private List<Itinerary> _itineraryList = new ArrayList<Itinerary>();

  private Map<Integer, Itinerary> _itineraryChoiceMap = new HashMap<Integer, Itinerary>();

  
  /**
   * regista um passageiro.
   * 
   * @param name novo nome.
   */

  public void registerPassenger(String name) {

    int id =_nextPassengerId++;                                
    Passenger p = new Passenger(id, name);

    _passengersMap.put(id, p);
  
  }

  /**
   * altera um nome de um passageiro.
   * 
   * @param id novo id.
   * @param name novo nome.
   */


  public void changePassengerName(int id,String name) throws NoSuchPassengerIdException {

    Passenger p = _passengersMap.get(id);

    if (p==null){
      throw new NoSuchPassengerIdException(id);
    }

    p.setName(name);

  } 

  /**
   * mostra um passageiro com um id proprio.
   * 
   * @param id id do passageiro.
   * @return inforcao do passageiro.
   */

  public String showPassengerbyId(int id) throws NoSuchPassengerIdException {

    Passenger p = _passengersMap.get(id);

    if (p==null)
      throw new NoSuchPassengerIdException(id);


    return p.toString();
    
  }

  /**
   * obtem todos os passageiros registados.
   * 
   * @return lista com todos os passageiros registados.
   */

  public Collection<String> getPassengers() {


    List<String> passengersList = new ArrayList<String>();

    Iterator<Map.Entry<Integer, Passenger>> iterator = _passengersMap.entrySet().iterator();  

    
    while (iterator.hasNext()) {  
      Map.Entry<Integer, Passenger> entry = iterator.next(); 
      
      Passenger p = entry.getValue();

      passengersList.add(p.toString()); 

    }


    return Collections.unmodifiableCollection(passengersList);
  }

  /**
   * regista um novo servico.
   * 
   * @param id novo id do servico.
   * @param cost custo do servico.
   * @param station array de estacoes.
   * @param trainstop array de trainstops.
   */

  public void registerService(int id, double cost, String[] station,LocalTime[] trainstop) {


    int arrayLength = station.length;
    
    Duration serviceDuration = Duration.between(trainstop[0],trainstop[arrayLength-1]);

    Service s = new Service(id,cost,serviceDuration);      
    int i;
    



    s.addDepartingTrainstop(trainstop[0] , station[0]);

    


    for (i=0; i < arrayLength; i++) {

      s.addTrainstop(trainstop[i] , station[i]);
      
      _serviceMap.put(id,s);
      
    }



    s.addArrivingTrainstop(trainstop[arrayLength-1] , station[arrayLength-1]); 
  }


  /**
   * regista uma nova estacao.
   * 
   * @param name nome para a estacao.
   */


  public void registerStation (int id, double cost, String[] station,LocalTime[] trainstop) {

    int i;
    int arrayLength = station.length;

    
    for(i=0; i < arrayLength; i++) {


      Station s = _stationsMap.get(station[i]);

      if(s == null){
        s = new Station(station[i]);
      }

      s.addTrainstop(trainstop[i],id,cost);
      _stationsMap.put(station[i], s);
    
    }

  }


  /**
   * obtem todos os servicos registados.
   * 
   * @return lista com todos os servicos registados.
   */


  public Collection<String> getServices() {


    List<String> serviceList  = new ArrayList<String>();

    Iterator<Map.Entry<Integer, Service>> iterator = _serviceMap.entrySet().iterator(); 

    
    while (iterator.hasNext()) {  
      Map.Entry<Integer, Service> entry = iterator.next(); 
      
      Service s = entry.getValue();

      serviceList.add(s.toString()); 

      serviceList.addAll(s.getTrainstops());
    }


    return Collections.unmodifiableCollection(serviceList);

  }

  /**
   * mostra servico com as estacoes associadas a esse servico.
   * 
   * @param id id do servico.
   * @return inforcao do servico.
   */

  public Collection<String> showServicebyNumber(int id) throws NoSuchServiceIdException{

    List<String> services = new ArrayList<String>();



    Service s = _serviceMap.get(id);

    if (s == null)
      throw new NoSuchServiceIdException(id);

    
    services.add(s.toString());

    services.addAll(s.getTrainstops());


    return Collections.unmodifiableCollection(services);

  }

  /**
   * mostra servicos com as mesmas estacoes de origem.
   * 
   * @param departingStation estacao de inicio.
   * @return lista de servicos com a mesma estacao de origem.
   */


  public Collection<String> showServicesbyDepartingStation(String departingStation) throws NoSuchStationNameException{




    Iterator<Map.Entry<Integer, Service>> iterator = _serviceMap.entrySet().iterator(); 

    List<Service> service  = new ArrayList<Service>();

    List<String> serviceDepartingStation  = new ArrayList<String>();

    Station station = _stationsMap.get(departingStation);


    if (station == null){
      throw new NoSuchStationNameException(departingStation);
    }
    

    while (iterator.hasNext()) {  
      Map.Entry<Integer, Service> entry = iterator.next(); 
      
      Service s = entry.getValue();


      if (departingStation.equals(s.getDepartingStation())){

        service.add(s);

      }



    }

    Comparator<Service> comparator;

    comparator = new Comparator<Service>() {
      public int compare(Service s1, Service s2) {
        return s1.getDepartingTime().compareTo(s2.getDepartingTime());
      }
    };

    Collections.sort(service, comparator);


    for (Service s: service) {

        serviceDepartingStation.add(s.toString());

        serviceDepartingStation.addAll(s.getTrainstops());

    }

    return Collections.unmodifiableCollection(serviceDepartingStation);


  }

  /**
   * mostra servicos com as mesmas estacoes de destino.
   * 
   * @param arrivingStation estacao de inicio.
   * @return lista de servicos com a mesma estacao de destino.
   */


  public Collection<String> showServicesbyArrivingStation(String arrivingStation) throws NoSuchStationNameException{




    Iterator<Map.Entry<Integer, Service>> iterator = _serviceMap.entrySet().iterator(); 

    List<Service> service  = new ArrayList<Service>();

    List<String> serviceArrivingStation  = new ArrayList<String>();

    Station station = _stationsMap.get(arrivingStation);


    if (station == null){
      throw new NoSuchStationNameException(arrivingStation);
    }
    

    while (iterator.hasNext()) {  
      Map.Entry<Integer, Service> entry = iterator.next(); 
      
      Service s = entry.getValue();


      if (arrivingStation.equals(s.getArrivingStation())){

        service.add(s);

      }



    }

    Comparator<Service> comparator;

    comparator = new Comparator<Service>() {
      public int compare(Service s1, Service s2) {
        return s1.getArrivingTime().compareTo(s2.getArrivingTime());
      }
    };

    Collections.sort(service, comparator);


    for (Service s: service) {

        serviceArrivingStation.add(s.toString());

        serviceArrivingStation.addAll(s.getTrainstops());

    }

    return Collections.unmodifiableCollection(serviceArrivingStation);


  }


  /**
   * Regista um itinerario.
   * 
   * @param passengerId id do passageiro
   * @param date data do itinerario
   * @param serviceId array de Ids de servicos
   * @param departureTrainStop array de horas de partida de paragens
   * @param arrivalTrainStop array de horas de chegada de paragens
   */


  public void registerItinerary (int passengerId,LocalDate date,int[] serviceId, String[] departureTrainStop, String[] arrivalTrainStop) {


    Passenger p = _passengersMap.get(passengerId);

    int itineraryId = p.getNumItineraries();

    itineraryId++; 

    Itinerary itinerary = new Itinerary(date,itineraryId);

    LocalTime[] departureTrainstop = new LocalTime[1];
    LocalTime[] arrivalTrainstop = new LocalTime[1];
    

    int i;
    int arrayLength = departureTrainStop.length;

    for (i=0; i < arrayLength; i++) {

      Service s = _serviceMap.get(serviceId[i]);

      departureTrainstop = s.getTrainstop(departureTrainStop[i]);

      arrivalTrainstop = s.getTrainstop(arrivalTrainStop[i]);




      itinerary.addSegment(departureTrainStop[i], departureTrainstop[0], arrivalTrainStop[i], arrivalTrainstop[0], serviceId[i], s.getCost(), s.getDuration(), arrayLength-1);
      

      
    }

    p.addItinerary(itineraryId, itinerary);

    itinerary.setTime();

    p.setAccumulatedTime(itinerary.getTime());

    p.setBalance(itinerary.getCost());

    _itineraryList.add(itinerary);



  } 

  /**
   * Mostra todos os itinerarios associados a um passageiro.
   * @param id id do passageiro
   * @return todos os itinerarios de um passageiro.
   */


  public Collection<String> showItinerariesbyPassenger(int id) throws NoSuchPassengerIdException{ //fazer exceçao

    
    Passenger p = _passengersMap.get(id);
    int newItineraryId = 1;

    
    if (p == null){
      throw new NoSuchPassengerIdException(id);
    }

    
    List<String> itineraries  = new ArrayList<String>();

    int numItineraries = p.getNumItineraries();

    
    if(numItineraries==0) {
      return null;

    }


    int j;

    itineraries.add(p.itineraryString());



    ArrayList<Itinerary> itinerariesbyPassanger = new ArrayList<>(p.getItineraries());



    Comparator<Itinerary> comparator;

    comparator = new Comparator<Itinerary>() {
      public int compare(Itinerary i1, Itinerary i2) {

        if ((i1.getDate().compareTo(i2.getDate())) == 0 ) {


          if (i1.getDepartingTime().compareTo(i2.getDepartingTime()) == 0){


            if ( i1.getArrivalTime().compareTo(i2.getArrivalTime()) == 0 ) {


              return i1.getDuration().compareTo(i2.getDuration());
            }

            return i1.getArrivalTime().compareTo(i2.getArrivalTime()); 
          }

          return i1.getDepartingTime().compareTo(i2.getDepartingTime());

        }
      
        return i1.getDate().compareTo(i2.getDate());
      }
    };



    Collections.sort(itinerariesbyPassanger, comparator);


    
    
    for (Itinerary i: itinerariesbyPassanger) {

      
      itineraries.add(""); 


      itineraries.add(i.toString(newItineraryId++));

      int numSegments = i.getSegmentCont();


      int[] serviceId = new int[numSegments];
      double[] segmentCost = new double[numSegments];
      String[] serviceDepartingStation = new String[numSegments];
      String[] serviceArrivalStation = new String[numSegments];

      serviceId = i.getServiceId();
      serviceDepartingStation = i.getDepartingStation();
      serviceArrivalStation = i.getArrivalStation();
      segmentCost = i.getSegmentCost();


      
      for (j=0; j < numSegments; j++) {



        Service s = _serviceMap.get(serviceId[j]);

        itineraries.add(s.segmentString(segmentCost[j]));

        
        itineraries.addAll(s.getItineraryTrainstops(serviceDepartingStation[j],serviceArrivalStation[j]));
        

      }


    }

    return Collections.unmodifiableCollection(itineraries);
  }

  /**
   * Mostra todos os itinerarios na trainCompany.
   * @return todos os itinerarios na trainCompany.
   */
 
  public Collection<String> showAllItineraries()  {


    Iterator<Map.Entry<Integer, Passenger>> iterator = _passengersMap.entrySet().iterator();

    List<String> itineraries  = new ArrayList<String>();

    int j;


   

    while (iterator.hasNext()) {  

      Map.Entry<Integer, Passenger> entry = iterator.next(); 
      
      Passenger p = entry.getValue();

      int numItineraries = p.getNumItineraries();




     

      if( numItineraries != 0) {

        itineraries.add(p.itineraryString());

        int newItineraryId = 1;



        ArrayList<Itinerary> itinerariesbyPassanger = new ArrayList<>(p.getItineraries());

        
        Comparator<Itinerary> comparator;

        comparator = new Comparator<Itinerary>() {
          public int compare(Itinerary i1, Itinerary i2) {

          if ((i1.getDate().compareTo(i2.getDate())) == 0 ) {


            if (i1.getDepartingTime().compareTo(i2.getDepartingTime()) == 0){


              return i1.getArrivalTime().compareTo(i2.getArrivalTime()); 
            }

            return i1.getDepartingTime().compareTo(i2.getDepartingTime());

          }
      
          return i1.getDate().compareTo(i2.getDate());
          }
        };



        Collections.sort(itinerariesbyPassanger, comparator);


    
      
        for (Itinerary i: itinerariesbyPassanger) {

      
          itineraries.add(""); 


          itineraries.add(i.toString(newItineraryId++));

          int numSegments = i.getSegmentCont();


          int[] serviceId = new int[numSegments];
          double[] segmentCost = new double[numSegments];
          String[] serviceDepartingStation = new String[numSegments];
          String[] serviceArrivalStation = new String[numSegments];

          serviceId = i.getServiceId();
          serviceDepartingStation = i.getDepartingStation();
          serviceArrivalStation = i.getArrivalStation();
          segmentCost = i.getSegmentCost();


      
          for (j=0; j < numSegments; j++) {



            Service s = _serviceMap.get(serviceId[j]);

            itineraries.add(s.segmentString(segmentCost[j]));

        
            itineraries.addAll(s.getItineraryTrainstops(serviceDepartingStation[j],serviceArrivalStation[j]));
        

          }
        }

        itineraries.add("");
      }

    }

    return Collections.unmodifiableCollection(itineraries);
  }



  /**
   * Procura se existe itinerarios com a estacao de origem e a estacao de chegada.
   * @param passengerId id do passageiro
   * @param departureStation estacao de partida
   * @param arrivalStation estacao de chegada
   * @param departureDate data do itinerario
   * @param departureTime hora de partida
   * @return todos os itinerarios com as condicoes indicadas.
   */



  public Collection<String> searchItineraries(int passengerId, String departureStation, String arrivalStation, String departureDate,
                                              String departureTime) throws  NoSuchStationNameException ,NoSuchPassengerIdException, BadDateSpecificationException, BadTimeSpecificationException { 


    LocalTime time= null;
    
    try{
      DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
      LocalDate date = LocalDate.parse(departureDate,dateFormater);

    }catch(DateTimeParseException e){
      throw new BadDateSpecificationException(departureDate);
    
    
    }try{
      DateTimeFormatter timeFormater = DateTimeFormatter.ofPattern("HH:mm");
      time = LocalTime.parse(departureTime,timeFormater);
    
    }catch(DateTimeParseException e){
      throw new BadTimeSpecificationException(departureTime);
    }

    Passenger p = _passengersMap.get(passengerId);

    if(p==null) {
      throw new NoSuchPassengerIdException(passengerId);
    }



    if (searchDirectItinerary(passengerId, departureStation, arrivalStation, departureDate,departureTime) != null){
      return searchDirectItinerary(passengerId, departureStation, arrivalStation, departureDate,departureTime);

    }

    List<Service> serviceListAux = new ArrayList<>();

    ArrayList<Itinerary> _itinerariesComplex = new ArrayList<Itinerary>();


    for(Service s : _serviceMap.values()){
      List<LocalTime> trainstops = new ArrayList<LocalTime>(s.getAllTrainstops());
      for(LocalTime trainstopTime: trainstops) {

        if(!time.isAfter(trainstopTime) && s.getStationName(trainstopTime).equals(departureStation)) {
          Itinerary itinerary = searchComplexItinerary(passengerId,trainstopTime,departureStation, arrivalStation, departureDate,departureTime,serviceListAux,s);

          if(itinerary != null)
            _itinerariesComplex.add(itinerary);
        }
      }
    }


    if (_itinerariesComplex.size() != 0) {
      return getReverseSortedItineraries(_itinerariesComplex);
      
    }

    return null;

  }

  /**
   * Procura se existe itinerarios compostos com a estacao de origem e a estacao de chegada.
   * @param passengerId id do passageiro
   * @param departureStation estacao de partida
   * @param arrivalStation estacao de chegada
   * @param departureDate data do itinerario
   * @param departureTime hora de partida
   * @param serviceList lista de servicos ja vistos
   * @param service servico a ser considerado
   * @return todos os itinerarios compostos com as condicoes indicadas.
   */

  private int contador = 0;

  public Itinerary searchComplexItinerary(int passengerId,LocalTime departureTrainstopTime , String departureStation, String arrivalStation, String departureDate,
                                              String departureTime, List<Service> serviceList,Service service) throws  NoSuchStationNameException {
    

    DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
    LocalDate date = LocalDate.parse(departureDate,dateFormater);

    Passenger p = _passengersMap.get(passengerId);
    int itineraryId = p.getNumItineraries();

    Itinerary itinerary = new Itinerary(date, ++itineraryId);
    Itinerary itineraryAux = null;
    Itinerary itineraryTmp = null;

    DateTimeFormatter timeFormater = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime initTime = LocalTime.parse(departureTime,timeFormater);

    serviceList.add(service);

    String initStation = searchStations(service, departureStation, initTime);

    if(initStation == null) 
      return null;

    String endStation = searchStations(service, arrivalStation,service.getStationTime(initStation));

    if(endStation != null){

      LocalTime[] arrivalTime = new LocalTime[1];
      arrivalTime = service.getTrainstop(endStation);

      LocalTime[] timeDepartingStation = new LocalTime[1];
      timeDepartingStation = service.getTrainstop(initStation);
        
      contador++;
      itinerary.addReverseSegment(initStation, timeDepartingStation[0], endStation, arrivalTime[0], service.getId() , service.getCost(), service.getDuration(), 0);
      itineraryTmp = itinerary;
      return itinerary;
    }
    

    List<LocalTime> trainstopsList = new ArrayList<>(service.getAllNextTrainStops(departureStation));

    for(LocalTime trainstops: trainstopsList) {


      for(Service s : _serviceMap.values()){
        List<LocalTime> trainstopsList2 = new ArrayList<LocalTime>(s.getAllTrainstops());

        for(LocalTime trainstopTime: trainstopsList2) {

          if(!trainstops.isAfter(trainstopTime) && s.getStationName(trainstopTime).equals(service.getStationName(trainstops))) {

            if(!serviceList.contains(s)) {

              DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm"); 
              String nextTrainstop = trainstopTime.format(formater);
              contador++;

              itineraryAux = searchComplexItinerary(passengerId,trainstopTime,s.getStationName(trainstopTime),arrivalStation, departureDate,nextTrainstop,serviceList,s);
              
              if(itineraryAux != null){
                itineraryAux.addReverseSegment(departureStation,departureTrainstopTime,service.getStationName(trainstops), trainstops, service.getId() ,service.getCost(), service.getDuration(), contador-1);
              }
              

              contador = 0;
              serviceList.clear();
              return itineraryAux;     
        
            }
          }
        }
      }
    }

      return null;
    
  }


  /**
   * Procura se existe uma estacao com a hora maior ou igual a time
   * @param service servico a ser considerado
   * @param station estacao a ser comparada
   * @param time tempo a ser comparado
   * @param arrivalStation estacao de chegada
   * @param departureDate data do itinerario
   * @param departureTime hora de partida
   * @param serviceList lista de servicos ja vistos
   * @return a estacao se for encontrada null caso contrario.
   */

  public String searchStations(Service service, String station, LocalTime time) {
    List<LocalTime> trainstops = new ArrayList<LocalTime>(service.getAllTrainstops());

    String returnStation = null;

    for(LocalTime t: trainstops) {
      
      if((service.getStationName(t).equals(station)) && !time.isAfter(t)){
        returnStation = service.getStationName(t);
      }
    }

    return returnStation;

  }


   /**
   * Procura se existe itinerarios diretos com a estacao de origem e a estacao de chegada.
   * @param passengerId id do passageiro
   * @param departureStation estacao de partida
   * @param arrivalStation estacao de chegada
   * @param departureDate data do itinerario
   * @param departureTime hora de partida
   * @return todos os itinerarios diretos com as condicoes indicadas.
   */


  public Collection<String> searchDirectItinerary(int passengerId, String departureStation, String arrivalStation, String departureDate,
                                              String departureTime) throws  NoSuchStationNameException {
    

    ArrayList<Itinerary> itineraries = new ArrayList<Itinerary>();
    
    Station departingStationName = _stationsMap.get(departureStation);
    Station arrivalStationName = _stationsMap.get(arrivalStation);


    if (departingStationName == null){
      throw new NoSuchStationNameException(departureStation);

    }

    if(arrivalStationName == null) {
      throw new NoSuchStationNameException(arrivalStation);
    }

    LocalTime departureTrainstop = null;

    LocalTime arrivalTrainstop = null;

    Service service;



    departureTrainstop = LocalTime.parse(departureTime);

    List<Integer> filteredServices = new ArrayList<>(departingStationName.getFilteredServices(departureTrainstop));

    LocalTime[] filteredTrainstops = departingStationName.getFilteredTrainstops(departureTrainstop, filteredServices.size());

    int j=0;



    for (Integer id: filteredServices) {

      service = _serviceMap.get(id);

      arrivalTrainstop = service.getTrainstopAt(arrivalStation, filteredTrainstops[j]);



    

      if (arrivalTrainstop != null) { 


        Passenger p = _passengersMap.get(passengerId);
        int itineraryId = p.getNumItineraries();
        itineraryId++;

        LocalDate date = LocalDate.parse(departureDate);
      
        Itinerary itinerary = new Itinerary(date,itineraryId);

        itinerary.addSegment(departureStation, filteredTrainstops[j], arrivalStation, arrivalTrainstop, service.getId() , service.getCost(), service.getDuration(), 0);

        itineraries.add(itinerary);

        _itineraryList.add(itinerary);

      }

      j++;

    }

    if (itineraries.size() != 0) {
      return getSortedItineraries(itineraries);
    }

    else {
      return null;
    }
  }


  /**
   * Ordena todos os itinerarios por ordem da data de partida, da hora de partida, da hora de chegada e duracao.
   * @param itinerariesList lista de itinerarios a ordenar
   * @return lista de itinerarios ordenados.
   */

  public Collection<String> getSortedItineraries( ArrayList<Itinerary> itinerariesList) {


    List<String> itineraries = new ArrayList<String>();

    int newItineraryId = 1;
    int choice = 1;

    int j;




    Comparator<Itinerary> comparator;

    comparator = new Comparator<Itinerary>() {
      public int compare(Itinerary i1, Itinerary i2) {

        if ((i1.getDate().compareTo(i2.getDate())) == 0 ) {


          if (i1.getDepartingTime().compareTo(i2.getDepartingTime()) == 0){

            return i1.getArrivalTime().compareTo(i2.getArrivalTime()); 
          }

          return i1.getDepartingTime().compareTo(i2.getDepartingTime());

        }
      
        return i1.getDate().compareTo(i2.getDate());
      }
    };



    Collections.sort(itinerariesList, comparator);


   for (Itinerary i: itinerariesList) {

      _itineraryChoiceMap.put(choice++,i);

      itineraries.add(" ");


      itineraries.add(i.toString(newItineraryId++));



      int numSegments = i.getSegmentCont();


      int[] serviceId = new int[numSegments];
      double[] segmentCost = new double[numSegments];
      String[] serviceDepartingStation = new String[numSegments];
      String[] serviceArrivalStation = new String[numSegments];

      serviceId = i.getServiceId();
      serviceDepartingStation = i.getDepartingStation();
      serviceArrivalStation = i.getArrivalStation();
      segmentCost = i.getSegmentCost();


      
      for (j=0; j < numSegments; j++) {



        Service s = _serviceMap.get(serviceId[j]);

        itineraries.add(s.segmentString(segmentCost[j]));

        
        itineraries.addAll(s.getItineraryTrainstops(serviceDepartingStation[j],serviceArrivalStation[j]));
        

      }


    }

    return Collections.unmodifiableCollection(itineraries);
  }


  /**
   * Ordena todos os itinerarios por ordem da data de partida, da hora de partida, da hora de chegada e duracao.
   * @param itinerariesList lista de itinerarios a ordenar
   * @return lista de itinerarios ordenados.
   */

  public Collection<String> getReverseSortedItineraries( ArrayList<Itinerary> itinerariesList) {


    List<String> itineraries = new ArrayList<String>();

    int newItineraryId = 1;
    int choice = 1;

    int j;




    Comparator<Itinerary> comparator;

    comparator = new Comparator<Itinerary>() {
      public int compare(Itinerary i1, Itinerary i2) {

        if ((i1.getDate().compareTo(i2.getDate())) == 0 ) {


          if (i1.getDepartingTime().compareTo(i2.getDepartingTime()) == 0){

            return i1.getArrivalTime().compareTo(i2.getArrivalTime()); 
          }

          return i1.getDepartingTime().compareTo(i2.getDepartingTime());

        }
      
        return i1.getDate().compareTo(i2.getDate());
      }
    };



    Collections.sort(itinerariesList, comparator);


   for (Itinerary i: itinerariesList) {

      _itineraryChoiceMap.put(choice++,i);

      itineraries.add(" ");


      itineraries.add(i.toString(newItineraryId++));



      int numSegments = i.getSegmentCont();


      int[] serviceId = new int[numSegments];
      double[] segmentCost = new double[numSegments];
      String[] serviceDepartingStation = new String[numSegments];
      String[] serviceArrivalStation = new String[numSegments];

      serviceId = i.getServiceId();
      serviceDepartingStation = i.getDepartingStation();
      serviceArrivalStation = i.getArrivalStation();
      segmentCost = i.getSegmentCost();


      
      for (j=numSegments-1; j >= 0; j--) {



        Service s = _serviceMap.get(serviceId[j]);

        itineraries.add(s.segmentString(segmentCost[j]));

        
        itineraries.addAll(s.getItineraryTrainstops(serviceDepartingStation[j],serviceArrivalStation[j]));
        

      }


    }

    return Collections.unmodifiableCollection(itineraries);
  }


  /**
   * Associa a compra de um itinerario ao passageiro.
   * @param passengerId id do passageiro
   * @param itineraryNumber escolha do passageiro de itinerario
   */

  public void commitItinerary(int passengerId, int itineraryNumber) throws NoSuchItineraryChoiceException, NoSuchPassengerIdException{

    

    Itinerary i = _itineraryChoiceMap.get(itineraryNumber);

    if( i == null) {
      throw new NoSuchItineraryChoiceException(passengerId,itineraryNumber);


    }

    Passenger p = _passengersMap.get(passengerId);

    if(p==null) {
      throw new NoSuchPassengerIdException(passengerId);
    }

    p.addItinerary(i.getId(), i);
    i.setTime();
    p.setAccumulatedTime(i.getTime());
    p.setBalance(i.getCost());


  }

  /**
   * Limpa a escolha de um passageiro.
   */

  public void clearChoice(){
    _itineraryChoiceMap.clear();

  }

  /**
   * reinicia o estado da aplicacao.
   */

  public void reset(){
    _passengersMap.clear();
    _itineraryList.clear();
    _nextPassengerId = 0;

  }




}
