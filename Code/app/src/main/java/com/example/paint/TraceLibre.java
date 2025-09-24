package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TraceLibre extends View {

    public enum Mode {
        FREEHAND,
        CIRCLE,
        RECTANGLE,
        TRIANGLE_EQUILATERAL,
        TRIANGLE_ISOSCELES,
        TRIANGLE_SCALENE
    }

    private Mode currentMode = Mode.FREEHAND;

    private Path currentPath;
    private Paint currentPaint;
    private List<DrawnShape> shapes;
    private int backgroundColor;

    // Shared
    private float startX, startY;

    // Circle
    private float tempRadius;

    // Rectangle
    private float endX, endY;

    // Triangle
    private float triEndX, triEndY;

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
            } else if (shape.isRectangle) {
                canvas.drawRect(shape.rect, shape.paint);
            } else if (shape.isTriangle) {
                canvas.drawPath(shape.trianglePath, shape.paint);
            } else {
                canvas.drawPath(shape.path, shape.paint);
            }
        }

        // --- Live Preview ---
        if (currentMode == Mode.FREEHAND) {
            canvas.drawPath(currentPath, currentPaint);
        } else if (currentMode == Mode.CIRCLE && tempRadius > 0) {
            canvas.drawCircle(startX, startY, tempRadius, currentPaint);
        } else if (currentMode == Mode.RECTANGLE) {
            if (startX != endX && startY != endY) {
                canvas.drawRect(new RectF(startX, startY, endX, endY), currentPaint);
            }
        } else if (currentMode == Mode.TRIANGLE_EQUILATERAL ||
                currentMode == Mode.TRIANGLE_ISOSCELES ||
                currentMode == Mode.TRIANGLE_SCALENE) {
            if (startX != triEndX && startY != triEndY) {
                Path preview = buildTrianglePath(currentMode, startX, startY, triEndX, triEndY);
                canvas.drawPath(preview, currentPaint);
            }
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

            case RECTANGLE:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = x;
                        startY = y;
                        endX = x;
                        endY = y;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        endX = x;
                        endY = y;
                        invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        endX = x;
                        endY = y;
                        Paint rectPaint = new Paint(currentPaint);
                        shapes.add(new DrawnShape(new RectF(startX, startY, endX, endY), rectPaint));
                        invalidate();
                        return true;
                }
                break;

            case TRIANGLE_EQUILATERAL:
            case TRIANGLE_ISOSCELES:
            case TRIANGLE_SCALENE:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = x;
                        startY = y;
                        triEndX = x;
                        triEndY = y;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        triEndX = x;
                        triEndY = y;
                        invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        triEndX = x;
                        triEndY = y;
                        Path triangle = buildTrianglePath(currentMode, startX, startY, triEndX, triEndY);
                        Paint triPaint = new Paint(currentPaint);
                        shapes.add(new DrawnShape(triangle, triPaint, true));
                        invalidate();
                        return true;
                }
        }
        return false;
    }

    // --- Build Triangle Path ---
    private Path buildTrianglePath(Mode type, float x1, float y1, float x2, float y2) {
        Path path = new Path();
        float midX = (x1 + x2) / 2;

        if (type == Mode.TRIANGLE_EQUILATERAL) {
            float side = Math.abs(x2 - x1);
            float height = (float) (Math.sqrt(3) / 2 * side);
            path.moveTo(x1, y2);
            path.lineTo(x2, y2);
            path.lineTo(midX, y2 - height);
        } else if (type == Mode.TRIANGLE_ISOSCELES) {
            path.moveTo(x1, y2);
            path.lineTo(x2, y2);
            path.lineTo(midX, y1);
        } else { // Scalene
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x1, y2);
        }

        path.close();
        return path;
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

        boolean isRectangle;
        RectF rect;

        boolean isTriangle;
        Path trianglePath;

        // Freehand
        DrawnShape(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
            this.isCircle = false;
            this.isRectangle = false;
            this.isTriangle = false;
        }

        // Circle
        DrawnShape(float cx, float cy, float radius, Paint paint) {
            this.cx = cx;
            this.cy = cy;
            this.radius = radius;
            this.paint = paint;
            this.isCircle = true;
            this.isRectangle = false;
            this.isTriangle = false;
        }

        // Rectangle
        DrawnShape(RectF rect, Paint paint) {
            this.rect = rect;
            this.paint = paint;
            this.isCircle = false;
            this.isRectangle = true;
            this.isTriangle = false;
        }

        // Triangle
        DrawnShape(Path trianglePath, Paint paint, boolean isTriangle) {
            this.trianglePath = trianglePath;
            this.paint = paint;
            this.isCircle = false;
            this.isRectangle = false;
            this.isTriangle = isTriangle;
        }
    }
}
