package bsu.rfct.java.course2.group7.Krutalevich.lab4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class GraphDisplay extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private double minX = -10;
    private double maxX = 10;
    private double minY = -10;
    private double maxY = 10;

    private double initialMinX, initialMaxX, initialMinY, initialMaxY;
    private Point startPoint = null;
    private Rectangle zoomRect = null;

    private List<Point> points = new ArrayList<>();
    private Point hoverPoint = null;

    //устанавливает размеры панели
    public GraphDisplay() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initialMinX = minX;
        initialMaxX = maxX;
        initialMinY = minY;
        initialMaxY = maxY;

        // Mouse listeners for hovering and zooming
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                checkHover(e.getPoint());
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (startPoint != null) {
                    int x = Math.min(startPoint.x, e.getX());
                    int y = Math.min(startPoint.y, e.getY());
                    int width = Math.abs(startPoint.x - e.getX());
                    int height = Math.abs(startPoint.y - e.getY());
                    zoomRect = new Rectangle(x, y, width, height);
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    startPoint = e.getPoint();
                    zoomRect = null;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && zoomRect != null) {
                    applyZoom(zoomRect);
                    startPoint = null;
                    zoomRect = null;
                    repaint();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    resetZoom();
                    repaint();
                }
            }
        });

        // Generate function points
        generatePoints();
    }

    // Generate points for the function y = sin(x)
    private void generatePoints() {
        double step = 0.1;
        for (double x = minX; x <= maxX; x += step) {
            double y = Math.cos(x);  // Example function y = cos(x)
            int screenX = (int) ((x - minX) / (maxX - minX) * WIDTH);
            int screenY = HEIGHT - (int) ((y - minY) / (maxY - minY) * HEIGHT);
            points.add(new Point(screenX, screenY));
        }
    }

    // Check if the mouse is hovering over any point
    private void checkHover(Point mousePoint) {
        hoverPoint = null;
        for (Point p : points) {
            if (mousePoint.distance(p) < 6) {  // Tolerance for hover detection
                hoverPoint = p;
                break;
            }
        }
    }

    // Apply zoom based on the selected rectangle
    private void applyZoom(Rectangle zoomRect) {
        double x1 = minX + zoomRect.x * (maxX - minX) / WIDTH;
        double x2 = minX + (zoomRect.x + zoomRect.width) * (maxX - minX) / WIDTH;
        double y1 = maxY - (zoomRect.y + zoomRect.height) * (maxY - minY) / HEIGHT;
        double y2 = maxY - zoomRect.y * (maxY - minY) / HEIGHT;

        minX = Math.min(x1, x2);
        maxX = Math.max(x1, x2);
        minY = Math.min(y1, y2);
        maxY = Math.max(y1, y2);

        points.clear();
        generatePoints();
    }

    // Reset zoom to the initial state
    private void resetZoom() {
        minX = initialMinX;
        maxX = initialMaxX;
        minY = initialMinY;
        maxY = initialMaxY;

        points.clear();
        generatePoints();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем оси
        drawAxes(g2d);

        // Устанавливаем стиль для линии функции
        g2d.setColor(Color.BLUE);
        float[] dashPattern = {12, 3, 3, 5};
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));

        // Рисуем линию функции
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Отображение точек
        for (Point p : points) {
            double x = minX + p.x * (maxX - minX) / WIDTH;
            double y = maxY - p.y * (maxY - minY) / HEIGHT;
            int integerPart = (int) Math.floor(y);

            if (integerPart % 2 != 0) { // Нечетная целая часть
                g2d.setColor(Color.RED); // Выделяем цветом
                drawDiamond(g2d, p.x, p.y, 6); // Рисуем ромб
            } else {
                g2d.setColor(Color.BLACK);
                g2d.fill(new Ellipse2D.Double(p.x - 3, p.y - 3, 6, 6)); // Обычная точка
            }
        }

        // Рисуем прямоугольник для зума
        if (zoomRect != null) {
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
            g2d.draw(zoomRect);
        }

        // Отображение координат при наведении на точку
        if (hoverPoint != null) {
            g2d.setColor(Color.BLACK);
            double x = minX + hoverPoint.x * (maxX - minX) / WIDTH;
            double y = maxY - hoverPoint.y * (maxY - minY) / HEIGHT;
            g2d.drawString(String.format("(%.2f, %.2f)", x, y), hoverPoint.x + 10, hoverPoint.y - 10);
        }
    }

    // Метод для рисования ромба
    private void drawDiamond(Graphics2D g2d, int x, int y, int size) {
        int[] xPoints = {x, x + size, x, x - size};
        int[] yPoints = {y - size, y, y + size, y};
        g2d.fillPolygon(xPoints, yPoints, 4);
    }




    // Draw coordinate axes
    private void drawAxes(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);  // X-axis
        g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);  // Y-axis
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Graph Display with Zoom and Hover");
        GraphDisplay graphDisplay = new GraphDisplay();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(graphDisplay);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
