package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Circle;

public class CircleTab extends PropertiesTab{
    public Circle cirlce;
    public FieldVerifier fv;
    
    public JLabel nameLabel; public JTextField nameField;
    
    public CircleTab(Circle cirlce, FieldVerifier fv){
        this.cirlce = cirlce;
        this.fv = fv;
        
        setLayout(new GridLayout(1, 2));
        
        nameLabel = new JLabel("Name");
        nameField = new JTextField(cirlce.drawingInfo.name);
        
        add(nameLabel);
        add(nameField);
    }

    @Override
    public void commit() {
        cirlce.drawingInfo.name = nameField.getText();
    }
}
