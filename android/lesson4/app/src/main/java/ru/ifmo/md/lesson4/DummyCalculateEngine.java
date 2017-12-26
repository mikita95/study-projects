package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {

    @Override
    public double calculate(String s) throws CalculationException {
        try {
            MyDouble tmp = new MyDouble();
            return ExpressionParser.parse(s, new MyDouble()).evaluate(tmp.getValue(0), tmp.getValue(0), tmp.getValue(0)).getDouble();

        } catch (Throwable e) {
            throw new CalculationException();
        }
    }


}
