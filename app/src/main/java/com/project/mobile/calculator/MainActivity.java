package com.project.mobile.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView text_view_expression;
    private TextView text_view_result;
    private Button btn_num_0;
    private Button btn_num_1;
    private Button btn_num_2;
    private Button btn_num_3;
    private Button btn_num_4;
    private Button btn_num_5;
    private Button btn_num_6;
    private Button btn_num_7;
    private Button btn_num_8;
    private Button btn_num_9;
    private Button btn_dot;
    private Button btn_ac;
    private ImageButton img_btn_equals;
    private ImageButton img_btn_plus;
    private ImageButton img_btn_minus;
    private ImageButton img_btn_divide;
    private ImageButton img_btn_multiply;
    private ImageButton img_btn_percent;
    private String num_left;
    private String num_right;
    private String operator;
    private String result;
    private String expression;
    private View.OnClickListener numberButtonListerner;
    private View.OnClickListener operatorImageButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        expression="";
        operator = "";
        num_left = "";
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        result = sharedPreferences.getString("last_result","0");
        text_view_result.setText(result);
        numberButtonListerner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button)view;
                onNumberButtonClicked(button.getText().toString());
            }
        };
        operatorImageButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.img_buttton_plus:
                        onOperatorImageButtonClicked("+");
                        break;
                    case R.id.img_button_minus:
                        onOperatorImageButtonClicked("-");
                        break;
                    case R.id.img_button_multiply:
                        onOperatorImageButtonClicked("×");
                        break;
                    case R.id.img_button_divide:
                        onOperatorImageButtonClicked(":");
                        break;
                }
            }
        };

        setNumberButtonListioner();
        setOperatorImageButtonListener();
        setDotButtonListener();
        setAcButtonListener();
        setEqualsButtonListener();
    }
    public void initWidgets(){
        text_view_expression = findViewById(R.id.text_view_expression);
        text_view_result = findViewById(R.id.text_view_result);
        btn_num_0 = findViewById(R.id.button_num_0);
        btn_num_1 = findViewById(R.id.button_num_1);
        btn_num_2 = findViewById(R.id.button_num_2);
        btn_num_3 = findViewById(R.id.button_num_3);
        btn_num_4 = findViewById(R.id.button_num_4);
        btn_num_5 = findViewById(R.id.button_num_5);
        btn_num_6 = findViewById(R.id.button_num_6);
        btn_num_7 = findViewById(R.id.button_num_7);
        btn_num_8 = findViewById(R.id.button_num_8);
        btn_num_9 = findViewById(R.id.button_num_9);
        btn_dot = findViewById(R.id.button_dot);
        btn_ac = findViewById(R.id.button_ac);
        img_btn_equals = findViewById(R.id.img_button_equals);
        img_btn_divide = findViewById(R.id.img_button_divide);
        img_btn_plus = findViewById(R.id.img_buttton_plus);
        img_btn_minus = findViewById(R.id.img_button_minus);
        img_btn_multiply = findViewById(R.id.img_button_multiply);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calculator, menu);
        return true;
    }
    private void setNumberButtonListioner() {
        btn_num_0.setOnClickListener(numberButtonListerner);
        btn_num_1.setOnClickListener(numberButtonListerner);
        btn_num_2.setOnClickListener(numberButtonListerner);
        btn_num_4.setOnClickListener(numberButtonListerner);
        btn_num_5.setOnClickListener(numberButtonListerner);
        btn_num_6.setOnClickListener(numberButtonListerner);
        btn_num_7.setOnClickListener(numberButtonListerner);
        btn_num_8.setOnClickListener(numberButtonListerner);
        btn_num_9.setOnClickListener(numberButtonListerner);
    }
    private void onNumberButtonClicked(String num){
        if (expression.endsWith("+") || expression.endsWith("-") ||
                expression.endsWith("×") || expression.endsWith(":")) {
            expression += " " + num;
        }
        else if (!expression.equals("0")) {
            expression += num;
        }
        else expression = num;
        text_view_expression.setText(expression);
    }
    private void setOperatorImageButtonListener() {
        img_btn_multiply.setOnClickListener(operatorImageButtonListener);
        img_btn_divide.setOnClickListener(operatorImageButtonListener);
        img_btn_minus.setOnClickListener(operatorImageButtonListener);
        img_btn_plus.setOnClickListener(operatorImageButtonListener);
    }
    private void onOperatorImageButtonClicked(String operator) {
        if (expression.endsWith("+") || expression.endsWith("-") ||
        expression.endsWith("×") || expression.endsWith(":")) {
            expression = expression.substring(0,expression.length() - 1) + operator;
        }
        else if (Utils.checkContainOperator(expression)){
            calculate();
            text_view_result.setText(result);
            expression = result + " " + operator;
        }
        else if (expression.isEmpty()) {
            expression += "0 " + operator;
        }
        else expression += " " + operator;
        this.operator = operator;
        text_view_expression.setText(expression);
    }
    private void setDotButtonListener() {
        btn_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDotButtonClicked();
            }
        });
    }
    private void onDotButtonClicked() {
        if (Utils.checkContainOperator(expression)) {
            if (expression.endsWith("+") || expression.endsWith("-") ||
                    expression.endsWith("×") || expression.endsWith(":")) {
                expression += " 0.";
            }
            else if (!expression.substring(expression.indexOf(operator)).contains(".")){
                expression += ".";
            }
        }
        else if (!expression.contains(".")) {
            expression += ".";
        }
        text_view_expression.setText(expression);
    }
    private void setAcButtonListener() {
        btn_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = "";
                text_view_expression.setText(expression);
                text_view_result.setText("0");
            }
        });
    }
    private void setEqualsButtonListener() {
        img_btn_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!operator.isEmpty() && expression.endsWith(operator)) {
                    result = "ERROR";
                }
                else calculate();
                text_view_result.setText(result);
            }
        });
    }
    private void calculate() {
        if (!operator.isEmpty()) {
            Log.d("ahuhu",operator);
            num_left = expression.substring(0, expression.indexOf(operator) - 1);
            num_right = expression.substring(expression.indexOf(operator) + 2);
            double left = Double.parseDouble(num_left);
            double right = Double.parseDouble(num_right);
            switch (operator) {
                case "+" :
                    result = Double.toString(left + right);
                    break;
                case "-" :
                    result = Double.toString(left - right);
                    break;
                case "×":
                    result = Double.toString(left  * right);
                    break;
                case ":":
                    if (right == 0) {
                        result = "INFINITY";
                    }
                    else result = Double.toString(left / right);
                    break;
                case "":
                    result = expression;
                    break;
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_item:
                expression = "";
                text_view_expression.setText(expression);
                text_view_result.setText("0");
                break;
            case R.id.save_item:
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Log.d("result",result);
                editor.putString("last_result",result);
                editor.apply();
        }
        return false;
    }

}
