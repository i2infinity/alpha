package amrish.ravidas.com.alpha;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class ViewTicTacToeBlock extends View {

    enum BlockType {
        NONE(0),
        CROSS(1),
        CIRCLE(2);

        private final int mValue;
        private final static SparseArray<BlockType> mMap = new SparseArray<>();


        BlockType(final int value) {
            mValue = value;
        }

        static {
            for (BlockType blockType : BlockType.values()) {
                mMap.put(blockType.mValue, blockType);
            }
        }

        public static BlockType valueOf(int type) {
            return mMap.get(type);
        }

        public int getValue() { return mValue; }
    }

    private BlockType mBlockType;
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

    public ViewTicTacToeBlock(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ViewTicTacToeBlock,
                0, 0);

        try {
            mBlockType = BlockType.valueOf(a.getInteger(R.styleable.ViewTicTacToeBlock_blockType, 0));
        } finally {
            a.recycle();
        }
        setupPaint();
        startAnimator();
    }

    public void setType(BlockType blockType) {
        mBlockType = blockType;
    }

    public void startAnimator() {
        if (mBlockType.equals(BlockType.NONE)) {
            return;
        }
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
        final float x = mCanvasWidth / 2;
        final float y = mCanvasHeight / 2;
        if (mBlockType.equals(BlockType.CROSS)) {
            PathUtils.drawCrossMark(canvas, mPaint, x, y, mCanvasWidth * 0.9 * mFraction);
        } else if (mBlockType.equals(BlockType.CIRCLE)) {
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setAlpha(100);
            PathUtils.drawCircle(canvas, mPaint, x, y, (float) (mCanvasWidth * 0.4 * mFraction));
            mPaint.setAlpha(255);
            mPaint.setStyle(Paint.Style.STROKE);
            PathUtils.drawCircle(canvas, mPaint, x, y, (float) (mCanvasWidth * 0.4 * mFraction));
        }
    }

    // Setup mPaint with color and stroke styles
    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
