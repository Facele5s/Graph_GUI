import java.util.ArrayList;
import java.util.List;

public class GraphWindow {
    private int width;
    private int height;

    private double x_min;
    private double x_max;

    private Double y_min;
    private Double y_max;

    private List<Graph.Point> points = new ArrayList<>();
    private List<ScreenPoint> graphPoints = new ArrayList<>();

    public GraphWindow(Graph graph, int width, int height) {
        this.width = width;
        this.height = height;

        updateGraphParams(graph);
    }

    public void updateGraphParams(Graph graph) {
        this.x_min = graph.getX_min();
        this.x_max = graph.getX_max();
        this.y_min = graph.getY_min();
        this.y_max = graph.getY_max();
        this.points = graph.getRealPoints();
    }


    public void calculateScreenPoints() {

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
