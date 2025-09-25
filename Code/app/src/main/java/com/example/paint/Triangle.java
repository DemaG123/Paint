package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class Triangle extends Outils {
    private float startX, startY, endX, endY;
    private boolean drawing = false;

    @Override
    public boolean onTouchEvent(MotionEvent event, TraceLibre view) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                drawing = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                view.invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                Path triangle = new Path();
                triangle.moveTo((startX + endX) / 2, startY); // Top
                triangle.lineTo(startX, endY);                // Bottom left
                triangle.lineTo(endX, endY);                  // Bottom right
                triangle.close();

                view.addShape(new TraceLibre.DrawnShape(triangle, new Paint(paint)));
                drawing = false;
                view.invalidate();
                return true;
        }
        return false;
    }

    @Override
    public void onDrawPreview(Canvas canvas) {
        if (drawing) {
            Path triangle = new Path();
            triangle.moveTo((startX + endX) / 2, startY);
            triangle.lineTo(startX, endY);
            triangle.lineTo(endX, endY);
            triangle.close();
            canvas.drawPath(triangle, paint);
        }
    }
}
