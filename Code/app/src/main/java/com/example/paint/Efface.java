package com.example.paint;

import android.graphics.Color;
import android.graphics.Paint;

public class Efface {
    private Paint paint;

    public Efface() {
        paint = new Paint();
        configurePaint();
    }

    private void configurePaint() {
        paint.setColor(Color.WHITE); // Black color
        paint.setStrokeWidth(100); // Line width
        paint.setStyle(Paint.Style.STROKE); // Draw only the outline
        paint.setAntiAlias(true); // Smooth lines
    }

    public Paint getPaint() {return paint;}
    public void setColor(int color) {
        paint.setColor(color);
    }
    public void setStrokeWidth(float width) {
        paint.setStrokeWidth(width);
    }
    public void setStyle(Paint.Style style) {
        paint.setStyle(style);
    }
}
