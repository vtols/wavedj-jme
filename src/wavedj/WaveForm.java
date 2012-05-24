package wavedj;

import function.ParserException;
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;

public class WaveForm extends Form implements CommandListener {
    
    private static final TextField funcField =
            new TextField("Function", "", 1000, TextField.ANY);
    private static final Command play =
            new Command("Play", Command.SCREEN, 0);
    
    public WaveForm() {
        super("WaveDJ");
        append(funcField);
        this.addCommand(play);
        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == play) {
            try {
                StreamGenerator sg = new StreamGenerator(funcField.getString());
                sg.generate();
                Player p = Manager.createPlayer(sg, "audio/x-wav"); 
                p.start();
            } catch (IOException ex) {
                Alert alert = new Alert("Error playing sound",
                                         ex.getMessage(),
                                         null, AlertType.WARNING);
                alert.setTimeout(2000);
                WaveMidlet.disp.setCurrent(alert, this);
            } catch (MediaException ex) {
                Alert alert = new Alert("Error playing sound",
                                         ex.getMessage(),
                                         null, AlertType.WARNING);
                alert.setTimeout(2000);
                WaveMidlet.disp.setCurrent(alert, this);
            } catch (ParserException ex) {
                Alert alert = new Alert("Parse error",
                                         ex.getMessage(),
                                         null, AlertType.WARNING);
                alert.setTimeout(2000);
                WaveMidlet.disp.setCurrent(alert, this);
            }
        }
    }
    
}
