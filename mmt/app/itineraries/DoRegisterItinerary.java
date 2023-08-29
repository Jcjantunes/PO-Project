package mmt.app.itineraries;

import mmt.core.TicketOffice;
import mmt.app.exceptions.BadDateException;
import mmt.app.exceptions.BadTimeException;
import mmt.app.exceptions.NoSuchItineraryException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.app.exceptions.NoSuchStationException;
import mmt.core.exceptions.BadDateSpecificationException;
import mmt.core.exceptions.BadTimeSpecificationException;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.core.exceptions.NoSuchItineraryChoiceException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;
 

import mmt.app.itineraries.Message;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;



/**
 * §3.4.3. Add new itinerary.
 */
public class DoRegisterItinerary extends Command<TicketOffice> {

  
  private Input<Integer> _passengerId;
  private Input<String> _departureStation;
  private Input<String> _arrivalStation;
  private Input<String> _departureDate;
  private Input<String> _departureTime;


  private Message _m;

  /**
   * @param receiver
   */
  public DoRegisterItinerary(TicketOffice receiver) {
    super(Label.REGISTER_ITINERARY, receiver);

      _passengerId = _form.addIntegerInput(_m.requestPassengerId());
      _departureStation = _form.addStringInput(_m.requestDepartureStationName());
      _arrivalStation = _form.addStringInput(_m.requestArrivalStationName());
      _departureDate = _form.addStringInput(_m.requestDepartureDate());
      _departureTime = _form.addStringInput(_m.requestDepartureTime());


  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    _form.parse();


    try {


      if(_receiver.searchItineraries( _passengerId.value(), _departureStation.value(), _arrivalStation.value(), _departureDate.value(),
                                              _departureTime.value()) != null) {


        ArrayList<String> itineraries = new ArrayList<>(_receiver.searchItineraries( _passengerId.value(), _departureStation.value(), _arrivalStation.value(), _departureDate.value(),
                                              _departureTime.value()));

        for (String i: itineraries) {
          _display.addLine(i);
        }

        _display.display();


        Form form = new Form("selectItinerary");
        Display display = new Display();

        Input<Integer> choice = form.addIntegerInput(_m.requestItineraryChoice());

        form.parse();



        if(choice.value()!= 0) {
          _receiver.commitItinerary(_passengerId.value(), choice.value());
        }

        _receiver.clearChoice();



      }



      //FIXME implement command
      // must call (at least) _receiver.searchItineraries(...) and _receiver.commitItinerary(...)
    } catch (NoSuchPassengerIdException e) {
        throw new NoSuchPassengerException(e.getId());
    } catch (NoSuchStationNameException e) {
        throw new NoSuchStationException(e.getName());
    } catch (NoSuchItineraryChoiceException e) {
        throw new NoSuchItineraryException(e.getPassengerId(), e.getItineraryId());
    } catch (BadDateSpecificationException e) {
        throw new BadDateException(e.getDate());
    } catch (BadTimeSpecificationException e) {
        throw new BadTimeException(e.getTime());
    }
  }
}
