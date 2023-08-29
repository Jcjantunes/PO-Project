package mmt.app.main;

import mmt.core.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

import java.io.FileNotFoundException;
import java.io.IOException;

import mmt.core.TicketOffice;
import mmt.app.main.Message;


/**
 * §3.1.1. Open existing document.
 */
public class DoOpen extends Command<TicketOffice> {


  private Message _m;

  private Input<String>  _filename;

  /**
   * @param receiver
   */
  public DoOpen(TicketOffice receiver) {
    super(Label.OPEN, receiver);


    _filename = _form.addStringInput(_m.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {

    _form.parse();

    try {
      _receiver.load(_filename.value());
    } catch (FileNotFoundException fnfe) {
        _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
        e.printStackTrace();
    }

    _receiver.saveFile(_filename.value());
    
  }

}
