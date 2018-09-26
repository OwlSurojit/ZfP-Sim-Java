package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.CircleArc;

public class CircleArcTab extends PropertiesTab{
    public CircleArc cirlceArc;
    public FieldVerifier fv;
    
    public JLabel nameLabel; public JTextField nameField;
    
    public CircleArcTab(CircleArc cirlceArc, FieldVerifier fv){
        this.cirlceArc = cirlceArc;
        this.fv = fv;
        
        setLayout(new GridLayout(1, 2));
        
        nameLabel = new JLabel("Name");
        nameField = new JTextField(cirlceArc.drawingInfo.name);
        
        add(nameLabel);
        add(nameField);
    }

    @Override
    public void commit() {
        cirlceArc.drawingInfo.name = nameField.getText();
    }
}
