package mmt.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalTime;
import java.time.LocalDate;

import mmt.core.exceptions.ImportFileException;

public class NewParser {

  private TrainCompany _trainCompany;

  public TrainCompany parseFile(String fileName) throws ImportFileException {

    _trainCompany = new TrainCompany();

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

      String line;

      while ((line = reader.readLine()) != null) {
          parseLine(line);
      }

    } catch (IOException ioe) {
      throw new ImportFileException(ioe);
    }

    return _trainCompany;
  }

  private void parseLine(String line) throws ImportFileException {
    String[] components = line.split("\\|");

    switch (components[0]) {
      case "PASSENGER":
        parsePassenger(components);
        break;

      case "SERVICE":
        parseService(components);
        break;

      case "ITINERARY":
        parseItinerary(components);
        break;

      default:
       throw new ImportFileException("invalid type of line: " + components[0]);
    }
  }

  private void parsePassenger(String[] components) throws ImportFileException {

    if (components.length != 2)
      throw new ImportFileException("invalid number of arguments in passenger line: " + components.length);

    String passengerName = components[1];

    _trainCompany.registerPassenger(passengerName);

  }



  private void parseService(String[] components) {
    double cost = Double.parseDouble(components[2]);
    int serviceId = Integer.parseInt(components[1]);

    String[] station = new String[(components.length-3)/2];
    LocalTime[] trainstop = new LocalTime[(components.length-3)/2];

 
    int j=0;
    for (int i = 3; i < components.length; i += 2) {
      String time = components[i];
      String stationName = components[i + 1];
      LocalTime ltime = LocalTime.parse(time);

      station[j] = stationName;                      
      trainstop[j] = ltime;
      j++;

    }  
  
    _trainCompany.registerStation(serviceId, cost, station, trainstop);
    
    _trainCompany.registerService(serviceId, cost, station, trainstop);

    
  }


  private void parseItinerary(String[] components) throws ImportFileException {

    if (components.length < 4)
      throw new ImportFileException("Invalid number of elements in itinerary line: " + components.length);

    int passengerId = Integer.parseInt(components[1]);
    LocalDate date = LocalDate.parse(components[2]);

    int[] itineraryServiceId = new int[components.length-3];
    String[] itineraryDepartingStation = new String[components.length-3];
    String[] itineraryArrivingStation = new String[components.length-3];

    int j=0;
    for (int i = 3; i < components.length; i++) {
      String segmentDescription[] = components[i].split("/");

      int serviceId = Integer.parseInt(segmentDescription[0]);
      String departureTrainStop = segmentDescription[1];
      String arrivalTrainStop = segmentDescription[2];

      itineraryServiceId[j] = serviceId;                      
      itineraryArrivingStation[j] = arrivalTrainStop;
      itineraryDepartingStation[j] = departureTrainStop;
      j++;

    }

    _trainCompany.registerItinerary(passengerId,date,itineraryServiceId,itineraryDepartingStation,itineraryArrivingStation);
  }
}