package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SurfaceDessin sd;
    ConstraintLayout surfaceDessinApp;
    private Path currentPath; // Path for drawing
    private Paint paint; // Paint object for styling the lines

    private Crayon crayon; // Crayon object to configure paint



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

        crayon = new Crayon();

        sd = new SurfaceDessin(this);
        sd.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1)); // Match parent
        surfaceDessinApp.addView(sd);

        Ecouteur ec = new Ecouteur();

        sd.setOnTouchListener(ec);

    }

    // Surface sur laquelle on va dessiner
    private class SurfaceDessin extends View {
        Paint crayonFull;
        Path path;
        private List<Path> paths; // To store the paths drawn by the user

        public SurfaceDessin(Context context) {
            super(context);

            // Initialize the paths list
            paths = new ArrayList<>();
            currentPath = new Path();

            // Set up the paint (line color, width, etc.)
            //paint = new Paint();
            //paint.setColor(0xFF000000); // Black color
            //paint.setStrokeWidth(20); // Line width
            //paint.setStyle(Paint.Style.STROKE); // Draw only the outline
            //paint.setAntiAlias(true); // Smooth lines

        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = crayon.getPaint();

            // Draw all the paths (draw the entire history)
            for (Path path : paths) {
                canvas.drawPath(path, paint);
            }

            // Draw the current path
            canvas.drawPath(currentPath, paint);

        }
    }

    private class Ecouteur implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
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
                // When the user lifts their finger, save the current path
                //paths.add(currentPath);
                //currentPath = new Path(); // Reset current path for next line
                return true;
            }
            return true;
        }
    }
}