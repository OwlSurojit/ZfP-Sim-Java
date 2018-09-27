package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Polygon;

public class PolygonTab extends PropertiesTab{
    public Polygon polygon;
    public FieldVerifier fv;
        
    public JPanel namePanel; public JLabel nameLabel; public JTextField nameField;
    public JScrollPane pointsScrollPane;
    
    public PolygonTab(Polygon line, FieldVerifier fv){
        this.polygon = line;
        this.fv = fv;
        
        setLayout(new GridLayout(2, 1));
        
        namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 2));
        nameLabel = new JLabel("Name");
        nameField = new JTextField(line.drawingInfo.name);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        
        pointsScrollPane = new JScrollPane();
        
        add(namePanel);
        add(pointsScrollPane);
    }

    @Override
    public void commit() {
        polygon.drawingInfo.name = nameField.getText();
    }
    
}
