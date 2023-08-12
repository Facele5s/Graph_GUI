import java.util.ArrayList;
import java.util.List;

public class Graph {
    // Class which represents the graph entity (minimums, maximums, points)

    private final int width;
    private final int height;

    private final double x_min;
    private final double x_max;

    private Double y_min;
    private Double y_max;

    private final List<Point> points = new ArrayList<>();   // Points with real values

    private final int type_param;   // 0 - exclude parameter, 1 - y = f(x) + a, 2 - y = f(x + a)
    private final int type_mult;    // 0 - exclude multiplier, 1 - y = f(x) * k, 2 - y = f(x * k)

    private final double a; // Parameter
    private final double k; // Multiplier

    private final String chosen_function;

    private Graph(Builder builder) {    // Constructor based on builder pattern
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

    private double func(double x) { // A method which represents selected function
        if (type_mult == 2) x *= k; // Apply the multiplier if necessary
        if (type_param == 2) x += a;    // Apply the parameter if necessary

        double f = 0;   // f(x)

        // Choice of function
        switch (chosen_function) {
            case "y = x" -> f = x;  // Linear function

            case "y = a" -> f = a;  // Straight line function

            case "y = x^2" -> f = x * x;    // Parabola

            case "y = x^3" -> f = x * x * x;    // Cubic function

            case "y = 1 / x" -> f = 1 / x;  // Hyperbole

            case "y = e^x" -> f = Math.exp(x);  // Exponential function

            case "y = sqrt(x)" -> f = Math.sqrt(x); // Square root function

            case "y = sin(x)" -> f = Math.sin(x);   // Sine

            case "y = cos(x)" -> f = Math.cos(x);   // Cosine

            case "y = tan(x)" -> f = Math.tan(x);   // Tangent

            case "y = arcsin(x)" -> f = Math.asin(x);   // Arcsinus

            case "y = arccos(x)" -> f = Math.acos(x);   // Arccosinus

            case "y = arctan(x)" -> f = Math.atan(x);   // Arctangent

            case "Heart" -> // Heart function
                    f = (Math.sqrt(Math.cos(x)) * Math.cos(300 * x) + Math.sqrt(Math.abs(x))) * Math.pow((4 - x * x), 0.01);

            case "Triangular signal" -> f = Math.asin(Math.sin(x)); // Triangular signal function
        }

        if (type_mult == 1) f *= k; // Apply the multiplier of necessary
        if (type_param == 1 && !chosen_function.equals("y = a")) f += a;    // Apply the parameter if necessary (not for straight line)

        return f;
    }

    // Getters

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

    public void calculatePoints() throws NanException { // A method which calculates all acceptable function points
        double resolution = (x_max - x_min + 1) / width;   // x steps per pixel resolution
        boolean hasRealValues = false;

        double x;
        Double y;
        Point point;

        // If Ymin or Ymax isn't defined, we calculate it according to resolution
        if (y_min.equals(Double.NaN)) y_min = -resolution * height / 2;
        if (y_max.equals(Double.NaN)) y_max = resolution * height / 2;

        // Calculate points for all pixels on horizontal display line
        for (int i = 0; i < width; i++) {
            // Get x value in range [Xmin, Xmax] according to pixel number
            x = x_min + i * (x_max - x_min) / (width - 1);
            // y = f(x)
            y = func(x);

            if (y.equals(Double.POSITIVE_INFINITY) || y.equals(Double.NEGATIVE_INFINITY) || y.equals(Double.NaN)) {
                point = new Point(x, y, false); // If function is NaN or infinity, we create empty point for this x value
            } else {
                point = new Point(x, y, true);  // Create real point
                hasRealValues = true;   //  Now the function has real values in this range
            }

            points.add(point);
        }

        if (!hasRealValues) throw new NanException("The function doesn't have real values");
    }

    // Builder pattern for Graph
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

    // Point with real x and y coordinates. Can be empty (not real)
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
