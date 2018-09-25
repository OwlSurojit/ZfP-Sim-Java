package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Circle;

public class CircleTab extends PropertiesTab{
    public Circle cirlce;
        
    public JLabel nameLabel; public JTextField nameField;
    
    public CircleTab(Circle cirlce){
        this.cirlce = cirlce;
        
        setLayout(new GridLayout(1, 2));
        
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
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
