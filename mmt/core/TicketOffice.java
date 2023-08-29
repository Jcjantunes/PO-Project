package mmt.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mmt.core.exceptions.BadDateSpecificationException;
import mmt.core.exceptions.BadTimeSpecificationException;
import mmt.core.exceptions.ImportFileException;
import mmt.core.exceptions.MissingFileAssociationException;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.core.exceptions.NoSuchServiceIdException;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.core.exceptions.NoSuchItineraryChoiceException;
import mmt.core.exceptions.NonUniquePassengerNameException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.app.exceptions.NoSuchServiceException;
import mmt.app.exceptions.NoSuchStationException;



import java.util.Collection;                                    
import java.util.Collections;

import java.io.File;




/**
 * Façade for handling persistence and other functions.
 */
public class TicketOffice {

  /** The object doing most of the actual work. */

  

  private TrainCompany _trainCompany;


  private String _filename;



  /**
   * verifica se _trainCompany esta instanciado.
   */

  public void trainCompanyExists() {
    if (_trainCompany == null)
      _trainCompany = new TrainCompany();
  }

  /**
   * obtem o nome do ficheiro _filename.
   */

  public String getFileName(){
    return _filename;   
  }

  /**
   * guarda o nome do ficheiro assosciado.
   * 
   * @param filename nome do ficheiro a guardar.
   */

  public void saveFile( String filename) {
    _filename = filename;
  }

  /**
   * apaga toda a informacao relativa a passageiros e itinerarios.
   */

  public void reset() {
    trainCompanyExists();
    _trainCompany.reset();
    
  }

  /**
   * guarda o estado da trainCompany.
   * 
   * @param filename nome do ficheiro a guardar.
   */

  public void save(String filename) throws IOException {  


    if (getFileName()==null) {
      _filename = filename;      
 
    }

    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename));
    out.writeObject(_trainCompany);
    out.close();
    
  }

  /**
   * abre um ficheiro ja criado.
   * 
   * @param filename nome do ficheiro a abrir.
   */


  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {

      

    ObjectInputStream inob = new ObjectInputStream(new FileInputStream(filename));
    _trainCompany = (TrainCompany)inob.readObject();
    inob.close();

  }

  /**
   * carrega a informacao de um ficheiro import.
   * 
   * @param datafile nome do ficheiro de import.
   */

  public void importFile(String datafile) throws ImportFileException {

    NewParser parser = new NewParser();
    _trainCompany = parser.parseFile(datafile);


  }

  //FIXME complete and implement the itinerary search (and pre-commit store) method
  public Collection<String> searchItineraries(int passengerId, String departureStation, String arrivalStation, String departureDate,
                                              String departureTime) throws NoSuchStationNameException ,NoSuchPassengerIdException, BadDateSpecificationException, BadTimeSpecificationException { 
    trainCompanyExists();
    return _trainCompany.searchItineraries(passengerId, departureStation, arrivalStation, departureDate, departureTime);
  }

  //FIXME complete and implement the itinerary commit method
  public void commitItinerary(int passengerId, int itineraryNumber) throws NoSuchPassengerIdException, NoSuchItineraryChoiceException { ///*FIXME define thrown exceptions */ {
    trainCompanyExists();
    _trainCompany.commitItinerary(passengerId, itineraryNumber);
  }

 

  public void registerPassenger(String name){

    trainCompanyExists();
    
    _trainCompany.registerPassenger(name);

  }



  public void changePassengerName(int id,String name) throws NoSuchPassengerIdException{

    trainCompanyExists();

    _trainCompany.changePassengerName(id,name);

  }



  public String showPassengerbyId(int id) throws NoSuchPassengerIdException{

    trainCompanyExists();

    return _trainCompany.showPassengerbyId(id);

  }



  public Collection<String> getPassengers() {

    trainCompanyExists();

    return _trainCompany.getPassengers();
  }





  public Collection<String> getServices() {

    trainCompanyExists();

    return _trainCompany.getServices();

  }



  public Collection<String> showServicebyNumber(int id) throws NoSuchServiceIdException {

    trainCompanyExists();

    return _trainCompany.showServicebyNumber(id);

  }




  public Collection<String> showServicesbyDepartingStation(String departingStation) throws NoSuchStationNameException {

    trainCompanyExists();

    return _trainCompany.showServicesbyDepartingStation(departingStation);
  }

  
  public Collection<String> showServicesbyArrivingStation(String arrivingStation) throws NoSuchStationNameException {

    trainCompanyExists();

    return _trainCompany.showServicesbyArrivingStation(arrivingStation);
  }


  public Collection<String> showItinerariesbyPassenger(int id) throws NoSuchPassengerIdException{ //depois adicionar excecao

    trainCompanyExists();

    return _trainCompany.showItinerariesbyPassenger(id);

  }


  public Collection<String> showAllItineraries() {

    trainCompanyExists();

    return _trainCompany.showAllItineraries();

  }


  public void clearChoice() {
    _trainCompany.clearChoice();
  }

}
