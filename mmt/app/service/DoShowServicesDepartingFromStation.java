package mmt.app.service;

import mmt.core.TicketOffice;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.app.exceptions.NoSuchStationException;
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
 * 3.2.3 Show services departing from station.
 */
public class DoShowServicesDepartingFromStation extends Command<TicketOffice> {


  private Message _m;

  private Input<String>  _departingStation;

  /**
   * @param receiver
   */
  public DoShowServicesDepartingFromStation(TicketOffice receiver) {
    super(Label.SHOW_SERVICES_DEPARTING_FROM_STATION, receiver);

    _departingStation = _form.addStringInput(_m.requestStationName());

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {


    _form.parse();

    try{

    ArrayList<String> services = new ArrayList<>(_receiver.showServicesbyDepartingStation(_departingStation.value()));

    for (String s: services) {
      _display.addLine(s);
    }

    _display.display();

    } catch (NoSuchStationNameException e) {
        throw new NoSuchStationException(_departingStation.value());
    } catch (Exception e){
        _display.addLine(""+ e.getClass().getName());
        _display.display();
    } 


  }

}
