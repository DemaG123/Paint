package com.example.paint;

import android.graphics.Color;
import android.graphics.Paint;

public class Rectangle {


    private Paint paint;

    public Rectangle() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE); // outline
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setStrokeWidth(float width) {
        paint.setStrokeWidth(width);
    }

}
