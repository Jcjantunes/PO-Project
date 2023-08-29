package mmt.app.itineraries;

import mmt.core.TicketOffice;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.app.exceptions.NoSuchPassengerException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;

import mmt.app.itineraries.Message;


import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;

import java.util.*;


/**
 * §3.4.2. Show all itineraries (for a specific passenger).
 */
public class DoShowPassengerItineraries extends Command<TicketOffice> {

  
  private Message _m;

  private Input<Integer>  _id;

  /**
   * @param receiver
   */
  public DoShowPassengerItineraries(TicketOffice receiver) {
    super(Label.SHOW_PASSENGER_ITINERARIES, receiver);


    _id = _form.addIntegerInput(_m.requestPassengerId());

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();

    try{


      if(_receiver.showItinerariesbyPassenger(_id.value()) == null) {
        _display.addLine(Message.noItineraries(_id.value()));

        _display.display();


      }

      else {
        ArrayList<String> itineraries = new ArrayList<>(_receiver.showItinerariesbyPassenger(_id.value()));

        for (String i: itineraries) {
          _display.addLine(i);
        }

        _display.display();
      }

    } catch (NoSuchPassengerIdException e) {
        throw new NoSuchPassengerException(_id.value());
    } catch (Exception e){
        _display.addLine(""+ e.getClass().getName());
        _display.display();
    }
  }

}
