package com.example.paint;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TriangleType extends Dialog {

    Button buttonEquilateral, buttonIsocele, buttonScalene, buttonOk;
    private final MainActivity parent;

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


        buttonOk.setOnClickListener(v -> dismiss());
    }

    private class Ecouteurs implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            dismiss();
        }
    }
}
