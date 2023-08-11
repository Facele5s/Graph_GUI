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

    private int type_param;
    private int type_mult;

    private double a;
    private double k;

    private String chosen_function;

    public Graph(int width, int height, double x_min, double x_max, double y_min, double y_max) {
        this.width = width;
        this.height = height;
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
        this.type_param = 0;
        this.type_mult = 0;
        this.a = 0.0;
        this.k = 0.0;
    }

    public void setParams(int type_param, int type_mult, double a, double k) {
        this.type_param = type_param;
        this.type_mult = type_mult;
        this.a = a;
        this.k = k;
    }

    public void setChosen_function(String chosen_function) {
        this.chosen_function = chosen_function;
    }

    private double func(double x) {
        if(type_mult == 2) x*= k;
        if(type_param == 2) x+= a;

        double f = 0;

        switch (chosen_function) {
            case "y = x": {
                f = x;
                break;
            }
            case "y = a": {
                f = a;
                break;
            }
            case "y = x^2": {
                f = x * x;
                break;
            }
            case "y = x^3": {
                f = x * x * x;
                break;
            }
            case "y = 1 / x": {
                f = 1 / x;
                break;
            }
            case "y = e^x": {
                f = Math.exp(x);
                break;
            }
            case "y = sqrt(x)": {
                f = Math.sqrt(x);
                break;
            }
            case "y = sin(x)": {
                f = Math.sin(x);
                break;
            }
            case "y = cos(x)": {
                f = Math.cos(x);
                break;
            }
            case "y = tan(x)": {
                f = Math.tan(x);
                break;
            }
            case "y = arcsin(x)": {
                f = Math.asin(x);
                break;
            }
            case "y = arccos(x)": {
                f = Math.acos(x);
                break;
            }
            case "y = arctan(x)": {
                f = Math.atan(x);
                break;
            }
            case "Heart": {
                f = (Math.sqrt(Math.cos(x)) * Math.cos(300 * x) + Math.sqrt(Math.abs(x))) * Math.pow((4 - x * x), 0.01);
                break;
            }
            case "Triangular signal": {
                f = Math.asin(Math.sin(x));
                break;
            }
        }

        if(type_mult == 1) f*= k;
        if(type_param == 1) f+= a;

        return f;
    }

    public double getX_min() {
        return x_min;
    }

    public double getX_max() {
        return x_max;
    }

    public Double getY_min() {
        return y_min;
    }

    public Double getY_max() {
        return y_max;
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
        points.clear();

        double resolution = (x_max - x_min + 1) / width;   // x steps per pixel
        boolean hasRealValues = false;

        double x;
        Double y;
        Point point;

        if(y_min.equals(Double.NaN)) y_min = -resolution * height / 2;
        if(y_max.equals(Double.NaN)) y_max = resolution * height / 2;

        for(int i = 0; i < width; i++) {
            x = x_min + i * (x_max - x_min) / (width - 1);
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

    static class Point implements Comparable<Point> {
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

        @Override
        public int compareTo(Point p) {
            if(this.x > p.getX()) {
                return 1;
            } else if(this.x < p.getX()) {
                return -1;
            } else return 0;
        }
    }



}
