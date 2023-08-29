package mmt.app.passenger;

import mmt.core.TicketOffice;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.app.exceptions.NoSuchPassengerException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;

import mmt.core.TicketOffice;
import mmt.app.passenger.Message;



/**
 * §3.3.2. Show specific passenger.
 */
public class DoShowPassengerById extends Command<TicketOffice> {


  private Message _m;

  private Input<Integer>  _id;



  /**
   * @param receiver
   */
  public DoShowPassengerById(TicketOffice receiver) {
    super(Label.SHOW_PASSENGER_BY_ID, receiver);

    _id = _form.addIntegerInput(_m.requestPassengerId());

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    

    try{

    _form.parse();


    _display.addLine(_receiver.showPassengerbyId(_id.value()));
    _display.display();

    } catch (NoSuchPassengerIdException e) {
        throw new NoSuchPassengerException(_id.value());
    } catch (Exception e){
        _display.addLine(""+ e.getClass().getName());
        _display.display();
    }


  }

}
