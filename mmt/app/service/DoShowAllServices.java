package mmt.app.service;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;



/**
 * 3.2.1 Show all services.
 */
public class DoShowAllServices extends Command<TicketOffice> {

  /**
   * @param receiver
   */
  public DoShowAllServices(TicketOffice receiver) {
    super(Label.SHOW_ALL_SERVICES, receiver);
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {

    ArrayList<String> service = new ArrayList<>(_receiver.getServices());


    for (String s: service) {
      _display.addLine(s);
    }

  
    _display.display();
    
    
  }

}
