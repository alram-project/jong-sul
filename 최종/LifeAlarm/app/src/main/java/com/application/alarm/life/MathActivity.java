package com.application.alarm.life;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;

public class MathActivity extends AppCompatActivity {

    //declare views
    private Button generate;
    private Button check;
    private TextView problem;
    private TextView feedback;
    private TextView modeIndicator;
    private RadioGroup operation;
    public RadioButton op;
    private RadioButton add;
    private RadioButton subtract;
    private RadioButton multiply;
    private RadioButton divide;
    private EditText blank;
    private Button offSwitch;

    private char operator;
    private final char addition = '+';
    private final char subtraction = '-';
    private final char multiplication = '*';
    private final char division = '/';

    double le = Double.NaN;
    double ri = Double.NaN;
    int incorrectTries = 0;
    int badRuns = 0;
    int correctTries = 0;
    int result = 0;
    int mode = 0; ////////////////////////////////////////////////////////////
    String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
        viewSetup();
        Intent intent = getIntent();
        mode = intent.getExtras().getInt("mode");
        modeSwitcher(mode);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank.setText(null);
                feedback.setText(null);
                check.setEnabled(false);
                int selectedOp = operation.getCheckedRadioButtonId();
                op = findViewById(selectedOp);
                if (op == add) {
                    operator = addition;
                    resetIncorrects();
                    generateProblem();
                } else if (op == subtract) {
                    operator = subtraction;
                    resetIncorrects();
                    generateProblem();
                } else if (op == multiply) {
                    operator = multiplication;
                    resetIncorrects();
                    generateProblem();
                } else if (op == divide) {
                    operator = division;
                    resetIncorrects();
                    generateProblem();
                } else {
                    feedback.setText("연산자 고르고 시작!");
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(incorrectTries,correctTries,badRuns);
            }
        });

        blank.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void afterTextChanged(android.text.Editable arg0) {
                buttonClickable();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        offSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 0;
                modeIndicator.setVisibility(View.INVISIBLE);
                offSwitch.setVisibility(View.INVISIBLE);
            }
        });

    }


    private void viewSetup() {
        generate = (Button) findViewById(R.id.btngenerate);
        check = (Button) findViewById(R.id.btncheck);
        offSwitch = (Button) findViewById(R.id.offSwitch);
        problem = (TextView) findViewById(R.id.randomProblem);
        feedback = (TextView) findViewById(R.id.textFeedback);
        modeIndicator = (TextView) findViewById(R.id.modeSwitchIndicator);
        operation = (RadioGroup) findViewById(R.id.radioGroup);
        add = (RadioButton) findViewById(R.id.radioAdd);
        subtract = (RadioButton) findViewById(R.id.radioSub);
        multiply = (RadioButton) findViewById(R.id.radioMult);
        divide = (RadioButton) findViewById(R.id.radioDiv);
        blank = (EditText) findViewById(R.id.numberInput);
    }

    private void generateProblem() {
        if(mode==0){
            le = Math.random() * 25;
            ri = Math.random() * 25;
        }
        if(mode==1){
            le = Math.random() * 100;
            ri = Math.random() * 100;
        }
        if(mode==2){
            le = Math.random() * 999;
            ri = Math.random() * 999;
        }

        int value1 = (int) le;
        int value2 = (int) ri;
        blank.setEnabled(true);
        if (value1 >= value2) {
            switch (operator) {
                case addition:
                    output = value1 + " + " + value2 + " = ?";
                    result = value1 + value2;
                    problem.setText(output);
                    break;
                case subtraction:
                    output = value1 + " - " + value2 + " = ?";
                    result = value1 - value2;
                    problem.setText(output);
                    break;
                case multiplication:
                    output = value1 + " x " + value2 + " = ?";
                    result = value1 * value2;
                    problem.setText(output);
                    break;
                case division:
                    if (value2 > 0) {
                        if (value1 % value2 != 0) {
                            generateProblem();
                            break;
                        } else if (value1 == value2) {
                            generateProblem();
                            break;
                        } else {
                            output = value1 + " ÷ " + value2 + " = ?";
                            result = value1 / value2;
                            problem.setText(output);
                            break;
                        }
                    } else generateProblem();
            }
        } else generateProblem();
    }


    private void incorrectCounter(int incorrectTries) {
        incorrectTries++;
        this.incorrectTries = incorrectTries;
    }

    private void badRunCounter(int badRuns){
        modeDown(badRuns);

        badRuns++;
        this.badRuns = badRuns;
    }

    private void correctCounter(int correctTries) {
        modeUp(correctTries);

        correctTries++;
        this.correctTries = correctTries;
    }

    private void resetIncorrects() {
        this.incorrectTries = 0;
    }

    private void resetBadRuns() {
        resetIncorrects();
        this.badRuns = 0;
    }

    private void resetCorrects() {
        this.correctTries = 0;
    }

    private void resetAll() {
        this.correctTries = 0;
        this.badRuns = 0;
        this.incorrectTries = 0;
    }

    private void modeUp(int correctTries){
        if(correctTries==4 && mode==1){
            this.mode = 2;
            modeSwitcher(mode);
        }
        else if(correctTries==4 && mode==0){
            this.mode = 1;
            modeSwitcher(mode);
        }
    }

    private void modeDown(int badRuns){
        if(badRuns==2 && mode==1){
            this.mode = 0;
            modeSwitcher(mode);
        }
        else if(badRuns==2 && mode==2){
            this.mode = 1;
            modeSwitcher(mode);
        }
    }

    private void modeSwitcher(int mode) {
        if (mode == 0) {
            resetAll();
            modeIndicator.setText("현재 난이도: 쉬움");
            modeIndicator.setVisibility(View.VISIBLE);
            offSwitch.setVisibility(View.INVISIBLE);
        } else if (mode == 2) {
            resetAll();
            modeIndicator.setText("현재 난이도: 어려움");
            modeIndicator.setVisibility(View.VISIBLE);
            offSwitch.setVisibility(View.VISIBLE);
        } else if (mode == 1) {
            resetAll();
            offSwitch.setVisibility(View.VISIBLE);
            modeIndicator.setText("현재 난이도: 보통");
            modeIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer(int incorrectTries, int correctTries, int badRuns) {
        int userResult = Integer.parseInt(blank.getText().toString());
        if (userResult == result) {
            blank.setText(null);
            blank.setEnabled(false);
            resetBadRuns();
            resetIncorrects();
            correctCounter(correctTries);
            feedback.setText("성공!");
            setResult(RESULT_OK);
            finish();
        } else if (userResult != result && incorrectTries<1) {
            blank.setText(null);
            resetCorrects();
            resetBadRuns();
            incorrectCounter(incorrectTries);
            feedback.setText("실패!");
        }
        else if (userResult != result && incorrectTries==1) {
            blank.setText(null);
            check.setEnabled(false);
            blank.setEnabled(false);
            resetCorrects();
            resetIncorrects();
            badRunCounter(badRuns);
            feedback.setText("연산자를 바꾸어 다시 도전하십시오.");
        }
    }

    public void buttonClickable(){
        boolean isReady = blank.getText().toString().length() > 0;
        check.setEnabled(isReady);
    }
}
