public class Main {
    public static void main(String[] args) {
        int width = 1280;
        int height = 720;

        Graph graph = new Graph(width, height, -10, 10, 0, 0);
        try {
            graph.calculatePoints();
        } catch (NanException e) {
            //
        }
    }
}