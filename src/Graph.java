import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int width;
    private int height;

    private int x_min;
    private int x_max;

    private double y_min_real;
    private double y_max_real;

    private List<RealPoint> realPoints = new ArrayList<>();

    public Graph(int width, int height, int x_min, int x_max) {
        this.width = width;
        this.height = height;
        this.x_min = x_min;
        this.x_max = x_max;
    }

    private double func(double x) {
        return Math.sqrt(x);
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
            if(y.equals(Double.POSITIVE_INFINITY)) {
                point = new RealPoint(x, y, 2);
            } else if(y.equals(Double.NEGATIVE_INFINITY)) {
                point = new RealPoint(x, y, 3);
            } else if(y.equals(Double.NaN)) {
                point = new RealPoint(x, y, 4);
            } else {
                point = new RealPoint(x, y, 1);
            }

            realPoints.add(point);
        }
    }

    static class RealPoint {
        private double x;
        private double y;
        private int type;   // 1 - real, 2 - p_infinity, 3 - n_infinity, 4 - NaN

        public RealPoint(double x, double y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public int getType() {
            return type;
        }

        @Override
        public String toString() {
            String type_info = "";
            switch (type) {
                case 1 -> type_info = "real";
                case 2 -> type_info = "p_infinity";
                case 3 -> type_info = "n_infinity";
                case 4 -> type_info = "NaN";
            }

            return String.format("RealPoint[x: %f, y: %f, type: %s]", x, y, type_info);
        }
    }



}
