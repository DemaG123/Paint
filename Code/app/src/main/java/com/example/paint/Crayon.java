package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class Crayon extends Outils {
    private Path currentPath;

    public Crayon() {
        super();
        currentPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, TraceLibre view) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(x, y);
                view.invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                view.addShape(new TraceLibre.DrawnShape(new Path(currentPath), new Paint(paint)));
                currentPath.reset();
                view.invalidate();
                return true;
        }
        return false;
    }

    @Override
    public void onDrawPreview(Canvas canvas) {
        canvas.drawPath(currentPath, paint);
    }
}
