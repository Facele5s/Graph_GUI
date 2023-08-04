public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(-10, 10);
        GraphWindow window = new GraphWindow(1280, 720);

        graph.calculatePoints();
        window.calculateGraph(graph.getRealPoints());
        window.drawAxes();
        window.drawGraph();
    }
}