public class Main {
    public static void main(String[] args) {
        int width = 1280;
        int height = 720;

        Graph graph = new Graph(width, height, -10, 10);
        GraphWindow window = new GraphWindow(width, height);
        graph.calculatePoints();
        for(Graph.RealPoint p: graph.getRealPoints()) {
            System.out.println(p);
        }

        window.calculateGraph(graph.getRealPoints());
        window.drawAxes();
        window.drawGraph();
    }
}