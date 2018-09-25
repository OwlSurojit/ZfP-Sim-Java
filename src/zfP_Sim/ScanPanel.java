package zfP_Sim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class ScanPanel extends JPanel {
    
    public MainWindow main;
    private int width = 1780;
    private int heigth = 380;
    private int padding = 25;
    private int labelPadding = 30;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberXDivisions = 10;
    private int numberYDivisions = 10;
    private List<Double[]> scores;

    public ScanPanel(){
        scores = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        int graphWidth = getWidth() - (2 * padding) - labelPadding;
        int graphHeight = getHeight() - (2 * padding) - labelPadding;
        
        double lowerPow;
        double xMax;
        double yMax;
        double xScale;
        double yScale;
        if(scores.size()>0){
            lowerPow = Math.floor(Math.log10(scores.get(scores.size() - 1)[0]));
            xMax = (Math.pow(10, lowerPow) * Math.ceil(scores.get(scores.size() - 1)[0] / Math.pow(10, lowerPow)));
            yMax = scores.get(0)[1];
            xScale = graphWidth / xMax;
            yScale = graphHeight / scores.get(0)[1];
        }
        else{
            lowerPow = 0;
            xMax = 1;
            yMax = 1;
            xScale = graphWidth;
            yScale = graphHeight;
        }
        
        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            int x1 = (int) (scores.get(i)[0] * xScale + padding + labelPadding);
            int y1 = (int) ((yMax - scores.get(i)[1]) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, graphWidth, graphHeight);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = x0 + pointWidth;
            int y = graphHeight + padding - ( i * graphHeight / numberYDivisions);
            
            g2.setColor(gridColor);
            g2.drawLine(x0, y, getWidth() - padding, y);
            g2.setColor(Color.BLACK);
            String yLabel = (double) (yMax * i / numberYDivisions) + "";
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(yLabel);
            g2.drawString(yLabel, x0 - labelWidth - 5, y + (metrics.getHeight() / 2) - 3);
            g2.drawLine(x0, y, x1, y);
        }

        // and for x axis
        for (int i = 0; i < numberXDivisions + 1; i++) {
            int x = padding + labelPadding + (i * graphWidth / numberXDivisions);
            int y0 = graphHeight + padding;
            int y1 = y0 - pointWidth;
            g2.setColor(gridColor);
            g2.drawLine(x, y0, x, padding);
            g2.setColor(Color.BLACK);
            String xLabel = (double) (xMax * i / numberXDivisions) + "";
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(xLabel);
            g2.drawString(xLabel, x - labelWidth / 2, y0 + metrics.getHeight() + 3);
            g2.drawLine(x, y0, x, y1);
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, graphHeight + padding, graphWidth + padding + labelPadding, graphHeight + padding);
        g2.drawLine(padding + labelPadding, graphHeight + padding, padding + labelPadding, padding);       

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() /*- 1*/; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            /*int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;*/
            g2.drawLine(x1, y1, x1, getHeight() - padding - labelPadding);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(width, heigth);
//    }

    public void setScores(Double[][] scores) {
        this.scores = Arrays.asList(scores);
        invalidate();
        this.repaint();
    }

    public List<Double[]> getScores() {
        return scores;
    }
}