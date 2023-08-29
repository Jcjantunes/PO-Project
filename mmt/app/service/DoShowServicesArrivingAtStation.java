package mmt.app.service;

import mmt.core.TicketOffice;
import mmt.core.exceptions.NoSuchStationNameException;
import mmt.app.exceptions.NoSuchStationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;


import java.util.List;
import java.util.ArrayList;
import java.util.Collection;                                    
import java.util.Collections;

/**
 * 3.2.4 Show services arriving at station.
 */
public class DoShowServicesArrivingAtStation extends Command<TicketOffice> {

  private Message _m;

  private Input<String>  _arrivingStation;

  /**
   * @param receiver
   */
  public DoShowServicesArrivingAtStation(TicketOffice receiver) {
    super(Label.SHOW_SERVICES_ARRIVING_AT_STATION, receiver);
    _arrivingStation = _form.addStringInput(_m.requestStationName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    _form.parse();

    try{

    ArrayList<String> services = new ArrayList<>(_receiver.showServicesbyArrivingStation(_arrivingStation.value()));

    for (String s: services) {
      _display.addLine(s);
    }

    _display.display();

    } catch (NoSuchStationNameException e) {
        throw new NoSuchStationException(_arrivingStation.value());
    } catch (Exception e){
        _display.addLine(""+ e.getClass().getName());
        _display.display();
    } 
  }

}
