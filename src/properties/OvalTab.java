package properties;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Oval;

public class OvalTab extends PropertiesTab{
    public Oval oval;
    public FieldVerifier fv;
        
    public JLabel nameLabel; public JTextField nameField;
    
    public OvalTab(Oval oval, FieldVerifier fv){
        this.oval = oval;
        this.fv = fv;
        
        setLayout(new GridLayout(1, 2));
        
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
