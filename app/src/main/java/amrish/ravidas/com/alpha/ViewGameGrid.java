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
    private final float[] mRangeX = new float[3];
    private final float[] mRangeY = new float[3];


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
        mRangeX[0] = w / 3;
        mRangeX[1] = w * 2/3;
        mRangeX[2] = w;
        mRangeY[0] = h / 3;
        mRangeY[1] = h * 2/3;
        mRangeY[2] = h;
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

    /**
     * Given a position in the grid which is virtually divided into 9 section (nonant). This function
     * returns the index of the section in which the point would belong to. The index starts from top
     * left corner and increments in steps. For e.g. Position 0x0 is 0, 1x0 is 3, 2x2 is 8
     */
    public int getNonantPosition(float x, float y) {
        int row = -1, col = -1, i = 0, j = 0;
        while (i < 3) {
            if (x < mRangeX[i]) {
                col = i;
                break;
            }
            i++;
        }
        while (j < 3) {
            if (y < mRangeY[j]) {
                row = j;
                break;
            }
            j++;
        }
        return (row >= 0 && col >= 0) ? row * 3 + col : -1;
    }
}
