import java.util.ArrayList;
import java.util.List;

public class GraphWindow {
    private int width;
    private int height;

    private double x_min;
    private double x_max;

    private Double y_min;
    private Double y_max;

    private List<ScreenPoint> graphPoints = new ArrayList<>();

    public GraphWindow(int width, int height, double x_min, double x_max, Double y_min, Double y_max) {
        this.width = width;
        this.height = height;
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
    }

    public void calculateScreenPoints(List<Graph.Point> points) {

    }

    public void drawGraph() {

    }

    public void drawAxes() {

    }

    static class ScreenPoint {
        private int x;
        private int y;

        public ScreenPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
