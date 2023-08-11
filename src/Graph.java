import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int width;
    private final int height;

    private final double x_min;
    private final double x_max;

    private Double y_min;
    private Double y_max;

    private final List<Point> points = new ArrayList<>();

    private final int type_param;
    private final int type_mult;

    private final double a;
    private final double k;

    private final String chosen_function;

    private Graph(Builder builder) {
        width = builder.width;
        height = builder.height;
        x_min = builder.x_min;
        x_max = builder.x_max;
        y_min = builder.y_min;
        y_max = builder.y_max;
        type_param = builder.type_param;
        type_mult = builder.type_mult;
        a = builder.a;
        k = builder.k;
        chosen_function = builder.chosen_function;
    }

    private double func(double x) {
        if (type_mult == 2) x *= k;
        if (type_param == 2) x += a;

        double f = 0;

        switch (chosen_function) {
            case "y = x" -> f = x;

            case "y = a" -> f = a;

            case "y = x^2" -> f = x * x;

            case "y = x^3" -> f = x * x * x;

            case "y = 1 / x" -> f = 1 / x;

            case "y = e^x" -> f = Math.exp(x);

            case "y = sqrt(x)" -> f = Math.sqrt(x);

            case "y = sin(x)" -> f = Math.sin(x);

            case "y = cos(x)" -> f = Math.cos(x);

            case "y = tan(x)" -> f = Math.tan(x);

            case "y = arcsin(x)" -> f = Math.asin(x);

            case "y = arccos(x)" -> f = Math.acos(x);

            case "y = arctan(x)" -> f = Math.atan(x);

            case "Heart" ->
                    f = (Math.sqrt(Math.cos(x)) * Math.cos(300 * x) + Math.sqrt(Math.abs(x))) * Math.pow((4 - x * x), 0.01);

            case "Triangular signal" -> f = Math.asin(Math.sin(x));
        }

        if (type_mult == 1) f *= k;
        if (type_param == 1 && !chosen_function.equals("y = a")) f += a;

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

        if (y_min.equals(Double.NaN)) y_min = -resolution * height / 2;
        if (y_max.equals(Double.NaN)) y_max = resolution * height / 2;

        for (int i = 0; i < width; i++) {
            x = x_min + i * (x_max - x_min) / (width - 1);
            y = func(x);

            if (y.equals(Double.POSITIVE_INFINITY) || y.equals(Double.NEGATIVE_INFINITY) || y.equals(Double.NaN)) {
                point = new Point(x, y, false);
            } else {
                point = new Point(x, y, true);
                hasRealValues = true;
            }

            points.add(point);
        }

        if (!hasRealValues) throw new NanException("The function doesn't have real values");
    }

    public static class Builder {
        private int width;
        private int height;

        private double x_min;
        private double x_max;

        private Double y_min;
        private Double y_max;

        private int type_param;
        private int type_mult;

        private double a;
        private double k;

        private String chosen_function;

        public Builder width(int val) {
            width = val;
            return this;
        }

        public Builder height(int val) {
            height = val;
            return this;
        }

        public Builder xmin(double val) {
            x_min = val;
            return this;
        }

        public Builder xmax(double val) {
            x_max = val;
            return this;
        }

        public Builder ymin(double val) {
            y_min = val;
            return this;
        }

        public Builder ymax(double val) {
            y_max = val;
            return this;
        }

        public Builder tparam(int val) {
            type_param = val;
            return this;
        }

        public Builder tmult(int val) {
            type_mult = val;
            return this;
        }

        public Builder a(double val) {
            a = val;
            return this;
        }

        public Builder k(double val) {
            k = val;
            return this;
        }

        public Builder chosenfunction(String val) {
            chosen_function = val;
            return this;
        }

        public Graph build() {
            return new Graph(this);
        }
    }

    static class Point {
        private final double x;
        private final double y;
        private final boolean real;

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
    }


}
