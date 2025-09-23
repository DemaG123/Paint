package com.example.paint;

import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TraceLibre extends View {

    // Modes: freehand or circle
    public enum Mode {
        FREEHAND,
        CIRCLE
    }

    private Mode currentMode = Mode.FREEHAND;

    private Path currentPath;
    private Paint currentPaint;
    private List<DrawnShape> shapes;  // can be path or circle
    private int backgroundColor;

    // Circle temp vars
    private float startX, startY;
    private float tempRadius;

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
        currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(5);
        shapes = new ArrayList<>();
        backgroundColor = Color.WHITE;
        setBackgroundColor(backgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw saved shapes
        for (DrawnShape shape : shapes) {
            if (shape.isCircle) {
                canvas.drawCircle(shape.cx, shape.cy, shape.radius, shape.paint);
            } else {
                canvas.drawPath(shape.path, shape.paint);
            }
        }

        // Draw current preview
        if (currentMode == Mode.FREEHAND) {
            canvas.drawPath(currentPath, currentPaint);
        } else if (currentMode == Mode.CIRCLE && tempRadius > 0) {
            canvas.drawCircle(startX, startY, tempRadius, currentPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (currentMode) {
            case FREEHAND:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentPath.moveTo(x, y);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        currentPath.lineTo(x, y);
                        invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Save a copy of the path with paint
                        Paint newPaint = new Paint(currentPaint);
                        shapes.add(new DrawnShape(new Path(currentPath), newPaint));
                        currentPath.reset();
                        invalidate();
                        return true;
                }
                break;

            case CIRCLE:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = x;
                        startY = y;
                        tempRadius = 0;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        tempRadius = (float) Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
                        invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        Paint circlePaint = new Paint(currentPaint);
                        shapes.add(new DrawnShape(startX, startY, tempRadius, circlePaint));
                        tempRadius = 0;
                        invalidate();
                        return true;
                }
                break;
        }

        return false;
    }

    // --- APIs ---
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
        shapes.clear();
        currentPath.reset();
        tempRadius = 0;
        invalidate();
    }

    public void setBackgroundColorCustom(int color) {
        backgroundColor = color;
        setBackgroundColor(color);
        invalidate();
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    // --- Helper class ---
    private static class DrawnShape {
        Path path;
        Paint paint;
        boolean isCircle;
        float cx, cy, radius;

        // Freehand path
        DrawnShape(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
            this.isCircle = false;
        }

        // Circle
        DrawnShape(float cx, float cy, float radius, Paint paint) {
            this.cx = cx;
            this.cy = cy;
            this.radius = radius;
            this.paint = paint;
            this.isCircle = true;
        }
    }
}
