package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Rectangle extends Outils {
    private float startX, startY, endX, endY;
    private boolean drawing = false;

    @Override
    public boolean onTouchEvent(MotionEvent event, TraceLibre view) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                endX = startX;
                endY = startY;
                drawing = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                view.invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                view.addShape(new TraceLibre.DrawnShape(
                        new RectF(Math.min(startX, endX), Math.min(startY, endY),
                                Math.max(startX, endX), Math.max(startY, endY)),
                        new Paint(paint)
                ));
                drawing = false;
                view.invalidate();
                return true;
        }
        return false;
    }

    @Override
    public void onDrawPreview(Canvas canvas) {
        if (drawing) {
            canvas.drawRect(startX, startY, endX, endY, paint);
        }
    }
}
