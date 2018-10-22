package com.example.ducvu212.demomvvm.screen.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.ducvu212.demomvvm.data.model.CustomPath;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/11.
 */
public class DrawableView extends View {
    public int mWidth;
    public int mHeight;
    private Path mDrawPath;
    private Paint mDrawPaint;
    private Canvas mDrawCanvas;
    private boolean mIsEditable;
    private List<CustomPath> mPaths;
    private List<CustomPath> undonePaths = new ArrayList<>();
    private int mPaintColor = Color.WHITE;
    private Bitmap mBitmapMaster;

    public DrawableView(Context context) {
        super(context);
        mPaths = new ArrayList<>();
    }

    public DrawableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaths = new ArrayList<>();
        setupDrawing();
    }

    public DrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPaintColor(int color) {
        mPaintColor = color;
        mDrawPaint.setColor(mPaintColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        Bitmap tempBitmap = EditActivity.sBitmap;
        Bitmap.Config config;
        if (tempBitmap.getConfig() != null) {
            config = tempBitmap.getConfig();
        } else {
            config = Bitmap.Config.ARGB_8888;
        }
        mBitmapMaster = Bitmap.createBitmap(tempBitmap.getWidth(), tempBitmap.getHeight(), config);
        mDrawCanvas = new Canvas(mBitmapMaster);
        mDrawCanvas.drawBitmap(tempBitmap, 0, 0, null);
    }

    private void setupDrawing() {
        setDrawingCacheEnabled(true);
        mDrawPath = new Path();
        mDrawPaint = new Paint();
        mDrawPaint.setColor(mPaintColor);
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setDither(true);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawPaint.setStrokeWidth(10);
    }

    public void setDrawingEnabled(boolean isEditable) {
        this.mIsEditable = isEditable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (CustomPath p : mPaths) {
            mDrawPaint.setColor(p.getColor());
            canvas.drawPath(p.getPath(), mDrawPaint);
        }
        canvas.drawPath(mDrawPath, mDrawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsEditable) {
            float touchX = event.getX();
            float touchY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    undonePaths.clear();
                    mDrawPath.reset();
                    mDrawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mDrawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    mDrawPath.lineTo(touchX, touchY);
                    mDrawCanvas.drawPath(mDrawPath, mDrawPaint);
                    mPaths.add(
                            new CustomPath.Builder().mPath(mDrawPath).mColor(mPaintColor).build());
                    mDrawPath = new Path();
                    break;
                default:
                    return false;
            }
        } else {
            return false;
        }
        invalidate();
        return true;
    }

    public void clearCanvas() {
        mDrawPath.reset();
        mDrawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void onClickUndo() {
        if (mPaths.size() > 0) {
            undonePaths.add(mPaths.remove(mPaths.size() - 1));
            invalidate();
        }
    }

    public void onClickRedo() {
        if (undonePaths.size() > 0) {
            mPaths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
    }

    public Bitmap getBitmapMaster() {
        return mBitmapMaster;
    }
}
