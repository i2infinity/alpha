package amrish.ravidas.com.alpha;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewToggleButton extends FrameLayout {

    @BindView(R.id.buttonText)
    TextView mButtonText;

    @BindView(R.id.buttonLayout)
    ConstraintLayout mConstraintLayout;

    private final float[] mLastTouchDownXY = new float[2];
    private final String mText;
    private boolean mIsSelected;
    private int mCanvasWidth = 0;
    private int mCanvasHeight = 0;
    private Paint mWhiteStokePaint, mWhiteFillPaint, mFillPaint, mOnClickPaint;
    private int mDefaultColor = getResources().getColor(R.color.colorWhite);
    private int mOnTouchColor = getResources().getColor(R.color.colorWhite);
    private int mSelectedColor = getResources().getColor(R.color.colorHotPink);
    private final Path mPath = new Path();
    private final Path mPathTrim = new Path();
    private float mFraction = 0.0f;
    private ValueAnimator mAnimator;
    private RectF mPillRectangle;

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // save the X,Y coordinates
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mLastTouchDownXY[0] = event.getX();
                mLastTouchDownXY[1] = event.getY();
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };

    private final ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFraction = animation.getAnimatedFraction();
            invalidate();
            requestLayout();
        }
    };

    public ViewToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ViewToggleButton,
                0, 0);

        try {
            mText = a.getString(R.styleable.ViewToggleButton_text);
            mIsSelected = a.getBoolean(R.styleable.ViewToggleButton_selected, false);
        } finally {
            a.recycle();
        }

        init();
        setupPaint();
        this.setWillNotDraw(false);
    }

    private void init() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.toggle_button, this, true);
        ButterKnife.bind(this, view);
        mButtonText.setText(mText);
        mConstraintLayout.setOnTouchListener(touchListener);
    }

    @OnClick(R.id.buttonLayout)
    public void onLayoutClick(View view) {
        startAnimator();
    }

    private void startAnimator() {
        // TODO - Handle on-click events when previous one is in progress
        mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mAnimator.setInterpolator(new DecelerateInterpolator(1.5f));
        mAnimator.setDuration(500);
        mAnimator.addUpdateListener(listener);
        mAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mCanvasWidth = w;
        mCanvasHeight = h;
        if (mCanvasHeight > 0 && mCanvasWidth > 0) {
            mPillRectangle = new RectF(3.0f, mCanvasHeight - 3, mCanvasWidth - 3 , 3);
            final RectF mFullRectangle = new RectF(0, mCanvasHeight, mCanvasWidth, 0);

            mPathTrim.addRoundRect(mFullRectangle, 0, 0, Path.Direction.CW);
            Path pathRoundedPill = new Path();
            pathRoundedPill.addRoundRect(mFullRectangle, mCanvasHeight, mCanvasHeight, Path.Direction.CW);
            mPathTrim.op(pathRoundedPill, Path.Op.DIFFERENCE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIsSelected) {
            drawSelectedPath(canvas);
        } else {
            drawNotSelectedPath(canvas);
//            canvas.drawPath(mPathTrim, mFillPaint);
        }

        if (mFraction > 0 && mFraction < 1.0f) {
            mPath.reset();
            mPath.addCircle(mLastTouchDownXY[0], mLastTouchDownXY[1], mCanvasWidth * mFraction, Path.Direction.CW);
            mPath.op(mPathTrim, Path.Op.DIFFERENCE);
            canvas.drawPath(mPath, mOnClickPaint);
        }
    }

    private void drawSelectedPath(Canvas canvas) {
        mPath.reset();
        mPath.addRoundRect(mPillRectangle, mCanvasHeight, mCanvasHeight, Path.Direction.CW);
        canvas.drawPath(mPath, mFillPaint);

        // Outer circle
        mPath.reset();
        mPath.addCircle(mCanvasWidth * 0.20f, mCanvasHeight / 2, mCanvasHeight / 5, Path.Direction.CW);
        canvas.drawPath(mPath, mWhiteStokePaint);

        mPath.reset();
        mPath.addCircle(mCanvasWidth * 0.20f, mCanvasHeight / 2, mCanvasHeight / 12, Path.Direction.CW);
        canvas.drawPath(mPath, mWhiteFillPaint);
    }

    private void drawNotSelectedPath(Canvas canvas) {
        mPath.reset();
        mPath.addRoundRect(mPillRectangle, mCanvasHeight, mCanvasHeight, Path.Direction.CW);
        mPath.addCircle(mCanvasWidth * 0.20f, mCanvasHeight / 2, mCanvasHeight / 5, Path.Direction.CW);
        canvas.drawPath(mPath, mWhiteStokePaint);
    }


    // Setup mWhiteStokePaint with color and stroke styles
    private void setupPaint() {
        mWhiteStokePaint = new Paint();
        mWhiteStokePaint.setColor(mDefaultColor);
        mWhiteStokePaint.setAntiAlias(true);
        mWhiteStokePaint.setStrokeWidth(6);
        mWhiteStokePaint.setStyle(Paint.Style.STROKE);
        mWhiteStokePaint.setStrokeJoin(Paint.Join.ROUND);
        mWhiteStokePaint.setStrokeCap(Paint.Cap.ROUND);

        mWhiteFillPaint = new Paint();
        mWhiteFillPaint.setColor(mDefaultColor);
        mWhiteFillPaint.setAntiAlias(true);
        mWhiteFillPaint.setStrokeWidth(6);
        mWhiteFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWhiteFillPaint.setStrokeJoin(Paint.Join.ROUND);
        mWhiteFillPaint.setStrokeCap(Paint.Cap.ROUND);

        mFillPaint = new Paint();
        mFillPaint.setColor(mSelectedColor);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setStrokeWidth(6);
        mFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFillPaint.setStrokeJoin(Paint.Join.ROUND);
        mFillPaint.setStrokeCap(Paint.Cap.ROUND);

        mOnClickPaint = new Paint();
        mOnClickPaint.setColor(mOnTouchColor);
        mOnClickPaint.setAlpha(100);
        mOnClickPaint.setAntiAlias(true);
        mOnClickPaint.setStrokeWidth(6);
        mOnClickPaint.setStyle(Paint.Style.FILL);
        mOnClickPaint.setStrokeJoin(Paint.Join.ROUND);
        mOnClickPaint.setStrokeCap(Paint.Cap.ROUND);

        mButtonText.setTextColor(mDefaultColor);
    }
}
