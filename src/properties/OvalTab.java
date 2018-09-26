package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Oval;

public class OvalTab extends PropertiesTab{
    public Oval oval;
        
    public JLabel nameLabel; public JTextField nameField;
    
    public OvalTab(Oval oval){
        this.oval = oval;
        
        setLayout(new GridLayout(1, 2));
        
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
        nameLabel = new JLabel("Name");
        nameField = new JTextField(oval.drawingInfo.name);
        
        add(nameLabel);
        add(nameField);
    }

    @Override
    public void commit() {
        oval.drawingInfo.name = nameField.getText();
    }
}
