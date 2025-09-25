package com.example.paint;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class Efface extends Crayon {
    public Efface() {
        super();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void onDrawPreview(Canvas canvas) {
        // Just use Crayon behavior
        super.onDrawPreview(canvas);
    }
}
