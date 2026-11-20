import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingForensics extends JPanel {

    ArrayList<Point> points = new ArrayList<>();

    public DrawingForensics() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);

        for (Point p : points) {
            g.fillOval(p.x - 5, p.y - 5, 10, 10);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Handling Forensics");

        frame.add(new DrawingForensics());
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}