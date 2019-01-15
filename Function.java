
import java.awt.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;



class Function implements Serializable {

    String functionName = "";
    String functionParameter = "";
    String Function = "";
    Color functionColor = new Color(0,0,0);
    double start  = -1000000000;
    double end = 1000000000;
    boolean visibility = true;
    Function(){

    }
    void setName(String name){
        functionName = name;
    }
    void setParameterName(String name){
        functionParameter = name;
    }
    void setFunction(String function){
        Function = function;
    }
    void setColor(Color color){
        functionColor = color;
    }
    void setBounds(double st, double en){
        start = st;
        end = en;
    }
    double getResult(double Parameter){
        double result = 0;
        String infix = Function.replaceAll(functionParameter,'('+String.format("%.16f",Parameter)+')').replaceAll("pi","3.1415926536").replaceAll("e","2.7182818285");
        infix = infix.replaceAll(",",".");
        System.out.println(infix);
        result = eval(infix);
        return result;
    }

    static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    ArrayList<Function> f = new ArrayList<>();
                    f = ProjectDisplay.openedProjects.get(ProjectDisplay.currentProjectIndex).getFunctions();
                    for (Function aF : f) {
                        if (func.equals(aF.functionName)) {
                            x = aF.getResult(x);
                        }
                    }
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(x);
                            break;
                        case "cos":
                            x = Math.cos(x);
                            break;
                        case "tan":
                            x = Math.tan(x);
                            break;
                        case "ctan":
                            x = 1 / Math.tan(x);
                            break;
                        case "abs":
                            x = Math.abs(x);
                            break;
                        case "lg":
                            x = Math.log10(x);
                            break;
                        case "ln":
                            x = Math.log(x);
                            break;
                        case "asin":
                            x = Math.asin(x);
                            break;
                        case "acos":
                            x = Math.acos(x);
                            break;
                        case "atan":
                            x = Math.atan(x);
                            break;
                    }
                    //else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) {
                    double y = parseFactor();// exponentiation
                     x = Math.pow(x, y);
                }

                return x;
            }
        }.parse();
    }

}
