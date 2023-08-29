package mmt.app.passenger;

import mmt.core.TicketOffice;
import mmt.app.exceptions.BadPassengerNameException;
import mmt.app.exceptions.DuplicatePassengerNameException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.core.exceptions.InvalidPassengerNameException;
import mmt.core.exceptions.NoSuchPassengerIdException;
import mmt.core.exceptions.NonUniquePassengerNameException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;              

            
import mmt.core.TicketOffice;         
import mmt.app.passenger.Message;


/**
 * §3.3.4. Change passenger name.
 */
public class DoChangerPassengerName extends Command<TicketOffice> {


  private Input<Integer>   _id;
  private Input<String>  _name;

  private Message _m;





  /**
   * @param receiver
   */
  public DoChangerPassengerName(TicketOffice receiver) {
    super(Label.CHANGE_PASSENGER_NAME, receiver);

    _id   = _form.addIntegerInput(_m.requestPassengerId());
    _name = _form.addStringInput(_m.requestPassengerName());

  }

  /*@see pt.tecnico.po.ui.Command#execute() */
  @Override


  public final void execute() throws DialogException {

    _form.parse();

    try{

    _receiver.changePassengerName(_id.value(),_name.value());

    } catch (NoSuchPassengerIdException e) {
        throw new NoSuchPassengerException(_id.value());
    } catch (Exception e){
        _display.addLine(""+ e.getClass().getName());
        _display.display();
    }

  }
}
