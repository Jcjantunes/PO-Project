package mmt.app.service;

import mmt.core.TicketOffice;
import mmt.core.exceptions.NoSuchServiceIdException;
import mmt.app.exceptions.NoSuchServiceException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;

import mmt.core.TicketOffice;
import mmt.app.service.Message;


import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;





/**
 * 3.2.2 Show service by number.
 */
public class DoShowServiceByNumber extends Command<TicketOffice> {


  private Message _m;

  private Input<Integer>  _id;

  /**
   * @param receiver
   */

  public DoShowServiceByNumber(TicketOffice receiver) {
    super(Label.SHOW_SERVICE_BY_NUMBER, receiver);

    _id = _form.addIntegerInput(_m.requestServiceId());

  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();


    try {

      ArrayList<String> services = new ArrayList<>(_receiver.showServicebyNumber(_id.value()));

      for (String s: services) {
        _display.addLine(s);
      }

      _display.display();      

    }catch (NoSuchServiceIdException e) {
      throw new NoSuchServiceException(_id.value());
    } catch (Exception e){
        _display.addLine(""+ e.getClass().getName());
        _display.display();
    }
    

  }

}
