import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class GraphWindow {
    private final BufferedImage img;

    private final int width;
    private final int height;

    private double x_min;
    private double x_max;

    private Double y_min;
    private Double y_max;

    private double resX;
    private double resY;

    Font font_digits;

    private List<Graph.Point> points = new ArrayList<>();
    private List<ScreenPoint> graphPoints = new ArrayList<>();

    private final Graphics2D g;

    public GraphWindow(Graph graph, int width, int height) {
        this.width = width;
        this.height = height;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();

        try {
            font_digits = Font.createFont(Font.TRUETYPE_FONT, new File("Cambria.ttf"));
            g.setFont(font_digits.deriveFont(20f));
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateGraphParams(graph);
    }

    public BufferedImage getImg() {
        return img;
    }

    private void updateGraphParams(Graph graph) {
        this.x_min = graph.getX_min();
        this.x_max = graph.getX_max();
        this.y_min = graph.getY_min();
        this.y_max = graph.getY_max();
        this.points = graph.getRealPoints();
    }

    public void calculateScreenPoints() {
        resX = (x_max - x_min) / (width - 1);
        resY = (y_max - y_min) / (height - 1);

        int x;
        int y;

        for (Graph.Point p : points) {
            x = getScreenX(p.getX());

            if (p.isReal() && p.getY() >= y_min && p.getY() <= y_max) {
                y = getScreenY(p.getY());
                graphPoints.add(new ScreenPoint(x, y, false));
            } else {
                graphPoints.add(new ScreenPoint(x, true));
            }
        }
    }

    public void drawGraph() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);

        ScreenPoint p_last = graphPoints.get(0);
        ScreenPoint p_current;

        for (int i = 1; i < graphPoints.size(); i++) {
            p_current = graphPoints.get(i);

            if (!p_last.isEmpty() && !p_current.isEmpty()) {
                g.drawLine(p_last.getX(), p_last.getY(), p_current.getX(), p_current.getY());
            }

            p_last = p_current;
        }

        drawAxes();
        drawDigitsStrokes();
    }

    private void drawAxes() {
        int x_axis = getScreenX(0);
        int y_axis = getScreenY(0);

        g.drawLine(x_axis, 0, x_axis, height);  // Vertical axis
        g.drawLine(x_axis, 0, x_axis - 6, 15);
        g.drawLine(x_axis, 0, x_axis + 6, 15);

        g.drawLine(0, y_axis, width, y_axis);   // Horizontal axis
        g.drawLine(width, y_axis, width - 15, y_axis + 6);
        g.drawLine(width, y_axis, width - 15, y_axis - 6);
    }

    private void drawDigitsStrokes() {
        double x_step = (x_max - x_min) / 17;
        for (double i = x_min + x_step; i <= x_max; i += x_step) {
            if (Math.abs(i) < x_step * 0.001) continue;

            g.drawLine(getScreenX(i), getScreenY(0) - 5, getScreenX(i), getScreenY(0) + 5);

            BigDecimal bd = new BigDecimal(i);
            bd = bd.round(new MathContext(2));
            String value = Double.toString(bd.doubleValue());

            g.drawString(value, getScreenX(i) - 16, getScreenY(0) + 22);
        }

        double y_step = (y_max - y_min) / 10;
        for (double i = y_min + y_step; i <= y_max - y_step; i += y_step) {
            if (Math.abs(i) < y_step * 0.001) continue;

            g.drawLine(getScreenX(0) - 5, getScreenY(i), getScreenX(0) + 5, getScreenY(i));

            BigDecimal bd = new BigDecimal(i);
            bd = bd.round(new MathContext(2));
            String value = Double.toString(bd.doubleValue());

            g.drawString(value, getScreenX(0) + 9, getScreenY(i) + 7);
        }
    }

    private int getScreenX(double x) {
        return (int) ((x - x_min) / resX);
    }

    private int getScreenY(double y) {
        return (int) (height - (y - y_min) / resY - 1);
    }

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
