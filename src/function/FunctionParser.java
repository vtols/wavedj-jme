package function;

public class FunctionParser {
    
    char[] e;
    int i, len;
    
    public FunctionParser(String sexpr) {
        e = sexpr.toCharArray();
        len = e.length;
    }
    
    public Function parse() {
        i = 0;
        return parseExpression();
    }
    
    private boolean inExp() {
        return i < len;
    }

    private void skipSp() {
        while (inExp() && e[i] == ' ')
            i++;
    }
    
    private boolean isAlpha(char c) {
        return ((c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z'));
    }
    
    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }
    
    private int toDigit(char c) {
        return Character.digit(c, 10);
    }
    
    private Function parseExpression() {
        Function f;
        skipSp();
        if (e[i] == '-') {
            i++;
            f = new UnaryOp(UnaryOp.MINUS, parseTerm());
        } else if (e[i] == '+')
            i++;
        f = parseTerm();
        while (inExp() && (e[i] == '+' || e[i] == '-')) {
            int op = (e[i++] == '+' ? BinaryOp.ADD : BinaryOp.SUB);
            f = new BinaryOp(op, f, parseTerm());
        }
        skipSp();
        return f;
    }

    private Function parseTerm() {
        Function f;
        skipSp();
        f = parseFactor();
        while (inExp() && (e[i] == '*' || e[i] == '/')) {
            int op = (e[i++] == '*' ? BinaryOp.MUL : BinaryOp.DIV);
            f = new BinaryOp(op, f, parseFactor());
        }
        skipSp();
        return f;
    }

    private Function parseFactor() {
        skipSp();
        Function f;
        if (isAlpha(e[i])) {
            String fName = "";
            while (isAlpha(e[i]) && inExp())
                fName += e[i++];
            fName = fName.toLowerCase();
            if (fName.equals("sin"))
                f = new Trigonometric(Trigonometric.SIN, parseAtom());
            else if (fName.equals("cos"))
                f = new Trigonometric(Trigonometric.COS, parseAtom());
            else if (fName.equals("tan"))
                f = new Trigonometric(Trigonometric.TAN, parseAtom());
            else if (fName.equals("pi"))
                f = new Constant(Math.PI);
            else if (fName.equals("t"))
                f = new Argument();
            else
                f = null;
        } else
            f = parseAtom();
        skipSp();
        return f;
    }

    private Function parseAtom() {
        Function f = null;
        skipSp();
        if (e[i] == '(') {
            i++;
            f = parseExpression();
            i++;
        } else if (isDigit(e[i])) {
            double val = 0.0;
            while (isDigit(e[i]) && inExp())
                val = val * 10.0 + toDigit(e[i++]);
            if (e[i] == '.') {
                i++;
                double d = 0.1;
                while (isDigit(e[i]) && inExp()) {
                    val += toDigit(e[i++]) * d;
                    d /= 10.0;
                }
            }
            f = new Constant(val);
        }
        return f;
    }
    
}
