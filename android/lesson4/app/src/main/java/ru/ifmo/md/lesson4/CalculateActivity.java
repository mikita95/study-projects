package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculateActivity extends Activity {
    private String expr;
    private CalculationEngine calculationEngine1;
    private boolean error;
    private TextView TextView1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        expr = "";
        calculationEngine1 = CalculationEngineFactory.defaultEngine();
        error = false;
        TextView1 = (TextView) findViewById(R.id.TextView1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        char nameOfKey = (char) event.getUnicodeChar();
        if ((nameOfKey == '0') || (nameOfKey == '1') || (nameOfKey == '2') || (nameOfKey == '3') || (nameOfKey == '4') ||
                (nameOfKey == '5') || (nameOfKey == '6') || (nameOfKey == '7') || (nameOfKey == '8') || (nameOfKey == '9') ||
                (nameOfKey == '+') || (nameOfKey == '-') || (nameOfKey == '*') || (nameOfKey == '/') || (nameOfKey == '.') ||
                (nameOfKey == '(') || (nameOfKey == ')')) {
            expr += nameOfKey;
            TextView1.setText(expr);
            return true;
        } else {
            if (keyCode == KeyEvent.KEYCODE_C) {
                expr = "";
                TextView1.setText("");
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (!expr.isEmpty()) {
                    expr = expr.substring(0, expr.length() - 1);
                    TextView1.setText(expr);
                }
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_EQUALS) {
                try {
                    double result = calculationEngine1.calculate(expr);
                    expr = Double.toString(result);
                    TextView1.setText(expr);
                    error = Double.isInfinite(result) || Double.isNaN(result);
                } catch (CalculationException e) {
                    expr = getString(R.string.errorMessage);
                    TextView1.setText(getString(R.string.errorMessage));
                    error = true;
                }
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public void buttonClick(View v) {
        Button button1 = (Button) v;
        String buttonText = button1.getText().toString();
        if (error) {
            expr = "";
            TextView1.setText("");
            error = false;
        }
        if (button1.getId() == R.id.equals) {
            try {
                double result = calculationEngine1.calculate(expr);
                expr = Double.toString(result);
                TextView1.setText(expr);
                error = Double.isInfinite(result) || Double.isNaN(result);
            } catch (CalculationException e) {
                expr = getString(R.string.errorMessage);
                TextView1.setText(getString(R.string.errorMessage));
                error = true;
            }
        } else if (button1.getId() == R.id.clear) {
            expr = "";
            TextView1.setText("");
        } else if (button1.getId() == R.id.delete) {
            if (!expr.isEmpty()) {
                expr = expr.substring(0, expr.length() - 1);
                TextView1.setText(expr);
            }
        } else {
            expr += buttonText;
            TextView1.setText(expr);
        }

    }

}
