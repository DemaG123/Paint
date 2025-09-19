package com.example.paint;

import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TraceLibre extends View {

    private Path currentPath;
    private Paint currentPaint;
    private List<DrawnPath> pathsAndPaints;
    private int backgroundColor;

    public TraceLibre(Context context) {
        super(context);
        init();
    }

    public TraceLibre(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        currentPath = new Path();
        currentPaint = new Paint();
        pathsAndPaints = new ArrayList<>();
        backgroundColor = Color.WHITE;
        setBackgroundColor(backgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (DrawnPath drawnPath : pathsAndPaints) {
            canvas.drawPath(drawnPath.path, drawnPath.paint);
        }

        canvas.drawPath(currentPath, currentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                // Save a copy of the paint
                Paint newPaint = new Paint(currentPaint);
                pathsAndPaints.add(new DrawnPath(currentPath, newPaint));
                currentPath = new Path();
                return true;
        }
        return false;
    }

    // API to control from MainActivity
    public void setPaint(Paint paint) {
        this.currentPaint = paint;
    }

    public void setStrokeWidth(float width) {
        currentPaint.setStrokeWidth(width);
    }

    public void setColor(int color) {
        currentPaint.setColor(color);
    }

    public void clearCanvas() {
        pathsAndPaints.clear();
        currentPath.reset();
        invalidate();
    }

    public void setBackgroundColorCustom(int color) {
        backgroundColor = color;
        setBackgroundColor(color);
        invalidate();
    }

    // --- Helper class ---
    private static class DrawnPath {
        Path path;
        Paint paint;

        DrawnPath(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

}
