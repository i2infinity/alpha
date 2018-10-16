package amrish.ravidas.com.alpha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class IconTicTacToeAnimate extends View {
    private final int paintColor = Color.BLACK;
    private float cx = (float) (1080 / 2);
    private float cy = (float) (1080 / 2);
    private Paint paint;

    public IconTicTacToeAnimate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    // ...variables and setting up paint...
    // Let's draw three circles
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.parseColor("#EE82EE"));
//        canvas.drawPath(createPath(2, 400.0f), paint);
        canvas.drawPath(createLine(cx, cy, 80, 45), paint);
        paint.setColor(Color.BLACK);
        canvas.drawPath(createLine(cx+50, cy+50, 80, 180), paint);
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private Path createLine(float x, float y, int length, int angleDegree) {
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Create a polygon path by specifying the angle between two sides. The path will stop once
     * the sum of angles is >= 360
     */
    private Path createPolygon(float x, float y, float angle, float radius) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * https://varun.ca/polar-coords/
     */
    private double getAngle(int sides) {
        return 360.0 / sides;
    }

    private Path createPath(int sides, float radius) {
        final Path path = new Path();
        final double angleRadians = 2.0 * Math.PI / sides;
        final double startAngleRadians = Math.PI / 2.0 + Math.toRadians(360.0 / (2 * sides));
        path.moveTo(cx + (float) (radius * Math.cos(startAngleRadians)),
                cy + (float) (radius * Math.sin(startAngleRadians)));

        for (int i = 1; i <= sides; i++) {
            path.lineTo(
                    cx + (float) (radius * Math.cos(startAngleRadians - angleRadians * i)),
                    cy + (float) (radius * Math.sin(startAngleRadians - angleRadians * i)));
        }

        return path;
    }
}
