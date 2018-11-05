package amrish.ravidas.com.alpha;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class ViewIconTicTacToeAnimate extends View {
    private Paint paint;
    private ValueAnimator animator;
    private float fraction = 0.0f;
    private int canvasWidth = 0;
    private int canvasHeight = 0;

    public ViewIconTicTacToeAnimate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        startAnimator();
    }

    private final ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            fraction = animation.getAnimatedFraction();
            invalidate();
        }
    };

    private void startAnimator() {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setInterpolator(new DecelerateInterpolator(2.5f));
        animator.setDuration(5000);
        animator.addUpdateListener(listener);
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        canvasWidth = w;
        canvasHeight = h;
    }

    private class Position {
        int x;
        int y;
        ShapeType type;
        Position(int x, int y, ShapeType type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    enum ShapeType {
        CROSS, CIRCLE, LINE_HORIZONTAL, LINE_VERTICAL
    }

    /**
     * Returns the center point in the 3x3 grid for positioning the game drawables
     */
    private Iterable<Position> getMidPoints() {
        List<Position> results = new ArrayList<>();
        final int sizeOfBox = canvasWidth / 3;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                final Position postn = new Position(
                        (j * sizeOfBox) - (sizeOfBox / 2),
                        (i * sizeOfBox) - (sizeOfBox / 2),
                        i == j ? ShapeType.CROSS : ShapeType.CIRCLE);
                results.add(postn);
            }
        }
        results.add(new Position(canvasWidth/2, sizeOfBox, ShapeType.LINE_HORIZONTAL));
        results.add(new Position(canvasWidth/2, 2 * sizeOfBox, ShapeType.LINE_HORIZONTAL));

        results.add(new Position(sizeOfBox, canvasWidth/2, ShapeType.LINE_VERTICAL));
        results.add(new Position(2 * sizeOfBox, canvasWidth/2, ShapeType.LINE_VERTICAL));

        return results;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvasHeight != canvasWidth) {
            throw new RuntimeException("Layout only works when width == height");
        }
        for (Position postn : getMidPoints()) {
            if (postn.type.equals(ShapeType.CROSS)) {
                PathUtils.drawCrossMark(canvas, paint, postn.x, postn.y, (canvasWidth / 5) * fraction);
            } else if (postn.type.equals(ShapeType.CIRCLE)) {
                PathUtils.drawCircle(canvas, paint, postn.x, postn.y, (canvasWidth / 15) * fraction);
            } else if (postn.type.equals(ShapeType.LINE_HORIZONTAL)) {
                PathUtils.drawHorizontalLine(canvas, paint, postn.x, postn.y, canvasWidth * fraction);
            } else if (postn.type.equals(ShapeType.LINE_VERTICAL)) {
                PathUtils.drawVerticalLine(canvas, paint, postn.x, postn.y, canvasWidth * fraction);
            }
        }
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF")); //EE82EE
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }
}
