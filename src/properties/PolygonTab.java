package properties;

import enums.VerificationType;
import eventListeners.DocumentVerificationListener;
import geometry.Point;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import shapesBase.Line;
import shapesBase.Polygon;
import structures.StructFieldType;

public class PolygonTab extends PropertiesTab{
    public Polygon polygon;
    public FieldVerifier fv;
        
    public JPanel namePanel; public JLabel nameLabel; public JTextField nameField;
    public JScrollPane pointsScrollPane; public JList pointsList;
    public JPanel buttonPanel; public JButton deleteButton; public JButton upButton; public JButton downButton;
    public JPanel addPanel; public PointPanel toAddPanel; public JButton addButton;
    
    public PolygonTab(Polygon polygon){
        this.polygon = polygon;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 2));
        nameLabel = new JLabel("Name");
        nameField = new JTextField(polygon.drawingInfo.name);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        
        pointsScrollPane = new JScrollPane();
        pointsScrollPane.setSize(600, 200);
        DefaultListModel<Point> points = new DefaultListModel<>();
        for(Point p : polygon.points){
            double x = Math.round(p.x * 100.0) / 100.0;
            double y = Math.round(p.y * 100.0) / 100.0;
            points.addElement(new Point(x, y));
        }
        pointsList = new JList();
        pointsList.setModel(points);
        pointsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pointsList.setSelectedIndex(0);
        pointsList.setCellRenderer(new PointCellRenderer());
        pointsScrollPane.setViewportView(pointsList);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        deleteButton = new JButton("✖");
        deleteButton.setForeground(Color.RED);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        deleteButton.setEnabled(pointsList.getModel().getSize() > 1);
        upButton = new JButton("▲");
        upButton.setForeground(Color.GREEN);
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });
        downButton = new JButton("▼");
        downButton.setForeground(Color.GREEN);
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(deleteButton);
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        
        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(1, 2));
        toAddPanel = new PointPanel(0, 0);
        addButton = new JButton("Punkt hinzufügen");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        addPanel.add(toAddPanel);
        addPanel.add(addButton);
        
        fv = new FieldVerifier(addButton);
        StructFieldType ax = new StructFieldType(toAddPanel.xField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType ay = new StructFieldType(toAddPanel.yField, VerificationType.NON_NEG_DOUBLE);
        fv.addField(ax);
        fv.addField(ay);
        toAddPanel.xField.getDocument().addDocumentListener(new DocumentVerificationListener(ax, fv) );
        toAddPanel.yField.getDocument().addDocumentListener(new DocumentVerificationListener(ay, fv) );
        
        add(namePanel);
        add(pointsScrollPane);
        add(buttonPanel);
        add(addPanel);
    }
    
    private void deleteButtonActionPerformed(ActionEvent evt) {
        int previousLit = pointsList.getSelectedIndex();
        Point toDelete = (Point) pointsList.getSelectedValue();
        if(toDelete != null){
            DefaultListModel<Point> copy = new DefaultListModel<>();
            for(int i = 0; i < pointsList.getModel().getSize(); i++){
                Point p = (Point) pointsList.getModel().getElementAt(i);
                if(p != toDelete){
                    copy.addElement(new Point(p.x, p.y));
                }
            }
            pointsList.setModel(copy);
            int size = pointsList.getModel().getSize();
            if(size > previousLit){
                pointsList.setSelectedIndex(previousLit);
            }
            else{
                pointsList.setSelectedIndex(size-1);
            }
            if(size == 1){
                deleteButton.setEnabled(false);
            }
        }
    }

    private void upButtonActionPerformed(ActionEvent evt) {
        int previousLit = pointsList.getSelectedIndex();
        Point toRaise = (Point) pointsList.getSelectedValue();
        if(toRaise != null && toRaise != pointsList.getModel().getElementAt(0)){
            DefaultListModel<Point> copy = new DefaultListModel<>();
            for(int i = 0; i < pointsList.getModel().getSize(); i++){
                Point p = (Point) pointsList.getModel().getElementAt(i);
                if(p != toRaise){
                    copy.addElement(new Point(p.x, p.y));
                }
                else{
                    copy.insertElementAt(new Point(p.x, p.y), copy.getSize()-1);
                }
            }
            pointsList.setModel(copy);
            pointsList.setSelectedIndex(previousLit-1);
        }
    }
    
    private void downButtonActionPerformed(ActionEvent evt) {
        int previousLit = pointsList.getSelectedIndex();
        Point toLower = (Point) pointsList.getSelectedValue();
        if(toLower != null && toLower != pointsList.getModel().getElementAt(pointsList.getModel().getSize()-1)){
            DefaultListModel<Point> copy = new DefaultListModel<>();
            boolean lower = false;
            for(int i = 0; i < pointsList.getModel().getSize(); i++){
                Point p = (Point) pointsList.getModel().getElementAt(i);
                if(p != toLower){
                    copy.addElement(new Point(p.x, p.y));
                    if(lower){
                        copy.addElement(toLower);
                        lower = false;
                    }
                }
                else{
                    lower = true;
                }
            }
            pointsList.setModel(copy);
            pointsList.setSelectedIndex(previousLit+1);
        }
    }
    
    private void addButtonActionPerformed(ActionEvent evt) {
        ((DefaultListModel) pointsList.getModel()).addElement(new Point(Double.parseDouble(toAddPanel.xField.getText()), Double.parseDouble(toAddPanel.yField.getText())));
        deleteButton.setEnabled(true);
    }

    @Override
    public void commit() {
        polygon.drawingInfo.name = nameField.getText();
        polygon.points = new Point[pointsList.getModel().getSize()];
        for(int i = 0; i < pointsList.getModel().getSize(); i++){
                polygon.points[i] = (Point) pointsList.getModel().getElementAt(i);
        }
        polygon.lines = new Line[polygon.points.length-1];
        for(int i = 0; i<polygon.points.length -1; i++){
            polygon.lines[i] = new Line(polygon.points[i], polygon.points[i+1]);
        }
    }
    
}
