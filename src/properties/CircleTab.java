package properties;

import enums.VerificationType;
import eventListeners.DocumentVerificationListener;
import geometry.Point;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import shapesBase.Circle;
import structures.StructFieldType;

public class CircleTab extends PropertiesTab{
    public Circle circle;
    public FieldVerifier fv;
    
    public JLabel nameLabel; public JTextField nameField;
    public JLabel centerLabel; public PointPanel centerPanel;
    public JLabel radiusLabel; public JTextField radiusField;
    
    public CircleTab(Circle circle, FieldVerifier fv){
        this.circle = circle;
        this.fv = fv;
        
        setLayout(new GridLayout(3, 2));
        
        nameLabel = new JLabel("Name");
        nameField = new JTextField(circle.drawingInfo.name);
        
        centerLabel = new JLabel("Mittelpunkt");
        centerPanel = new PointPanel(Math.round(circle.center.x * 100.0) / 100.0, Math.round(circle.center.y * 100.0) / 100.0);
        
        radiusLabel = new JLabel("Radius");
        radiusField = new JTextField(Double.toString(Math.round(circle.radius * 100.0) / 100.0));
        
        
        StructFieldType cx = new StructFieldType(centerPanel.xField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType cy = new StructFieldType(centerPanel.yField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType r = new StructFieldType(radiusField, VerificationType.POS_DOUBLE);
        fv.addField(cx);
        fv.addField(cy);
        fv.addField(r);
        centerPanel.xField.getDocument().addDocumentListener(new DocumentVerificationListener(cx, fv) );
        centerPanel.yField.getDocument().addDocumentListener(new DocumentVerificationListener(cy, fv) );
        radiusField.getDocument().addDocumentListener(new DocumentVerificationListener(r, fv) );
        
        add(nameLabel);
        add(nameField);
        add(centerLabel);
        add(centerPanel);
        add(radiusLabel);
        add(radiusField);
    }

    @Override
    public void commit() {
        circle.drawingInfo.name = nameField.getText();
        circle.center = new Point(Double.parseDouble(centerPanel.xField.getText()), Double.parseDouble(centerPanel.yField.getText()));
        circle.radius = Double.parseDouble(radiusField.getText());
    }
}
