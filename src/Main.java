public class Main {
    public static void main(String[] args) {
        int width = 1280;
        int height = 720;

        Graph graph = new Graph(width, height, -10, 10);
        graph.calculatePoints();


        GraphWindow window = graph.createGraphWindow(width, height, -10, 10);
        try {
            window.calculateGraph(graph.getRealPoints());
        } catch (NanException e) {
            //
        }
        window.drawAxes();
        window.drawGraph();
    }
}