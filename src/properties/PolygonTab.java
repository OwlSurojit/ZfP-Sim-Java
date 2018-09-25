package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Polygon;

public class PolygonTab extends PropertiesTab{
    
    public Polygon polygon;
        
    public JLabel nameLabel; public JTextField nameField;
    
    public PolygonTab(Polygon line){
        this.polygon = line;
        
        setLayout(new GridLayout(1, 2));
        
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
        nameLabel = new JLabel("Name");
        nameField = new JTextField(line.drawingInfo.name);
        
        add(nameLabel);
        add(nameField);
    }

    @Override
    public void commit() {
        polygon.drawingInfo.name = nameField.getText();
    }
    
}
