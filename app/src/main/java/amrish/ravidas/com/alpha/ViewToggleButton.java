package amrish.ravidas.com.alpha;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewToggleButton extends FrameLayout {

    @BindView(R.id.buttonText)
    TextView buttonText;
    private final String mText;
    private boolean mIsSelected;
    private int mCanvasWidth = 0;
    private int mCanvasHeight = 0;
    private Paint mWhiteStokePaint, mWhiteFillPaint, mFillPaint;
    private RectF mRectFLeftArcBoundaries, mRectFRightArcBoundaries;
    private int mDefaultColor = getResources().getColor(R.color.colorWhite);
    private int mSelectedColor = getResources().getColor(R.color.colorHotPink);
    private final Path mPath = new Path();

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
        buttonText.setText(mText);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mCanvasWidth = w;
        mCanvasHeight = h;
        if (mCanvasHeight > 0 && mCanvasWidth > 0) {
            mRectFLeftArcBoundaries = new RectF(mCanvasWidth * 0.70f, 12f, mCanvasWidth - 12, mCanvasHeight - 12);
            mRectFRightArcBoundaries = new RectF(12, 12, mCanvasWidth * 0.30f, mCanvasHeight - 12);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIsSelected) {
            drawSelectedPath(canvas);
        } else {
            drawUnSelectedPath(canvas);
        }
    }

    private void drawSelectedPath(Canvas canvas) {
        mPath.reset();

        // Top border
        mPath.moveTo(mCanvasWidth * 0.15f, 12f);
        mPath.lineTo(mCanvasWidth * 0.85f, 12f);

        // Right arc
        mPath.arcTo(mRectFLeftArcBoundaries, 270, 180);

        // Bottom border
        mPath.moveTo(mCanvasWidth * 0.85f, mCanvasHeight - 12);
        mPath.lineTo(mCanvasWidth * 0.15f, mCanvasHeight - 12);

        // Left arc
        mPath.arcTo(mRectFRightArcBoundaries, 90, 180);
        canvas.drawPath(mPath, mFillPaint);

        // Outer circle
        mPath.reset();
        mPath.addCircle(mCanvasWidth * 0.20f, mCanvasHeight / 2, mCanvasHeight / 5, Path.Direction.CW);
        canvas.drawPath(mPath, mWhiteStokePaint);

        mPath.reset();
        mPath.addCircle(mCanvasWidth * 0.20f, mCanvasHeight / 2, mCanvasHeight / 12, Path.Direction.CW);
        canvas.drawPath(mPath, mWhiteFillPaint);
    }

    private void drawUnSelectedPath(Canvas canvas) {
        mPath.reset();

        // Top border
        mPath.moveTo(mCanvasWidth * 0.15f, 12f);
        mPath.lineTo(mCanvasWidth * 0.85f, 12f);

        // Right arc
        mPath.arcTo(mRectFLeftArcBoundaries, 270, 180);

        // Bottom border
        mPath.moveTo(mCanvasWidth * 0.85f, mCanvasHeight - 12);
        mPath.lineTo(mCanvasWidth * 0.15f, mCanvasHeight - 12);

        // Left arc
        mPath.arcTo(mRectFRightArcBoundaries, 90, 180);

        // Outer circle
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

        buttonText.setTextColor(mDefaultColor);
    }
}
