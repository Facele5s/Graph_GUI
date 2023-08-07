import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int width;
    private int height;

    private double x_min;
    private double x_max;

    private List<RealPoint> realPoints = new ArrayList<>();

    public Graph(int width, int height, double x_min, double x_max) {
        this.width = width;
        this.height = height;
        this.x_min = x_min;
        this.x_max = x_max;
    }

    private double func(double x) {
        return 1 / x;
    }

    public void setX_min(int x_min) {
        this.x_min = x_min;
    }

    public void setX_max(int x_max) {
        this.x_max = x_max;
    }

    public List<RealPoint> getRealPoints() {
        return realPoints;
    }

    public void calculatePoints() {
        double resolution = (double) (x_max - x_min) / width;   // x steps per pixel
        Double y;
        RealPoint point;

        for(double x = x_min; x <= x_max; x+= resolution) {
            y = func(x);
            if(y.equals(Double.POSITIVE_INFINITY) || y.equals(Double.NEGATIVE_INFINITY) || y.equals(Double.NaN)) {
                point = new RealPoint(x, y, false);
            } else {
                point = new RealPoint(x, y, true);
            }

            realPoints.add(point);
        }
    }

    public GraphWindow createGraphWindow(int width, int height, double x_min, double x_max) {
        return new GraphWindow(width, height, x_min, x_max);
    }

    public GraphWindow createGraphWindow(int width, int height, double x_min, double x_max, double y_min, double y_max) {
        return new GraphWindow(width, height, x_min, x_max, y_min, y_max);
    }

    static class RealPoint {
        private double x;
        private double y;
        private boolean real;

        public RealPoint(double x, double y, boolean real) {
            this.x = x;
            this.y = y;
            this.real = real;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public boolean isReal() {
            return real;
        }

        @Override
        public String toString() {
            String type_info = real ? "real" : "NaN or inf";

            return String.format("RealPoint[x: %f, y: %f, type: %s]", x, y, type_info);
        }
    }



}
