package function;

public class FunctionParser {
    
    char[] e;
    int i, len;
    
    public FunctionParser(String sexpr) {
        e = sexpr.toCharArray();
        len = e.length;
    }
    
    public Function parse() throws ParserException {
        i = 0;
        Function f = parseExpression();
        if (inExp() || f == null)
            throw new ParserException("Incorrect expression");
        return f;
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
    
    private Function parseExpression() throws ParserException {
        Function f;
        skipSp();
        if (inExp() && e[i] == '-') {
            i++;
            f = new UnaryOp(UnaryOp.MINUS, parseTerm());
        } else if (inExp() && e[i] == '+')
            i++;
        f = parseTerm();
        while (inExp() && (e[i] == '+' || e[i] == '-')) {
            int op = (e[i++] == '+' ? BinaryOp.ADD : BinaryOp.SUB);
            f = new BinaryOp(op, f, parseTerm());
        }
        skipSp();
        return f;
    }

    private Function parseTerm() throws ParserException {
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

    private Function parseFactor() throws ParserException {
        skipSp();
        Function f;
        if (inExp() && isAlpha(e[i])) {
            String fName = "";
            while (inExp() && isAlpha(e[i]))
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
                throw new ParserException("Unknown function or constant " +
                                           fName);
        } else
            f = parseAtom();
        skipSp();
        return f;
    }

    private Function parseAtom() throws ParserException {
        Function f = null;
        skipSp();
        if (!inExp())
            throw new ParserException("Incorrect expression");
        if (e[i] == '(') {
            i++;
            f = parseExpression();
            if (!inExp() || e[i] != ')')
                throw new ParserException("Expected ')'");
            i++;
        } else if (isDigit(e[i])) {
            double val = 0.0;
            while (inExp() && isDigit(e[i]))
                val = val * 10.0 + toDigit(e[i++]);
            if (inExp() && e[i] == '.') {
                i++;
                double d = 0.1;
                while (inExp() && isDigit(e[i])) {
                    val += toDigit(e[i++]) * d;
                    d /= 10.0;
                }
            }
            f = new Constant(val);
        }
        return f;
    }
    
}
