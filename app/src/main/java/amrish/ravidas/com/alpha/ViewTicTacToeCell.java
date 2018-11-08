package amrish.ravidas.com.alpha;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class ViewTicTacToeCell extends View {

    enum CellType {
        NONE(0),
        CROSS(1),
        CIRCLE(2),
        SOLID_CIRCLE(3);

        private final int mValue;
        private final static SparseArray<CellType> mMap = new SparseArray<>();


        CellType(final int value) {
            mValue = value;
        }

        static {
            for (CellType cellType : CellType.values()) {
                mMap.put(cellType.mValue, cellType);
            }
        }

        public static CellType valueOf(int type) {
            return mMap.get(type);
        }

        public int getValue() { return mValue; }
    }

    private final Path mPath;
    private CellType mCellType;
    private int mColor;
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
            requestLayout();
        }
    };

    public ViewTicTacToeCell(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ViewTicTacToeCell,
                0, 0);

        try {
            mCellType = CellType.valueOf(a.getInteger(R.styleable.ViewTicTacToeCell_cellType, 0));
            mColor = a.getResourceId(R.styleable.ViewTicTacToeCell_cellColor, R.color.colorWhite);
        } finally {
            a.recycle();
        }
        mPath = new Path();
        setupPaint();
        startAnimator();
    }

    public void setType(CellType cellType) {
        mCellType = cellType;
    }

    public void startAnimator() {
        if (mCellType.equals(CellType.NONE)) {
            invalidate();
            return;
        }
        mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mAnimator.setInterpolator(new DecelerateInterpolator(2.5f));
        mAnimator.setDuration(1000);
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
        mPath.reset();
        final float x = mCanvasWidth / 2;
        final float y = mCanvasHeight / 2;
        // TODO Do not create `new Path()` objects in onDraw(...)
        if (mCellType.equals(CellType.CROSS)) {
            PathUtils.drawCrossMark(canvas, mPath, mPaint, x, y, mCanvasWidth * 0.7 * mFraction);
        } else if (mCellType.equals(CellType.CIRCLE)) {
            // Draw the transparent fill
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setAlpha(100);
            PathUtils.drawCircle(canvas, mPath, mPaint, x, y, (float) (mCanvasWidth * 0.3 * mFraction));
            // Draw outer border
            mPaint.setAlpha(255);
            mPaint.setStyle(Paint.Style.STROKE);
            PathUtils.drawCircle(canvas, mPath, mPaint, x, y, (float) (mCanvasWidth * 0.3 * mFraction));
        } else if (mCellType.equals(CellType.SOLID_CIRCLE)) {
            // Draw fill
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setAlpha(255);
            PathUtils.drawCircle(canvas, mPath, mPaint, x, y, (float) (mCanvasWidth * 0.3 * mFraction));
        }
    }

    // Setup mPaint with color and stroke styles
    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(mColor));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
