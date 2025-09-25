package com.example.paint;

import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {

    TraceLibre traceLibre;
    ConstraintLayout surfaceDessinApp;

    ImageView buttonCrayon, buttonEfface, buttonPot, buttonCircle, buttonRectangle, buttonTriangle;
    Chip chipWhite, chipBlack, chipRed, chipOrange, chipYellow, chipGreen, chipBlue, chipPurple;
    SeekBar strokeWidth;

    private Outils crayon, efface,circle, rectangle, triangle;
    ImageView buttonSelected;
    private int backgroundColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        surfaceDessinApp = findViewById(R.id.surfaceDessinApp);

        traceLibre = new TraceLibre(this);
        traceLibre.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        surfaceDessinApp.addView(traceLibre);

        buttonCrayon = findViewById(R.id.buttonCrayon);
        buttonPot = findViewById(R.id.buttonPot);
        buttonEfface = findViewById(R.id.buttonEfface);
        buttonCircle = findViewById(R.id.buttonCircle);
        buttonRectangle = findViewById(R.id.buttonRectangle);
        buttonTriangle = findViewById(R.id.buttonTriangle);
        strokeWidth = findViewById(R.id.strokeWidth);

        chipWhite = findViewById(R.id.chipWhite);
        chipBlack = findViewById(R.id.chipBlack);
        chipRed = findViewById(R.id.chipRed);
        chipOrange = findViewById(R.id.chipOrange);
        chipYellow = findViewById(R.id.chipYellow);
        chipGreen = findViewById(R.id.chipGreen);
        chipBlue = findViewById(R.id.chipBlue);
        chipPurple = findViewById(R.id.chipPurple);

        crayon = new Crayon();
        efface = new Efface();
        circle = new Circle();
        rectangle = new Rectangle();
        triangle = new Triangle();

        buttonCrayon.setOnClickListener(v -> {
            buttonSelected = buttonCrayon;
            traceLibre.setTool(crayon);
            strokeWidth.setVisibility(VISIBLE);
        });

        buttonEfface.setOnClickListener(v -> {
            buttonSelected = buttonEfface;
            efface.setColor(backgroundColor);
            traceLibre.setTool(efface);
        });

        buttonPot.setOnClickListener(v -> {
            buttonSelected = buttonPot;
            traceLibre.clearCanvas();
        });

        buttonCircle.setOnClickListener(v -> {
            buttonSelected = buttonCircle;
            traceLibre.setTool(circle);
            strokeWidth.setVisibility(VISIBLE);
        });

        buttonRectangle.setOnClickListener(v -> {
            buttonSelected = buttonRectangle;
            traceLibre.setTool(rectangle);
            strokeWidth.setVisibility(VISIBLE);
        });

        buttonTriangle.setOnClickListener(v -> {
            //buttonSelected = buttonTriangle;
            //traceLibre.setTool(triangle);
            //strokeWidth.setVisibility(VISIBLE);
            TriangleType dialog = new TriangleType(MainActivity.this);
            dialog.show();
        });

        strokeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                traceLibre.setStrokeWidth(progress * 20);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        chipWhite.setOnClickListener(v -> applyColor(chipWhite));
        chipBlack.setOnClickListener(v -> applyColor(chipBlack));
        chipRed.setOnClickListener(v -> applyColor(chipRed));
        chipOrange.setOnClickListener(v -> applyColor(chipOrange));
        chipYellow.setOnClickListener(v -> applyColor(chipYellow));
        chipGreen.setOnClickListener(v -> applyColor(chipGreen));
        chipBlue.setOnClickListener(v -> applyColor(chipBlue));
        chipPurple.setOnClickListener(v -> applyColor(chipPurple));
    }

    private void applyColor(Chip chip) {
        int color = chip.getChipBackgroundColor().getDefaultColor();
        if (buttonSelected == buttonCrayon || buttonSelected == buttonRectangle || buttonSelected == buttonTriangle || buttonSelected == buttonCircle) {
            traceLibre.setColor(color);
        } else {
            backgroundColor = color;
            efface.setColor(backgroundColor);
            if (buttonSelected == buttonPot) {
                traceLibre.setBackgroundColorCustom(backgroundColor);
            }
        }
    }
}
