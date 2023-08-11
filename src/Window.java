import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    JPanel p_graph;
    JPanel p_controls;
    JPanel p_ranges;
    JPanel p_function;

    JLabel l_graph;
    JLabel l_x_range;
    JLabel l_y_range;
    JLabel l_output;

    JTextField tf_x_min;
    JTextField tf_x_max;
    JTextField tf_y_min;
    JTextField tf_y_max;

    JButton btn_draw;

    JComboBox<String> box_functions;


    public Window() {
        super("Graph :D");
        setSize(1300, 900);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        p_graph = new JPanel();
        //p_graph.setBackground(Color.BLUE);
        p_graph.setPreferredSize(new Dimension(1300, 740));
        p_graph.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(p_graph, BorderLayout.PAGE_START);

        p_controls = new JPanel();
        //p_controls.setBackground(Color.RED);
        p_controls.setPreferredSize(new Dimension(1300, 160));
        p_controls.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 60));
        add(p_controls, BorderLayout.PAGE_END);

        p_ranges = new JPanel();
        //p_ranges.setBackground(Color.YELLOW);
        p_ranges.setPreferredSize(new Dimension(250, 60));
        p_ranges.setLayout(new GridLayout(2, 3));
        p_controls.add(p_ranges);

        p_function = new JPanel();
        //p_function.setBackground(Color.GREEN);
        p_function.setPreferredSize(new Dimension(600, 40));
        p_function.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 7));
        p_controls.add(p_function);

        l_graph = new JLabel();
        BufferedImage blank_graph = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = blank_graph.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1280, 720);
        l_graph.setIcon(new ImageIcon(blank_graph));
        p_graph.add(l_graph);

        tf_x_min = new JTextField();
        tf_x_max = new JTextField();
        tf_y_min = new JTextField();
        tf_y_max = new JTextField();

        l_x_range = new JLabel("<= x <=");
        l_x_range.setPreferredSize(new Dimension(60, 20));
        l_x_range.setHorizontalAlignment(JLabel.CENTER);
        l_x_range.setVerticalAlignment(JLabel.CENTER);

        l_y_range = new JLabel("<= y <=");
        l_y_range.setPreferredSize(new Dimension(60, 20));
        l_y_range.setHorizontalAlignment(JLabel.CENTER);
        l_y_range.setVerticalAlignment(JLabel.CENTER);

        p_ranges.add(tf_x_min);
        p_ranges.add(l_x_range);
        p_ranges.add(tf_x_max);
        p_ranges.add(tf_y_min);
        p_ranges.add(l_y_range);
        p_ranges.add(tf_y_max);

        String[] strings = new String[0];
        box_functions = new JComboBox<>(strings);
        p_function.add(box_functions);

        btn_draw = new JButton("Draw graph");
        p_function.add(btn_draw);

        l_output = new JLabel("OUTPUT");
        p_function.add(l_output);





        setVisible(true);
    }


}
