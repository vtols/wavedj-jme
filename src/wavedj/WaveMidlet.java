package wavedj;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.Display;

public class WaveMidlet extends MIDlet {
    
    public static Display disp;

    public void startApp() {
        disp = Display.getDisplay(this);
        disp.setCurrent(new WaveForm());
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
