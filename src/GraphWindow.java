import java.util.ArrayList;
import java.util.List;

public class GraphWindow {
    private int width;
    private int height;

    private List<ScreenPoint> screenPoints = new ArrayList<>();

    public GraphWindow(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void calculateGraph(List<Graph.RealPoint> realPoints) {

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
