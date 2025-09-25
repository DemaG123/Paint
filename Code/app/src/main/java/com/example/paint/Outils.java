package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public abstract class Outils {

    protected Paint paint;

    public Outils() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
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

    // Handle user touch events
    public abstract boolean onTouchEvent(MotionEvent event, TraceLibre view);

    // Draw temporary preview while dragging
    public abstract void onDrawPreview(Canvas canvas);

}
