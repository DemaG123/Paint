package com.example.paint;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TriangleType extends Dialog {

    Button buttonEquilateral, buttonIsocele, buttonScalene, buttonOk;

    MainActivity parent;

    public TriangleType(MainActivity parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle_type);

        buttonEquilateral = findViewById(R.id.buttonEquilateral);
        buttonIsocele = findViewById(R.id.buttonIsocele);
        buttonScalene = findViewById(R.id.buttonScalene);
        buttonOk = findViewById(R.id.bouttonOk);

        Ecouteurs ec = new Ecouteurs();
        buttonEquilateral.setOnClickListener(ec);
        buttonIsocele.setOnClickListener(ec);
        buttonScalene.setOnClickListener(ec);
        buttonOk.setOnClickListener(ec);

    }

    private class Ecouteurs implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == buttonEquilateral){
                //textView.setText(buttonChoixIso.getText());
            }
            if (v == buttonIsocele){
                //textView.setText(buttonChoixCarree.getText());
            }
            if (v == buttonScalene){
                //textView.setText(buttonChoixCarree.getText());
            }
            if (v == buttonOk){
                dismiss();
            }


        }
    }



}