import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphWindow {
    BufferedImage img;

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
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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
        double resX = (x_max - x_min) / (width - 1);  // Resolution: steps per pixel
        double resY = (y_max - y_min) / (height * 0.9 - 1); // 10% of vertical space must be free

        int x;
        int y;

        for(Graph.Point p: points) {
            x = (int) (p.getX() / resX + (width) / 2);

            if(p.isReal()) {
                y = (int) (-p.getY() / resY + height / 2);
                graphPoints.add(new ScreenPoint(x, y, false));
            } else {
                graphPoints.add(new ScreenPoint(x, true));
            }
        }
    }

    public void drawGraph() {
        Graphics2D g = img.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);

        for(ScreenPoint p: graphPoints) {
            try {
                if(!p.isEmpty()) img.setRGB(p.getX(), p.getY(), Color.WHITE.getRGB());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e);
            }
        }

        ScreenPoint p_last = graphPoints.get(0);
        ScreenPoint p_current;

        for(int i = 1; i < graphPoints.size(); i++) {
            p_current = graphPoints.get(i);

            if(!p_last.isEmpty() && !p_current.isEmpty()) {
                g.drawLine(p_last.getX(), p_last.getY(), p_current.getX(), p_current.getY());
            }

            p_last = p_current;
        }

        File f = new File("img.png");
        try {
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void drawAxes() {

    }

    static class ScreenPoint {
        private int x;
        private int y;
        private boolean empty;

        public ScreenPoint(int x, int y, boolean empty) {
            this.x = x;
            this.y = y;
            this.empty = empty;
        }

        public ScreenPoint(int x, boolean empty) {
            this.x = x;
            this.y = 0;
            this.empty = empty;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isEmpty() {
            return empty;
        }

        @Override
        public String toString() {
            return String.format("ScreenPoint[x: %d, y: %d, empty: %s]", x, y, empty);
        }
    }
}
