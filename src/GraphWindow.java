import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class GraphWindow {
    // Class which represents a display

    private final BufferedImage img;    // Image with gtaph

    private final int width;
    private final int height;

    private final double x_min;
    private final double x_max;

    private final Double y_min;
    private final Double y_max;

    private double resX;    // x rendering resolution
    private double resY;    // y rendering resolution

    Font font_digits;

    private final List<Graph.Point> points;   // Real points (some may be empty)
    private final List<ScreenPoint> graphPoints = new ArrayList<>();  // Screen points (converted real points)

    private final Graphics2D g;

    // Constructor
    public GraphWindow(Graph graph, int width, int height) {
        this.width = width;
        this.height = height;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();

        try {   // Load font from file
            font_digits = Font.createFont(Font.TRUETYPE_FONT, new File("Cambria.ttf"));
            g.setFont(font_digits.deriveFont(20f));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.x_min = graph.getX_min();
        this.x_max = graph.getX_max();
        this.y_min = graph.getY_min();
        this.y_max = graph.getY_max();
        this.points = graph.getRealPoints();
    }

    public BufferedImage getImg() {
        return img;
    }

    // A method which converts real points into screen points
    public void calculateScreenPoints() {
        resX = (x_max - x_min) / (width - 1);   // x steps per pixel resolution
        resY = (y_max - y_min) / (height - 1);  // y steps per pixel resolution

        int x;
        int y;

        // For all real points
        for (Graph.Point p : points) {
            x = getScreenX(p.getX());   // Convert real x value into x coordinate on display

            // If real point is defined and y is in accepted range
            if (p.isReal() && p.getY() >= y_min && p.getY() <= y_max) {
                y = getScreenY(p.getY());   // Convert real y value into y coordinate on display
                graphPoints.add(new ScreenPoint(x, y, false)); // Create screen point
            } else {
                graphPoints.add(new ScreenPoint(x, true));  // Else create break point
            }
        }
    }

    public void drawGraph() {   // Graph rendering
        g.setColor(Color.BLACK);    // Black background
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);

        ScreenPoint p_last = graphPoints.get(0);    //Last reviewed point
        ScreenPoint p_current;  // Current point

        // For all screen points
        for (int i = 1; i < graphPoints.size(); i++) {
            p_current = graphPoints.get(i); // Update current point

            // If both: last and current points are not empty, we connect them by line
            if (!p_last.isEmpty() && !p_current.isEmpty()) {
                g.drawLine(p_last.getX(), p_last.getY(), p_current.getX(), p_current.getY());
            }

            p_last = p_current; // Update last point
        }

        drawAxes(); // Axes rendering
        drawDigitsStrokes();    // Digits and strokes rendering
    }

    private void drawAxes() {   // Axes rendering method
        int y_axis = getScreenX(0); // coordinate of y-axis on screen
        int x_axis = getScreenY(0); // coordinate of x-axis on screen

        g.drawLine(y_axis, 0, y_axis, height);  // Vertical axis
        // Arrowhead
        g.drawLine(y_axis, 0, y_axis - 6, 15);
        g.drawLine(y_axis, 0, y_axis + 6, 15);

        g.drawLine(0, x_axis, width, x_axis);   // Horizontal axis
        // Arrowhead
        g.drawLine(width, x_axis, width - 15, x_axis + 6);
        g.drawLine(width, x_axis, width - 15, x_axis - 6);
    }

    private void drawDigitsStrokes() {  // Digits and strokes rendering method
        double x_step = (x_max - x_min) / 17;   // x step for 16 values on x-axis
        for (double i = x_min + x_step; i <= x_max; i += x_step) {
            // We don't render values that are very close to zero in contrast to other values
            if (Math.abs(i) < x_step * 0.001) continue;

            // Draw a stroke
            g.drawLine(getScreenX(i), getScreenY(0) - 5, getScreenX(i), getScreenY(0) + 5);

            // Value rounding
            BigDecimal bd = new BigDecimal(i);
            bd = bd.round(new MathContext(2));
            String value = Double.toString(bd.doubleValue());

            // Draw a value
            g.drawString(value, getScreenX(i) - 16, getScreenY(0) + 22);
        }

        double y_step = (y_max - y_min) / 10;   // y step for 9 values on y-axis
        for (double i = y_min + y_step; i <= y_max - y_step; i += y_step) {
            // We don't render values that are very close to zero in contrast to other values
            if (Math.abs(i) < y_step * 0.001) continue;

            // Draw a stroke
            g.drawLine(getScreenX(0) - 5, getScreenY(i), getScreenX(0) + 5, getScreenY(i));

            // Value rounding
            BigDecimal bd = new BigDecimal(i);
            bd = bd.round(new MathContext(2));
            String value = Double.toString(bd.doubleValue());

            // Draw a value
            g.drawString(value, getScreenX(0) + 9, getScreenY(i) + 7);
        }
    }

    // A method which converts real x value into x coordinate on screen
    private int getScreenX(double x) {
        return (int) ((x - x_min) / resX);
    }

    // A method which converts real y value into y coordinate on screen
    private int getScreenY(double y) {
        return (int) (height - (y - y_min) / resY - 1);
    }

    // Point with integer x and y coordinates. Screen points. Can be empty (breakpoint)
    static class ScreenPoint {
        private final int x;
        private final int y;
        private final boolean empty;

        public ScreenPoint(int x, int y, boolean empty) {
            this.x = x;
            this.y = y;
            this.empty = empty;
        }

        public ScreenPoint(int x, boolean empty) {
            this.x = x;
            this.y = 0;
            this.empty = empty;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isEmpty() {
            return empty;
        }
    }
}
