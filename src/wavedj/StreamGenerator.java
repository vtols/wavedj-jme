package wavedj;

import function.*;
import java.io.*;

public class StreamGenerator extends InputStream {
    
    int wP = 0, rP = 0, ds = 44 + 8000;
    byte[] data = new byte[ds];
    FunctionParser parser;
    Function func;

    StreamGenerator(String strFunc) {
        parser = new FunctionParser(strFunc);
    }
    
    void generate() throws ParserException {
        func = parser.parse();
        genHeader();
        genSound();
    }
    
    void genHeader() {
        add("RIFF");
        addInt(36 + 8000);
        add("WAVE");
        
        add("fmt ");
        addInt(16);
        addShort((short) 1);
        addShort((short) 1);
        addInt(8000);
        addInt(8000);
        addShort((short) 1);
        addShort((short) 8);
        
        add("data");
        addInt(8000);
    }
    
    void genSound() {
        float t = 0.0f, dt = 1.0f / 8000;
        for (int i = 0; i < 8000; i++, t += dt) {
            data[wP++] = (byte)(func.eval(t) * 127 + 127);
        }
    }
    
    void add(String s) {
        for (int i = 0; i < s.length(); i++)
            addByte((byte) s.charAt(i));
    }
    
    void addByte(byte x) {
        data[wP++] = x;
    }
    
    void addShort(short x) {
        for (int j = 0; j < 16; j += 8)
            data[wP++] = (byte)(x >> j);
    }
    
    void addInt(int x) {
        for (int j = 0; j < 32; j += 8)
            data[wP++] = (byte)(x >> j);
    }

    public int read() {
        if (rP == ds)
            return -1;
        return data[rP++];
    }
    
}
