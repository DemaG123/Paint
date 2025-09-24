package com.example.paint;

import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {

    TraceLibre traceLibre;
    ConstraintLayout surfaceDessinApp;

    ImageView buttonCrayon, buttonEfface, buttonPot, buttonCircle, buttonRectangle, buttonTriangle;
    Chip chipWhite, chipBlack, chipRed, chipOrange, chipYellow, chipGreen, chipBlue, chipPurple;
    SeekBar strokeWidth;

    Crayon crayon;
    Efface efface;

    ImageView buttonSelected;
    int backgroundColor = Color.WHITE;

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

        // Layout
        surfaceDessinApp = findViewById(R.id.surfaceDessinApp);

        // DrawingView
        traceLibre = new TraceLibre(this);
        traceLibre.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        surfaceDessinApp.addView(traceLibre);

        // Tools
        buttonCrayon = findViewById(R.id.buttonCrayon);
        buttonPot = findViewById(R.id.buttonPot);
        buttonEfface = findViewById(R.id.buttonEfface);
        buttonCircle = findViewById(R.id.buttonCircle);
        buttonRectangle = findViewById(R.id.buttonRectangle);
        buttonTriangle = findViewById(R.id.buttonTriangle);
        strokeWidth = findViewById(R.id.strokeWidth);

        // Colors
        chipWhite = findViewById(R.id.chipWhite);
        chipBlack = findViewById(R.id.chipBlack);
        chipRed = findViewById(R.id.chipRed);
        chipOrange = findViewById(R.id.chipOrange);
        chipYellow = findViewById(R.id.chipYellow);
        chipGreen = findViewById(R.id.chipGreen);
        chipBlue = findViewById(R.id.chipBlue);
        chipPurple = findViewById(R.id.chipPurple);

        // Paint helpers
        crayon = new Crayon();
        efface = new Efface();

        // --- Tool Buttons ---
        buttonCrayon.setOnClickListener(v -> {
            buttonSelected = buttonCrayon;
            traceLibre.setMode(TraceLibre.Mode.FREEHAND);
            traceLibre.setPaint(crayon.getPaint());
            strokeWidth.setVisibility(VISIBLE);
        });

        buttonEfface.setOnClickListener(v -> {
            buttonSelected = buttonEfface;
            traceLibre.setMode(TraceLibre.Mode.FREEHAND);
            efface.setColor(backgroundColor);
            traceLibre.setPaint(efface.getPaint());
        });

        buttonPot.setOnClickListener(v -> {
            buttonSelected = buttonPot;
            traceLibre.clearCanvas();
        });

        buttonCircle.setOnClickListener(v -> {
            buttonSelected = buttonCircle;
            traceLibre.setMode(TraceLibre.Mode.CIRCLE);
            traceLibre.setPaint(crayon.getPaint());
            strokeWidth.setVisibility(VISIBLE);
        });

        buttonRectangle.setOnClickListener(v -> {
            buttonSelected = buttonRectangle;
            traceLibre.setMode(TraceLibre.Mode.RECTANGLE);
            traceLibre.setPaint(crayon.getPaint());
            strokeWidth.setVisibility(VISIBLE);
        });

        buttonTriangle.setOnClickListener(v -> {
            TriangleType dialog = new TriangleType(MainActivity.this);
            dialog.show();
        });

        // --- Stroke width slider ---
        strokeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                traceLibre.setStrokeWidth(progress * 20);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // --- Color chips ---
        View.OnClickListener colorClick = v -> {
            Chip chip = (Chip) v;
            int color = chip.getChipBackgroundColor().getDefaultColor();

            if (buttonSelected == buttonCrayon ||
                    buttonSelected == buttonCircle ||
                    buttonSelected == buttonRectangle ||
                    buttonSelected == buttonTriangle) {
                traceLibre.setColor(color);
            } else {
                backgroundColor = color;
                efface.setColor(backgroundColor);
                if (buttonSelected == buttonPot) {
                    traceLibre.setBackgroundColorCustom(backgroundColor);
                }
            }
        };

        chipWhite.setOnClickListener(colorClick);
        chipBlack.setOnClickListener(colorClick);
        chipRed.setOnClickListener(colorClick);
        chipOrange.setOnClickListener(colorClick);
        chipYellow.setOnClickListener(colorClick);
        chipGreen.setOnClickListener(colorClick);
        chipBlue.setOnClickListener(colorClick);
        chipPurple.setOnClickListener(colorClick);
    }
}
