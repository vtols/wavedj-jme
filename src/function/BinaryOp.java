package function;

public class BinaryOp extends Function {
    
    public static final int
            ADD = 0,
            SUB = 1,
            MUL = 2,
            DIV = 3;
    
    Function left, right;
    int fType;
    
    BinaryOp(int type, Function l, Function r) {
        fType = type;
        left = l;
        right = r;
    }

    public double eval(double arg) {
        switch (fType) {
            case ADD:
                return left.eval(arg) + right.eval(arg);
            case SUB:
                return left.eval(arg) - right.eval(arg);
            case MUL:
                return left.eval(arg) * right.eval(arg);
            case DIV:
                return left.eval(arg) / right.eval(arg);
            default:
                return 0;
        }
    }
    
}
