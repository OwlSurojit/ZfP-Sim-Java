package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.CircleArc;

public class CircleArcTab extends PropertiesTab{
    public CircleArc cirlceArc;
        
    public JLabel nameLabel; public JTextField nameField;
    
    public CircleArcTab(CircleArc cirlceArc){
        this.cirlceArc = cirlceArc;
        
        setLayout(new GridLayout(1, 2));
        
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
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
