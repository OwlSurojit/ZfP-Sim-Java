package properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PointPanel extends JPanel{
    public JLabel xLabel;
    public JTextField xField;
    public JLabel yLabel;
    public JTextField yField;
    
    public PointPanel(double x, double y){
        xLabel = new JLabel("x:");
        //xLabel.setSize(50, 50);
        
        xField = new JTextField();
        xField.setText(Double.toString(x));
        //xField.setSize(100, 50);
        
        yLabel = new JLabel("y:");
        //yLabel.setSize(50, 50);
        
        yField = new JTextField();
        yField.setText(Double.toString(y));
        //yField.setSize(100, 50);
        
        add(xLabel);
        add(xField);
        add(yLabel);
        add(yField);
    }
}
