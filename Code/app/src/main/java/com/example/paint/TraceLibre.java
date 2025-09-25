package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
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

    private Outils currentTool;
    private final List<DrawnShape> shapes; // All saved shapes
    private int backgroundColor;

    public TraceLibre(Context context) {
        super(context);
        shapes = new ArrayList<>();
        backgroundColor = 0xFFFFFFFF; // White
        setBackgroundColor(backgroundColor);
    }

    public TraceLibre(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        shapes = new ArrayList<>();
        backgroundColor = 0xFFFFFFFF;
        setBackgroundColor(backgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw finished shapes
        for (DrawnShape shape : shapes) {
            shape.draw(canvas);
        }

        // Let current tool draw preview
        if (currentTool != null) {
            currentTool.onDrawPreview(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (currentTool != null) {
            return currentTool.onTouchEvent(event, this);
        }
        return false;
    }

    // --- API ---
    public void setTool(Outils tool) {
        this.currentTool = tool;
    }

    public void setStrokeWidth(float width) {
        if (currentTool != null) {
            currentTool.setStrokeWidth(width);
        }
    }

    public void setColor(int color) {
        if (currentTool != null) {
            currentTool.setColor(color);
        }
    }

    public void clearCanvas() {
        shapes.clear();
        invalidate();
    }

    public void setBackgroundColorCustom(int color) {
        backgroundColor = color;
        setBackgroundColor(color);
        invalidate();
    }

    public void addShape(DrawnShape shape) {
        shapes.add(shape);
    }

    // --- Inner Shape wrapper ---
    public static class DrawnShape {
        private Path path;
        private RectF rect;
        private float cx, cy, radius;
        private Paint paint;
        private ShapeType type;

        enum ShapeType { PATH, CIRCLE, RECTANGLE }

        // Path
        public DrawnShape(Path path, Paint paint) {
            this.type = ShapeType.PATH;
            this.path = path;
            this.paint = paint;
        }

        // Circle
        public DrawnShape(float cx, float cy, float radius, Paint paint) {
            this.type = ShapeType.CIRCLE;
            this.cx = cx;
            this.cy = cy;
            this.radius = radius;
            this.paint = paint;
        }

        // Rectangle
        public DrawnShape(RectF rect, Paint paint) {
            this.type = ShapeType.RECTANGLE;
            this.rect = rect;
            this.paint = paint;
        }

        public void draw(Canvas canvas) {
            switch (type) {
                case PATH:
                    canvas.drawPath(path, paint);
                    break;
                case CIRCLE:
                    canvas.drawCircle(cx, cy, radius, paint);
                    break;
                case RECTANGLE:
                    canvas.drawRect(rect, paint);
                    break;
            }
        }
    }
}
