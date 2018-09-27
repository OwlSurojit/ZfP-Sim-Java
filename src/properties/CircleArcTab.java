package properties;

import enums.VerificationType;
import eventListeners.DocumentVerificationListener;
import geometry.Point;
import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.CircleArc;
import structures.StructFieldType;

public class CircleArcTab extends PropertiesTab{
    public CircleArc circleArc;
    public FieldVerifier fv;
    
    public JLabel nameLabel; public JTextField nameField;
    public JLabel centerLabel; public PointPanel centerPanel;
    public JLabel radiusLabel; public JTextField radiusField;
    public JLabel offsetLabel; public JTextField offsetField;
    public JLabel arcLabel; public JTextField arcField;
    
    public CircleArcTab(CircleArc circleArc, FieldVerifier fv){
        this.circleArc = circleArc;
        this.fv = fv;
        
        setLayout(new GridLayout(5, 2));
        
        nameLabel = new JLabel("Name");
        nameField = new JTextField(circleArc.drawingInfo.name);
        
        centerLabel = new JLabel("Mittelpunkt");
        centerPanel = new PointPanel(circleArc.center.x, circleArc.center.y);
        
        radiusLabel = new JLabel("Radius");
        radiusField = new JTextField(Double.toString(circleArc.radius));
        
        offsetLabel = new JLabel("Bogenanfang (in °)");
        offsetField = new JTextField(Double.toString(circleArc.offsetangle));
        
        arcLabel = new JLabel("Bogengröße (in °)");
        arcField = new JTextField(Double.toString(circleArc.arcangle));
        
        StructFieldType cx = new StructFieldType(centerPanel.xField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType cy = new StructFieldType(centerPanel.yField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType r = new StructFieldType(radiusField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType oa = new StructFieldType(offsetField, VerificationType.NON_ZERO_DOUBLE);
        StructFieldType aa = new StructFieldType(arcField, VerificationType.NON_ZERO_DOUBLE);
        fv.addField(cx);
        fv.addField(cy);
        fv.addField(r);
        fv.addField(oa);
        fv.addField(aa);
        centerPanel.xField.getDocument().addDocumentListener(new DocumentVerificationListener(cx, fv) );
        centerPanel.yField.getDocument().addDocumentListener(new DocumentVerificationListener(cy, fv) );
        radiusField.getDocument().addDocumentListener(new DocumentVerificationListener(r, fv) );
        offsetField.getDocument().addDocumentListener(new DocumentVerificationListener(oa, fv) );
        arcField.getDocument().addDocumentListener(new DocumentVerificationListener(aa, fv) );
        
        add(nameLabel);
        add(nameField);
        add(centerLabel);
        add(centerPanel);
        add(radiusLabel);
        add(radiusField);
        add(offsetLabel);
        add(offsetField);
        add(arcLabel);
        add(arcField);
    }

    @Override
    public void commit() {
        circleArc.drawingInfo.name = nameField.getText();
        circleArc.center = new Point(Double.parseDouble(centerPanel.xField.getText()), Double.parseDouble(centerPanel.yField.getText()));
        circleArc.radius = Double.parseDouble(radiusField.getText());
        circleArc.offsetangle = Double.parseDouble(offsetField.getText());
        circleArc.arcangle = Double.parseDouble(arcField.getText());
    }
}
