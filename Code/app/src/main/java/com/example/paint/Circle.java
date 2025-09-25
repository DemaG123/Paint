package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Circle extends Outils {

    private float startX;
    private float startY;
    private float radius;
    private boolean drawing = false;

    public Circle() {
        super();
    }

    public Circle(int color, float strokeWidth) {
        super();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, TraceLibre view) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                radius = 0f;
                drawing = true;
                view.invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:
                radius = (float) Math.hypot(x - startX, y - startY);
                view.invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                radius = (float) Math.hypot(x - startX, y - startY);

                // Save a copy of the paint so future changes don't affect this circle
                Paint savedPaint = new Paint(paint);

                // Add the finished circle to the canvas' shape list
                view.addShape(new TraceLibre.DrawnShape(startX, startY, radius, savedPaint));

                drawing = false;
                view.invalidate();
                return true;
        }

        return false;
    }

    @Override
    public void onDrawPreview(Canvas canvas) {
        if (drawing && radius > 0f) {
            canvas.drawCircle(startX, startY, radius, paint);
        }
    }
}
