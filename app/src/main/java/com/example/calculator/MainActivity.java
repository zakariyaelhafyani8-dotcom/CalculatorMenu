package com.example.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.example.calculator.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv, solutionTv;
    MaterialButton btnC, btnAc, btnBrakedOpen, btnBraketClose, btnDot;
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
            Toast.makeText(this, "dega en standar", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.calc_scientifique) {
            // Opération
            Intent intent = new Intent(MainActivity.this, Calc_scientifique.class);
            startActivity(intent);
            Toast.makeText(this, "en calcul scientifique", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.calc_programeur){
            Intent intent = new Intent(MainActivity.this, programmeur.class);
            startActivity(intent);
            Toast.makeText(this, "en calcul programmeur", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(btnC, R.id.button_c);
        assignId(btnBrakedOpen, R.id.button_braketOpen);
        assignId(btnBraketClose, R.id.button_braketClose);
        assignId(btnDot, R.id.button_dot);
        assignId(btnEqual, R.id.button_equal);
        assignId(btnDivide, R.id.button_divide);
        assignId(btnMultipl, R.id.button_multipl);
        assignId(btnPlus, R.id.button_plus);
        assignId(btnMinus, R.id.button_minus);
        assignId(btn0, R.id.button_zero);
        assignId(btn1, R.id.button_one);
        assignId(btn2, R.id.button_two);
        assignId(btn3, R.id.button_three);
        assignId(btn4, R.id.button_four);
        assignId(btn5, R.id.button_five);
        assignId(btn6, R.id.button_sex);
        assignId(btn7, R.id.button_seven);
        assignId(btn8, R.id.button_eight);
        assignId(btn9, R.id.button_nine);
    }
    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }

        if(buttonText.equals("C")){
            try {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
            }catch (Exception e){

            }

        }else{
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Erreur")){
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1); // Important for Android

            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", ""); // ✅ fixed here
            }

            return finalResult;
        } catch (Exception e) {
            return "Erreur";
        } finally {
            Context.exit(); // ✅ always exit the context
        }
    }

}