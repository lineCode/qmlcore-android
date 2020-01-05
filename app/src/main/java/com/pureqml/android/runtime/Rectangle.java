package com.pureqml.android.runtime;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.pureqml.android.IExecutionEnvironment;

import static com.pureqml.android.runtime.TypeConverter.*;

public final class Rectangle extends Element {
    private final static String TAG = "rt.Rectangle";
    private Paint   _background;
    private Paint   _border;
    int             _radius;

    private void setupPaint(Paint paint) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
    }

    public Rectangle(IExecutionEnvironment env) {
        super(env);
        _background = new Paint(Paint.ANTI_ALIAS_FLAG);
        _background.setStyle(Paint.Style.FILL);
        setupPaint(_background);
    }

    Paint getBorder() {
        if (_border == null) {
            _border = new Paint(Paint.ANTI_ALIAS_FLAG);
            _border.setStyle(Paint.Style.STROKE);
            setupPaint(_border);
        }
        return _border;
    }

    protected void setStyle(String name, Object value) throws Exception {
        switch(name) {
            case "background-color":    _background.setColor(toColor((String)value)); break;
            case "border-color":        getBorder().setColor(toColor((String)value)); break;
            case "border-width":        getBorder().setStrokeWidth(toInteger(value)); break;
            case "border-radius":       _radius = toInteger(value); break;
            default:
                super.setStyle(name, value); return;
        }
        update();
    }

    @Override
    public void paint(PaintState state) {
        beginPaint();

        Canvas canvas = state.canvas;
        float opacity = state.opacity;
        Rect rect = translateRect(_rect, state.baseX, state.baseY);
        if (_radius > 0) {
            canvas.drawRoundRect(new RectF(rect), _radius, _radius, patchAlpha(_background, state.opacity));
        } else {
            canvas.drawRect(rect, patchAlpha(_background, state.opacity));
        }

        if (_border != null) {
            if (_radius > 0) {
                canvas.drawRoundRect(new RectF(rect), _radius, _radius, patchAlpha(_border, state.opacity));
            } else {
                canvas.drawRect(rect, patchAlpha(_border, state.opacity));
            }
        }
        _lastRect.union(rect);
        paintChildren(state);

        endPaint();
    }
}
