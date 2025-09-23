package com.example.paint;

import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TriangleType extends Dialog {

    Button buttonOk, buttonChoixIso, buttonChoixCarree;

    TextView textView;

    MainActivity parent;

    public TriangleType(MainActivity parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle_type);

        buttonOk = findViewById(R.id.bouttonOk);
        buttonChoixIso = findViewById(R.id.buttonChoixIso);
        buttonChoixCarree = findViewById(R.id.buttonChoixCarree);

        textView = findViewById(R.id.textView);

        Ecouteurs ec = new Ecouteurs();
        buttonChoixIso.setOnClickListener(ec);
        buttonChoixCarree.setOnClickListener(ec);
        buttonOk.setOnClickListener(ec);

    }

    private class Ecouteurs implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == buttonChoixIso){
                textView.setText(buttonChoixIso.getText());
            }
            if (v == buttonChoixCarree){
                textView.setText(buttonChoixCarree.getText());
            }
            if (v == buttonOk){
                dismiss();
            }


        }
    }



}