package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Calc_scientifique extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton btnC, btnDelet, btnBrakedOpen, btnBraketClose, btnDot;
    MaterialButton btn2nd, btnCos, btnSin, btnTan, btnDeg, btnFactX, btn1surX;
    MaterialButton btnLog, btnLn, btnExpo, btnPi, btnXpowY, btnQuerX;
    MaterialButton btnDivide, btnMultipl, btnPlus, btnMinus, btnEqual;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.calc_standard) {
            Intent intent = new Intent(Calc_scientifique.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "En calcul standard", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.calc_scientifique) {
            Toast.makeText(this, "Déjà en mode scientifique", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.calc_programeur) {
            Intent intent = new Intent(Calc_scientifique.this, programmeur.class);
            startActivity(intent);
            Toast.makeText(this, "en calcul programmeur", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_scientifique);

        resultTv = findViewById(R.id.result);
        solutionTv = findViewById(R.id.solution);

        assignId(btnC, R.id.button_C);
        assignId(btnDelet, R.id.button_delete);
        assignId(btnBrakedOpen, R.id.button_braketopen);
        assignId(btnBraketClose, R.id.button_braketclose);
        assignId(btnDot, R.id.button_point);
        assignId(btnEqual, R.id.button_Equal);
        assignId(btnDivide, R.id.button_div);
        assignId(btnMultipl, R.id.button_multi);
        assignId(btnPlus, R.id.button_Plus);
        assignId(btnMinus, R.id.button_Minus);
        assignId(btn0, R.id.button_0);
        assignId(btn1, R.id.button_1);
        assignId(btn2, R.id.button_2);
        assignId(btn3, R.id.button_3);
        assignId(btn4, R.id.button_4);
        assignId(btn5, R.id.button_5);
        assignId(btn6, R.id.button_6);
        assignId(btn7, R.id.button_7);
        assignId(btn8, R.id.button_8);
        assignId(btn9, R.id.button_9);
        assignId(btnCos, R.id.button_cos);
        assignId(btnSin, R.id.button_sin);
        assignId(btnTan, R.id.button_tan);
        assignId(btn2nd, R.id.button_2nd);
        assignId(btnFactX, R.id.button_fact);
        assignId(btnXpowY, R.id.button_XpowY);
        assignId(btnQuerX, R.id.button_qwerX);
        assignId(btnDeg, R.id.button_deg);
        assignId(btn1surX, R.id.button_1surX);
        assignId(btnLog, R.id.button_log);
        assignId(btnLn, R.id.button_ln);
        assignId(btnPi, R.id.button_pi);
        assignId(btnExpo, R.id.button_expo);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        switch (buttonText) {
            case "C":
                if(dataToCalculate == ""){
                    resultTv.setText("0");
                }
                dataToCalculate = "";
                break;

            case "Del":
                if (!dataToCalculate.isEmpty()) {
                    dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                }
                break;

            case "=":
                String finalResult = getResult(dataToCalculate);
                resultTv.setText(finalResult);
                return;

            default:
                dataToCalculate += buttonText;
        }

        solutionTv.setText(dataToCalculate);
    }

    public static double factorial(int n) {
        if (n == 0 || n == 1) return 1;
        double result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1); // Important for Android
            Scriptable scriptable = context.initSafeStandardObjects();

            // Support scientific functions like sin, cos, tan, log, etc.
            data = data.replace("sin", "Math.sin")
                    .replace("cos", "Math.cos")
                    .replace("tan", "Math.tan")
                    .replace("log", "Math.log10")
                    .replace("ln", "Math.log")
                    .replace("π", "Math.PI")
                    .replace("√", "Math.sqrt")
                    .replace("^", "Math.pow")
                    .replace("!","factorial")
                    .replace("e^", "Math.exp");

            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        } catch (Exception e) {
            return "Erreur";
        } finally {
            Context.exit();
        }
    }
}
