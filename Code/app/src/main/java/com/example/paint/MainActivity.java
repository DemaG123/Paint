package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SurfaceDessin sd;
    ConstraintLayout surfaceDessinApp;
    private Path currentPath; // Path for drawing
    private Paint paint; // Paint object for styling the lines
    private List<Path> paths; // To store the paths drawn by the user
    private List<DrawnPath> pathsAndTools;

    ImageView buttonCrayon;
    ImageView buttonEfface;
    ImageView buttonPot;

    Chip chipWhite;
    Chip chipBlack;
    Chip chipRed;
    Chip chipOrange;
    Chip chipYellow;
    Chip chipGreen;
    Chip chipBlue;
    Chip chipPurple;

    private Crayon crayon; // Crayon object to configure paint
    private Efface efface;


    ImageView buttonSelected;



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

        surfaceDessinApp = findViewById(R.id.surfaceDessinApp);
        buttonCrayon = findViewById(R.id.buttonCrayon);
        buttonPot = findViewById(R.id.buttonPot);
        buttonEfface = findViewById(R.id.buttonEfface);

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

        paint = crayon.getPaint();

        sd = new SurfaceDessin(this);
        sd.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1)); // Match parent
        surfaceDessinApp.addView(sd);

        Ecouteur ec = new Ecouteur();

        sd.setOnTouchListener(ec);
        buttonCrayon.setOnClickListener(ec);
        buttonPot.setOnClickListener(ec);
        buttonEfface.setOnClickListener(ec);

        Ecouteur ecColor = new Ecouteur();

        chipWhite.setOnClickListener(ecColor);
        chipBlack.setOnClickListener(ecColor);
        chipRed.setOnClickListener(ecColor);
        chipOrange.setOnClickListener(ecColor);
        chipYellow.setOnClickListener(ecColor);
        chipGreen.setOnClickListener(ecColor);
        chipBlue.setOnClickListener(ecColor);
        chipPurple.setOnClickListener(ecColor);

    }

    private int backgroundColor = Color.WHITE;
    private class SurfaceDessin extends View {


        public SurfaceDessin(Context context) {
            super(context);
            // Initialize the paths list
            pathsAndTools = new ArrayList<>();
            currentPath = new Path();
            //backgroundColor = Color.LTGRAY; // Set the initial background color
            setBackgroundColor(backgroundColor); // Set background color to the view

        }
        // Method to update the background color and also update the backgroundColor field
        public void updateBackgroundColor(int newColor) {
            backgroundColor = newColor;  // Update the background color field
            setBackgroundColor(backgroundColor);  // Apply the color to the view's background
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            // Draw all the paths (draw the entire history)
            for (DrawnPath drawnPath : pathsAndTools) {
                canvas.drawPath(drawnPath.path, drawnPath.paint);  // Use the correct paint for each path
            }

            // Draw the current path
            canvas.drawPath(currentPath, paint);

        }
    }

    private class Ecouteur implements View.OnTouchListener, View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == buttonCrayon) {
                buttonSelected = buttonCrayon;
                paint = crayon.getPaint();
            }
            if (v == buttonPot) {
                buttonSelected = buttonPot;
                //sd.updateBackgroundColor(Color.WHITE); // Or any other color you'd like
                pathsAndTools.clear();
                currentPath.reset();
                sd.invalidate();

            }
            if (v == buttonEfface) {
                buttonSelected = buttonEfface;
                paint = efface.getPaint();
                efface.setColor(backgroundColor);
            }

            if (v == chipWhite) {
                backgroundColor = getChipBackgroundColor(chipWhite);
            }

            if (v == chipBlack) {
                backgroundColor = getChipBackgroundColor(chipBlack);
            }

            if (v == chipRed) {
                backgroundColor = getChipBackgroundColor(chipRed);
            }

            if (v == chipOrange) {
                backgroundColor = getChipBackgroundColor(chipOrange);
            }

            if (v == chipYellow) {
                backgroundColor = getChipBackgroundColor(chipYellow);
            }

            if (v == chipGreen) {
                backgroundColor = getChipBackgroundColor(chipGreen);
            }

            if (v == chipBlue) {
                backgroundColor = getChipBackgroundColor(chipBlue);
            }

            if (v == chipPurple) {
                backgroundColor = getChipBackgroundColor(chipPurple);
            }



        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (buttonSelected == buttonCrayon || buttonSelected == buttonEfface) {
                float x = event.getX();
                float y = event.getY();

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    currentPath.moveTo(x, y);
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE){
                    currentPath.lineTo(x, y);
                    sd.invalidate();
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    pathsAndTools.add(new DrawnPath(currentPath, paint));
                    currentPath = new Path();  // Reset current path for next line
                    return true;
                }
            }
            if (buttonSelected == buttonPot) {
                sd.setBackgroundColor(backgroundColor);
            }


            return true;

        }

    }

    private int getChipBackgroundColor(Chip chip) {
        // Chips use a ColorStateList, so get the current (default) color
        return chip.getChipBackgroundColor().getDefaultColor();
    }

    private class DrawnPath {
        Path path;
        Paint paint;

        DrawnPath(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

}