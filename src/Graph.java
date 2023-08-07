import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int width;
    private int height;

    private double x_min;
    private double x_max;

    private Double y_min;
    private Double y_max;

    private List<Point> points = new ArrayList<>();

    public Graph(int width, int height, double x_min, double x_max, double y_min, double y_max) {
        this.width = width;
        this.height = height;
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
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

    public void setY_min(double y_min) {
        this.y_min = y_min;
    }

    public void setY_max(double y_max) {
        this.y_max = y_max;
    }

    public List<Point> getRealPoints() {
        return points;
    }

    public void calculatePoints() throws NanException {
        double resolution = (x_max - x_min) / width;   // x steps per pixel
        Double y;
        Point point;
        boolean hasRealValues = false;

        if(y_min == 0) y_min = -resolution * height / 2;
        if(y_max == 0) y_max = resolution * height / 2;

        for(double x = x_min; x <= x_max; x+= resolution) {
            y = func(x);
            if(y.equals(Double.POSITIVE_INFINITY) || y.equals(Double.NEGATIVE_INFINITY) || y.equals(Double.NaN)) {
                point = new Point(x, y, false);
            } else {
                point = new Point(x, y, true);
                hasRealValues = true;
            }

            points.add(point);
        }

        if(!hasRealValues) throw new NanException("The function doesn't have real values");
    }

    public GraphWindow createGraphWindow(int width, int height, double x_min, double x_max, double y_min, double y_max) {
        return new GraphWindow(width, height, x_min, x_max, y_min, y_max);
    }

    static class Point {
        private double x;
        private double y;
        private boolean real;

        public Point(double x, double y, boolean real) {
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

            return String.format("Point[x: %f, y: %f, type: %s]", x, y, type_info);
        }
    }



}
