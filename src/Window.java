import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Window extends JFrame implements ActionListener {
    Graph graph = null;

    JPanel p_graph;
    JPanel p_controls;
    JPanel p_ranges;
    JPanel p_function;

    JLabel l_graph;
    JLabel l_x_range;
    JLabel l_y_range;
    JLabel l_output;
    JLabel l_param;
    JLabel l_mult;

    JTextField tf_x_min;
    JTextField tf_x_max;
    JTextField tf_y_min;
    JTextField tf_y_max;
    JTextField tf_param;
    JTextField tf_mult;

    JRadioButton rb_param_1;
    JRadioButton rb_param_2;
    JRadioButton rb_param_3;
    JRadioButton rb_mult_1;
    JRadioButton rb_mult_2;
    JRadioButton rb_mult_3;

    JButton btn_draw;

    JComboBox<String> box_functions;

    int type_param;
    int type_mult;

    public Window() {
        super("Graph :D");
        setSize(1300, 1000);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        p_graph = new JPanel();
        p_graph.setBackground(Color.BLACK);
        p_graph.setPreferredSize(new Dimension(1300, 740));
        p_graph.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(p_graph, BorderLayout.PAGE_START);

        p_controls = new JPanel();
        p_controls.setPreferredSize(new Dimension(1300, 260));
        p_controls.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 50));
        add(p_controls, BorderLayout.PAGE_END);

        p_ranges = new JPanel();
        p_ranges.setPreferredSize(new Dimension(300, 200));
        p_ranges.setLayout(new GridLayout(6, 3));
        p_controls.add(p_ranges);

        p_function = new JPanel();
        p_function.setPreferredSize(new Dimension(600, 40));
        p_function.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p_controls.add(p_function);

        l_graph = new JLabel();
        BufferedImage blank_graph = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = blank_graph.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1280, 720);
        l_graph.setIcon(new ImageIcon(blank_graph));
        p_graph.add(l_graph);

        tf_x_min = new JTextField();
        tf_x_min.setPreferredSize(new Dimension(50, 25));
        tf_x_max = new JTextField();
        tf_x_max.setPreferredSize(new Dimension(50, 25));
        tf_y_min = new JTextField();
        tf_y_min.setPreferredSize(new Dimension(50, 25));
        tf_y_max = new JTextField();
        tf_y_max.setPreferredSize(new Dimension(50, 25));

        tf_param = new JTextField();
        tf_param.setPreferredSize(new Dimension(40, 25));

        tf_mult = new JTextField();
        tf_mult.setPreferredSize(new Dimension(40, 25));

        l_x_range = new JLabel("⩽ x ⩽");
        l_x_range.setHorizontalAlignment(JLabel.CENTER);
        l_x_range.setVerticalAlignment(JLabel.CENTER);

        l_y_range = new JLabel("⩽ y ⩽");
        l_y_range.setHorizontalAlignment(JLabel.CENTER);
        l_y_range.setVerticalAlignment(JLabel.CENTER);

        l_param = new JLabel("a = ");
        l_param.setHorizontalAlignment(JLabel.RIGHT);
        l_param.setVerticalAlignment(JLabel.CENTER);

        l_mult = new JLabel("k = ");
        l_mult.setHorizontalAlignment(JLabel.RIGHT);
        l_mult.setVerticalAlignment(JLabel.CENTER);


        rb_param_1 = new JRadioButton("Exclude a");
        rb_param_1.setSelected(true);
        rb_param_1.addActionListener(this);

        rb_param_2 = new JRadioButton("y = f(x) + a");
        rb_param_2.addActionListener(this);

        rb_param_3 = new JRadioButton("y = f(x + a)");
        rb_param_3.addActionListener(this);


        rb_mult_1 = new JRadioButton("Exclude k");
        rb_mult_1.setSelected(true);
        rb_mult_1.addActionListener(this);

        rb_mult_2 = new JRadioButton("y = k * f(x)");
        rb_mult_2.addActionListener(this);

        rb_mult_3 = new JRadioButton("y = f(k * x)");
        rb_mult_3.addActionListener(this);

        ////

        p_ranges.add(tf_x_min);
        p_ranges.add(l_x_range);
        p_ranges.add(tf_x_max);

        p_ranges.add(tf_y_min);
        p_ranges.add(l_y_range);
        p_ranges.add(tf_y_max);

        p_ranges.add(new JLabel());
        p_ranges.add(l_param);
        p_ranges.add(tf_param);

        p_ranges.add(new JLabel());
        p_ranges.add(l_mult);
        p_ranges.add(tf_mult);

        p_ranges.add(rb_param_1);
        p_ranges.add(rb_param_2);
        p_ranges.add(rb_param_3);
        ButtonGroup bg_param = new ButtonGroup();
        bg_param.add(rb_param_1);
        bg_param.add(rb_param_2);
        bg_param.add(rb_param_3);

        p_ranges.add(rb_mult_1);
        p_ranges.add(rb_mult_2);
        p_ranges.add(rb_mult_3);
        ButtonGroup bg_mult = new ButtonGroup();
        bg_mult.add(rb_mult_1);
        bg_mult.add(rb_mult_2);
        bg_mult.add(rb_mult_3);

        ////

        String[] functions = {"y = x", "y = a", "y = x^2", "y = x^3", "y = 1 / x",
                "y = e^x", "y = sqrt(x)", "y = sin(x)", "y = cos(x)", "y = tan(x)", "y = arcsin(x)",
                "y = arccos(x)", "y = arctan(x)", "Heart", "Triangular signal"
                };
        box_functions = new JComboBox<>(functions);
        p_function.add(box_functions);

        btn_draw = new JButton("Draw graph!");
        btn_draw.addActionListener(this);
        p_function.add(btn_draw);

        l_output = new JLabel();
        p_function.add(l_output);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exclude a" -> type_param = 0;

            case "y = f(x) + a" -> type_param = 1;

            case "y = f(x + a)" -> type_param = 2;

            case "Exclude k" -> type_mult = 0;

            case "y = k * f(x)" -> type_mult = 1;

            case "y = f(k * x)" -> type_mult = 2;
            
            case "Draw graph!" -> {
                double x_min;
                double x_max;
                double y_min;
                double y_max;
                double a = 0;
                double k = 0;

                try {
                    x_min = Double.parseDouble(tf_x_min.getText());
                    x_max = Double.parseDouble(tf_x_max.getText());
                } catch (Exception ex) {
                    l_output.setText("Wrong 'x range' input");
                    return;
                }

                if (x_min >= x_max) {
                    l_output.setText("Xmin must be less than Xmax");
                    return;
                }

                if (tf_y_min.getText().equals("")) {
                    y_min = Double.NaN;
                } else {
                    try {
                        y_min = Double.parseDouble(tf_y_min.getText());
                    } catch (Exception ex) {
                        l_output.setText("Wrong 'Ymin' input");
                        return;
                    }
                }

                if (tf_y_max.getText().equals("")) {
                    y_max = Double.NaN;
                } else {
                    try {
                        y_max = Double.parseDouble(tf_y_max.getText());
                    } catch (Exception ex) {
                        l_output.setText("Wrong 'Ymax' input");
                        return;
                    }
                }

                if (type_param > 0) {
                    if (tf_param.getText().equals("")) {
                        type_param = 0;
                        rb_param_1.setSelected(true);
                    } else {
                        try {
                            a = Double.parseDouble(tf_param.getText());
                        } catch (Exception ex) {
                            l_output.setText("Wrong 'a' input");
                            return;
                        }
                    }
                }

                if (type_mult > 0) {
                    if (tf_mult.getText().equals("")) {
                        type_mult = 0;
                        rb_mult_1.setSelected(true);
                    } else {
                        try {
                            k = Double.parseDouble(tf_mult.getText());
                        } catch (Exception ex) {
                            l_output.setText("Wrong 'k' input");
                            return;
                        }
                    }
                }

                if (y_min >= y_max) {
                    l_output.setText("Ymin must be less than Ymax");
                    return;
                }

                Graph.Builder builder = new Graph.Builder().width(1280).height(720);
                builder.xmin(x_min).xmax(x_max);
                builder.ymin(y_min).ymax(y_max);
                builder.tparam(type_param).tmult(type_mult);
                builder.a(a).k(k);
                builder.chosenfunction((String) box_functions.getSelectedItem());
                graph = builder.build();

                try {
                    graph.calculatePoints();
                } catch (NanException ex) {
                    l_output.setText("Function doesn't have real values in this range");
                    return;
                }

                GraphWindow graphWindow = new GraphWindow(graph, 1280, 720);
                graphWindow.calculateScreenPoints();
                graphWindow.drawGraph();
                l_graph.setIcon(new ImageIcon(graphWindow.getImg()));
            }
        }
    }
}
