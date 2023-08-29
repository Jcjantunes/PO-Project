package mmt.app.passenger;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;


import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;




/**
 * §3.3.1. Show all passengers.
 */
public class DoShowAllPassengers extends Command<TicketOffice> {

  /**
   * @param receiver
   */
  public DoShowAllPassengers(TicketOffice receiver) {
    super(Label.SHOW_ALL_PASSENGERS, receiver);
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {

    ArrayList<String> passengers = new ArrayList<>(_receiver.getPassengers());



    for (String p: passengers) {
      _display.addLine(p);
    }

  
    _display.display();
    

  }

}
