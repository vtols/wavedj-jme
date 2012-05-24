package function;

public class Trigonometric extends Function {
    
    public static final int
            SIN = 0,
            COS = 1,
            TAN = 2;
    
    int fType;
    Function fArg;
    
    Trigonometric(int type, Function arg) {
        fType = type;
        fArg = arg;
    }

    public double eval(double arg) {
        switch (fType) {
            case SIN:
                return Math.sin(fArg.eval(arg));
            case COS:
                return Math.cos(fArg.eval(arg));
            case TAN:
                return Math.tan(fArg.eval(arg));
            default:
                return 0.0;
        }
    }
    
}
