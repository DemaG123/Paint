package com.example.paint;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

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
import android.widget.SeekBar;

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

    TraceLibre traceLibre;
    ConstraintLayout surfaceDessinApp;

    ImageView buttonCrayon, buttonEfface, buttonPot;
    Chip chipWhite, chipBlack, chipRed, chipOrange, chipYellow, chipGreen, chipBlue, chipPurple;
    SeekBar strokeWidth;

    private Crayon crayon;
    private Efface efface;

    ImageView buttonSelected;
    private int backgroundColor = Color.WHITE;

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

        // Button listeners
        buttonCrayon.setOnClickListener(v -> {
            buttonSelected = buttonCrayon;
            traceLibre.setPaint(crayon.getPaint());
            strokeWidth.setVisibility(VISIBLE);
        });

        buttonEfface.setOnClickListener(v -> {
            buttonSelected = buttonEfface;
            efface.setColor(backgroundColor);
            traceLibre.setPaint(efface.getPaint());
        });

        buttonPot.setOnClickListener(v -> {
            buttonSelected = buttonPot;
            traceLibre.clearCanvas();
        });

        strokeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                traceLibre.setStrokeWidth(progress * 20);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Color chips
        View.OnClickListener colorClick = v -> {
            Chip chip = (Chip) v;
            int color = chip.getChipBackgroundColor().getDefaultColor();

            if (buttonSelected == buttonCrayon) {
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

    /*SurfaceDessin sd;
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

    SeekBar strokeWidth;

    private Crayon crayon; // Crayon object to configure paint
    private Efface efface;


    ImageView buttonSelected;
    private int backgroundColor = Color.WHITE;


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

        // Menu color buttons
        chipWhite = findViewById(R.id.chipWhite);
        chipBlack = findViewById(R.id.chipBlack);
        chipRed = findViewById(R.id.chipRed);
        chipOrange = findViewById(R.id.chipOrange);
        chipYellow = findViewById(R.id.chipYellow);
        chipGreen = findViewById(R.id.chipGreen);
        chipBlue = findViewById(R.id.chipBlue);
        chipPurple = findViewById(R.id.chipPurple);

        // Canva Area
        surfaceDessinApp = findViewById(R.id.surfaceDessinApp);

        // Menu tools buttons
        buttonCrayon = findViewById(R.id.buttonCrayon);
        buttonPot = findViewById(R.id.buttonPot);
        buttonEfface = findViewById(R.id.buttonEfface);
        strokeWidth = findViewById(R.id.strokeWidth);

        //Tools
        crayon = new Crayon();
        paint = crayon.getPaint();
        efface = new Efface();

        //Canvas
        sd = new SurfaceDessin(this);
        sd.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1)); // Match parent
        surfaceDessinApp.addView(sd);

        //Listener for Tools & Canvas
        Ecouteur ec = new Ecouteur();
        sd.setOnTouchListener(ec);
        buttonCrayon.setOnClickListener(ec);
        buttonPot.setOnClickListener(ec);
        buttonEfface.setOnClickListener(ec);
        strokeWidth.setOnSeekBarChangeListener(ec);


        //Listener for Colors
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

    private class SurfaceDessin extends View {

        public SurfaceDessin(Context context) {
            super(context);
            // Initialize the paths list
            pathsAndTools = new ArrayList<>();
            currentPath = new Path();
            setBackgroundColor(backgroundColor);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            for (DrawnPath drawnPath : pathsAndTools) {
                canvas.drawPath(drawnPath.path, drawnPath.paint);
            }

            canvas.drawPath(currentPath, paint);

        }
    }

    private class Ecouteur implements View.OnTouchListener, View.OnClickListener , SeekBar.OnSeekBarChangeListener{
        int strokeFactor = strokeWidth.getProgress();
        @Override
        public void onClick(View v) {

            if (v == buttonCrayon) {

                buttonSelected = buttonCrayon;
                paint = crayon.getPaint();
                strokeWidth.setVisibility(VISIBLE);
                crayon.setStrokeWidth(strokeFactor * 10);
            }
            else if (v == buttonPot) {
                buttonSelected = buttonPot;
                pathsAndTools.clear();
                currentPath.reset();
                sd.invalidate();

            }
            else if (v == buttonEfface) {
                buttonSelected = buttonEfface;
                paint = efface.getPaint();
                efface.setColor(backgroundColor);
            }

            else if (v instanceof Chip) {
                Chip chip = (Chip) v;
                int color = getChipBackgroundColor(chip);

                if (buttonSelected == buttonCrayon) {
                    paint.setColor(color);
                } else {
                    backgroundColor = color;
                    efface.setColor(backgroundColor);
                }
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
                    Paint newPaint = new Paint(paint);
                    pathsAndTools.add(new DrawnPath(currentPath, newPaint));
                    currentPath = new Path();
                    return true;
                }
            }
            if (buttonSelected == buttonPot) {
                sd.setBackgroundColor(backgroundColor);
                sd.invalidate();
            }


            return true;

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            crayon.setStrokeWidth(progress * 20);
            if (buttonSelected == buttonCrayon) {
                paint.setStrokeWidth(crayon.getPaint().getStrokeWidth());
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

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
    }*/

}