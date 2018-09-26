package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Polygon;

public class PolygonTab extends PropertiesTab{
    public Polygon polygon;
    public FieldVerifier fv;
        
    public JLabel nameLabel; public JTextField nameField;
    
    public PolygonTab(Polygon line, FieldVerifier fv){
        this.polygon = line;
        this.fv = fv;
        
        setLayout(new GridLayout(1, 2));
        
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
