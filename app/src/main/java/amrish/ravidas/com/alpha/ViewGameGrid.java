package amrish.ravidas.com.alpha;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class ViewGameGrid extends View {
    private Paint mPaint;
    private ValueAnimator mAnimator;
    private float mFraction = 0.0f;
    private int mCanvasWidth = 0;
    private int mCanvasHeight = 0;

    private final ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFraction = animation.getAnimatedFraction();
            invalidate();
        }
    };

    public ViewGameGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
        startAnimator();
    }

    private void startAnimator() {
        mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mAnimator.setInterpolator(new DecelerateInterpolator(2.5f));
        mAnimator.setDuration(2000);
        mAnimator.addUpdateListener(listener);
        mAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mCanvasWidth = w;
        mCanvasHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        PathUtils.drawHorizontalLine(canvas, mPaint, mCanvasWidth / 2, mCanvasHeight / 3, mCanvasWidth * mFraction);
        PathUtils.drawHorizontalLine(canvas, mPaint, mCanvasWidth / 2, mCanvasHeight * 2 / 3, mCanvasWidth * mFraction);
        PathUtils.drawVerticalLine(canvas, mPaint, mCanvasWidth / 3, mCanvasHeight / 2, mCanvasHeight * mFraction * 0.95);
        PathUtils.drawVerticalLine(canvas, mPaint, mCanvasWidth * 2/ 3, mCanvasHeight / 2, mCanvasHeight * mFraction * 0.95);
    }

    // Setup mPaint with color and stroke styles
    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#FFDCDADB")); //EE82EE
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
