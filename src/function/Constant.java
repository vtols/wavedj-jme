package function;

public class Constant extends Function {
    
    double constVal;
    
    Constant(double val) {
        constVal = val;
    }

    public double eval(double arg) {
        return constVal;
    }
    
}
