package amrish.ravidas.com.alpha;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

class PathUtils {

    static void drawCircle(Canvas canvas, Path path, Paint paint, float x, float y, float radius) {
        path.moveTo(x, y);
        path.addCircle(x, y, radius, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    /**
     * Draw a vertical line of provided length centered at x and y
     */
    static void drawVerticalLine(Canvas canvas, Path path, Paint paint, float x, float y, double length) {
        canvas.drawPath(createLine(path, x, y, length / 2, 90), paint);
        canvas.drawPath(createLine(path, x, y, length / 2, 270), paint);
    }

    /**
     * Draw a horizontal line of provided length centered at x and y
     */
    static void drawHorizontalLine(Canvas canvas, Path path, Paint paint, float x, float y, double length) {
        canvas.drawPath(createLine(path, x, y, length / 2, 0), paint);
        canvas.drawPath(createLine(path, x, y, length / 2, 180), paint);
    }

    /**
     * Draw cross mark centered at x and y args and provided length
     */
    static void drawCrossMark(Canvas canvas, Path path, Paint paint, float x, float y, double length) {
        canvas.drawPath(createLine(path, x, y, length / 2, 45), paint);
        canvas.drawPath(createLine(path, x, y, length / 2, 135), paint);
        canvas.drawPath(createLine(path, x, y, length / 2, 225), paint);
        canvas.drawPath(createLine(path, x, y, length / 2, 315), paint);
    }


    static Path createLine(Path path, float x, float y, double length, double angleDegree) {
        path.moveTo(x, y);
        path.lineTo(
                x + (float) (length * Math.cos(Math.toRadians(angleDegree))),
                y + (float) (length * Math.sin(Math.toRadians(angleDegree))));
        return path;
    }

    /**
     * Create a polygon by specifying the total number of sides to it
     */
    static Path createPolygon(float x, float y, int sides, float radius) {
        // TODO https://medium.com/androiddevelopers/playing-with-paths-3fbc679a6f77
        throw new RuntimeException("Not implemented");
    }

    /**
     * Create a polygon path by specifying the angle between two sides. The path will stop once
     * the sum of angles is >= 360
     */
    static Path createPolygon(float x, float y, float angle, float radius) {
        // TODO https://medium.com/androiddevelopers/playing-with-paths-3fbc679a6f77
        throw new RuntimeException("Not implemented");
    }
}
