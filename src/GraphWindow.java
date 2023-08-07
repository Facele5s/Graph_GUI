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

    public GraphWindow(int width, int height, double x_min, double x_max) {
        this.width = width;
        this.height = height;
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = Double.NaN;
        this.y_max = Double.NaN;
    }

    public GraphWindow(int width, int height, double x_min, double x_max, double y_min, double y_max) {
        this(width, height, x_min, x_max);
        this.y_min = y_min;
        this.y_max = y_max;
    }

    public void calculateGraph(List<Graph.RealPoint> realPoints) throws NanException {
        if(y_min.equals(Double.NaN)) {
            for(int i = 0; i < realPoints.size(); i++) {
                if(realPoints.get(i).isReal()) {
                    y_min = y_max = realPoints.get(i).getY();
                    break;
                }
            }
            if(y_min.equals(Double.NaN)) throw new NanException("The function doesn't have real values");

            for(Graph.RealPoint p: realPoints) {
                if(p.isReal()) {
                    double y = p.getY();
                    if(y < y_min) y_min = y;
                    if(y > y_max) y_max = y;
                }
            }
        }

        for(Graph.RealPoint p: realPoints) {
            double y = p.getY();
            if(p.isReal() && y <= y_max && y>= y_min) {
                ScreenPoint point = new ScreenPoint(p.getX(), y);
                graphPoints.add(point);
            }
        }
    }

    public void drawGraph() {

    }

    public void drawAxes() {

    }

    static class ScreenPoint {
        private double x;
        private double y;

        public ScreenPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
