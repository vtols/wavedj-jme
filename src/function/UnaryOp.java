package function;

public class UnaryOp extends Function {
    
    public static final int
            MINUS = 0,
            PLUS  = 1;
    
    int fType;
    Function fArg;
    
    UnaryOp(int type, Function arg) {
        fType = type;
        fArg = arg;
    }

    public double eval(double arg) {
        switch (fType) {
            case 0:
                return -fArg.eval(arg);
            case 1:
                return  fArg.eval(arg);
            default:
                return 0.0;
        }
    }
    
}
