package amrish.ravidas.com.alpha;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class IconTicTacToeAnimate extends View {
    private final int paintColor = Color.BLACK;
    private Paint paint;
    private ValueAnimator animator;
    private float fraction = 0.0f;
    private int canvasWidth = 0;
    private int canvasHeight = 0;

    public IconTicTacToeAnimate(Context context, @Nullable AttributeSet attrs) {
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
        animator.setDuration(3000);
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

    // ...variables and setting up paint...
    // Let's draw three circles
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvasHeight != canvasWidth) {
            throw new RuntimeException("Layout only works when width == height");
        }
        paint.setColor(Color.parseColor("#FFFFFF")); //EE82EE
        for (Position postn : getMidPoints()) {
            if (postn.type.equals(ShapeType.CROSS)) {
                drawCrossMark(canvas, postn.x, postn.y, (canvasWidth / 5) * fraction);
            } else if (postn.type.equals(ShapeType.CIRCLE)) {
                drawCircle(canvas, postn.x, postn.y, (canvasWidth / 15) * fraction);
            } else if (postn.type.equals(ShapeType.LINE_HORIZONTAL)) {
                drawHorizontalLine(canvas, postn.x, postn.y, canvasWidth * fraction);
            } else if (postn.type.equals(ShapeType.LINE_VERTICAL)) {
                drawVerticalLine(canvas, postn.x, postn.y, canvasWidth * fraction);
            }
        }
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * Draw a vertical line of provided length centered at x and y
     */
    private void drawVerticalLine(Canvas canvas, float x, float y, double length) {
        canvas.drawPath(createLine(x, y, length / 2, 90), paint);
        canvas.drawPath(createLine(x, y, length / 2, 270), paint);
    }

    /**
     * Draw a horizontal line of provided length centered at x and y
     */
    private void drawHorizontalLine(Canvas canvas, float x, float y, double length) {
        canvas.drawPath(createLine(x, y, length / 2, 0), paint);
        canvas.drawPath(createLine(x, y, length / 2, 180), paint);
    }

    /**
     * Draw cross mark centered at x and y args and provided length
     */
    private void drawCrossMark(Canvas canvas, float x, float y, double length) {
        canvas.drawPath(createLine(x, y, length / 2, 45), paint);
        canvas.drawPath(createLine(x, y, length / 2, 135), paint);
        canvas.drawPath(createLine(x, y, length / 2, 225), paint);
        canvas.drawPath(createLine(x, y, length / 2, 315), paint);
    }

    private void drawCircle(Canvas canvas, float x, float y, float radius) {
        final Path path = new Path();
        path.moveTo(x, y);
        path.addCircle(x, y, radius, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    private Path createLine(float x, float y, double length, double angleDegree) {
        final Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(
                x + (float) (length * Math.cos(Math.toRadians(angleDegree))),
                y + (float) (length * Math.sin(Math.toRadians(angleDegree))));
        return path;
    }

    /**
     * Create a polygon by specifying the total number of sides to it
     */
    private Path createPolygon(float x, float y, int sides, float radius) {
        // TODO https://medium.com/androiddevelopers/playing-with-paths-3fbc679a6f77
        throw new RuntimeException("Not implemented");
    }

    /**
     * Create a polygon path by specifying the angle between two sides. The path will stop once
     * the sum of angles is >= 360
     */
    private Path createPolygon(float x, float y, float angle, float radius) {
        // TODO https://medium.com/androiddevelopers/playing-with-paths-3fbc679a6f77
        throw new RuntimeException("Not implemented");
    }

    /**
     * https://varun.ca/polar-coords/
     */
    private double getAngle(int sides) {
        return 360.0 / sides;
    }
}
