package com.example.lenovo.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Calculate extends AppCompatActivity {

    private int[] numericButtons = {R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine};
    private int[] operatorButtons = {R.id.add, R.id.sub, R.id.mul, R.id.divide,R.id.percent,R.id.exponent};
    public TextView txtScreen;
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calculate);
            this.txtScreen = (TextView) findViewById(R.id.tv1);
            setNumericOnClickListener();
            setOperatorOnClickListener();
        }

        private void setNumericOnClickListener() {
            View.OnClickListener number = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    if (stateError) {
                        txtScreen.setText(button.getText());
                        stateError = false;
                    } else {
                        txtScreen.append(button.getText());

                    }
                    lastNumeric = true;
                }
            };
            for (int id : numericButtons) {
                findViewById(id).setOnClickListener(number);
            }
        }
            private void setOperatorOnClickListener() {
            View.OnClickListener op = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b=(Button)v;
                    if (lastNumeric && !stateError) {
                        txtScreen.append(b.getText());
                        }
                        lastNumeric = false;
                        lastDot = false;
                    }
                };
            for (int id : operatorButtons) {
                findViewById(id).setOnClickListener(op);
            }
                findViewById(R.id.sqrt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button b=(Button) v;
                        if (txtScreen.equals("") || !lastNumeric) {
                            txtScreen.append(b.getText());
                            lastNumeric = false;
                            lastDot = false;
                        }
                    }
                });

                findViewById(R.id.dot).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastNumeric && !stateError && !lastDot) {
                        txtScreen.append(".");
                        lastNumeric = false;
                        lastDot = true;
                    }
                }
            });

            findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtScreen.setText("");
                    lastNumeric = false;
                    stateError = false;
                    lastDot = false;
                }
            });
            findViewById(R.id.equal).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEqual();
                }
            });
        }

        private void onEqual() {
            if (lastNumeric && !stateError) {
                String txt = txtScreen.getText().toString();
                Expression expression = new ExpressionBuilder(txt).build();
                try {
                    double result = expression.evaluate();
                    txtScreen.setText(Double.toString(result));
                    lastDot = true; // Result contains a dot
                } catch (ArithmeticException ex) {
                    txtScreen.setText("Error");
                    stateError = true;
                    lastNumeric = false;
                }
            }
        }
    }


