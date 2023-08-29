package mmt.app.passenger;

import mmt.core.TicketOffice;
import mmt.app.exceptions.BadPassengerNameException;
import mmt.app.exceptions.DuplicatePassengerNameException;
import mmt.core.exceptions.InvalidPassengerNameException;
import mmt.core.exceptions.NonUniquePassengerNameException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Form;              



import mmt.core.TicketOffice;
import mmt.app.passenger.Message;


/**
 * §3.3.3. Register passenger.
 */
public class DoRegisterPassenger extends Command<TicketOffice> {

  private Input<String>  _name;

  private Message _m;

  /**
   * @param receiver
   */
  public DoRegisterPassenger(TicketOffice receiver) {
    super(Label.REGISTER_PASSENGER, receiver);


      _name = _form.addStringInput(_m.requestPassengerName());
    
  }


  /*@see pt.tecnico.po.ui.Command#execute() */
  @Override

  public final void execute() throws DialogException {

    _form.parse();

    _receiver.registerPassenger(_name.value());

  }

}
