package mmt.app.main;

import java.io.IOException;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;


import mmt.core.TicketOffice;
import mmt.app.main.Message;



/**
 * §3.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<TicketOffice> {

  private Message _m;

  private Input<String>  _filename;


  /**
   * @param receiver
   */
  public DoSave(TicketOffice receiver) {
    super(Label.SAVE, receiver);

  
    _filename = _form.addStringInput(_m.newSaveAs());

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {


    if (_receiver.getFileName()==null) {
      _form.parse();
  
    }

    try{
    _receiver.save(_filename.value());

    
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}
