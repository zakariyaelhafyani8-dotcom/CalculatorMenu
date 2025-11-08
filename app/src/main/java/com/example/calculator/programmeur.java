package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class programmeur extends AppCompatActivity  {

    TextView resultTv, solutionTv;
    MaterialButton btnC, btnDelet, btnBrakedOpen, btnBraketClose, btnDot;
    MaterialButton btn2nd, btnCos, btnSin, btnTan, btnDeg, btnFactX, btn1surX;
    MaterialButton btnLog, btnLn, btnExpo, btnPi, btnXpowY, btnQuerX;
    MaterialButton btnDivide, btnMultipl, btnPlus, btnMinus, btnEqual;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_programmeur);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.calc_standard) {
            Intent intent = new Intent(programmeur.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "En calcul standard", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.calc_scientifique) {
            Intent intent = new Intent(programmeur.this, Calc_scientifique.class);
            startActivity(intent);
            Toast.makeText(this, "en mode scientifique", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.calc_programeur) {
            Toast.makeText(this, "dega en calcul programmeur", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}