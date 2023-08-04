import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int x_min;
    private int x_max;

    private double y_min_real;
    private double y_max_real;

    private List<RealPoint> realPoints = new ArrayList<>();

    public Graph(int x_min, int x_max) {
        this.x_min = x_min;
        this.x_max = x_max;
    }

    private double func(int x) {
        return 0;
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

    }

    static class RealPoint {
        private double x;
        private double y;
        private boolean isReal;

        public RealPoint(double x, double y, boolean isReal) {
            this.x = x;
            this.y = y;
            this.isReal = isReal;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public boolean isReal() {
            return isReal;
        }
    }



}
