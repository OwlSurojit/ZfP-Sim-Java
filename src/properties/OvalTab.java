package properties;

import enums.VerificationType;
import eventListeners.DocumentVerificationListener;
import geometry.Point;
import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.Oval;
import structures.StructFieldType;

public class OvalTab extends PropertiesTab{
    public Oval oval;
    public FieldVerifier fv;
        
    public JLabel nameLabel; public JTextField nameField;
    public JLabel p1Label; public PointPanel p1Panel;
    public JLabel p2Label; public PointPanel p2Panel;
    public JLabel eLabel; public JTextField eField;
    
    public OvalTab(Oval oval, FieldVerifier fv){
        this.oval = oval;
        this.fv = fv;
        
        setLayout(new GridLayout(4, 2));
        
        nameLabel = new JLabel("Name");
        nameField = new JTextField(oval.drawingInfo.name);
        
        p1Label = new JLabel("Erster Brennpunkt");
        p1Panel = new PointPanel(oval.p1.x, oval.p1.y);
        
        p2Label = new JLabel("Zweiter Brennpunkt");
        p2Panel = new PointPanel(oval.p2.x, oval.p2.y);
        
        eLabel = new JLabel("Elongation");
        eField = new JTextField(Double.toString(oval.e));
        
        StructFieldType p1x = new StructFieldType(p1Panel.xField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType p1y = new StructFieldType(p1Panel.yField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType p2x = new StructFieldType(p2Panel.xField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType p2y = new StructFieldType(p2Panel.yField, VerificationType.NON_NEG_DOUBLE);
        StructFieldType e = new StructFieldType(eField, VerificationType.NON_NEG_DOUBLE);
        fv.addField(p1x);
        fv.addField(p1y);
        fv.addField(p2x);
        fv.addField(p2y);
        fv.addField(e);
        p1Panel.xField.getDocument().addDocumentListener(new DocumentVerificationListener(p1x, fv) );
        p1Panel.yField.getDocument().addDocumentListener(new DocumentVerificationListener(p1y, fv) );
        p2Panel.xField.getDocument().addDocumentListener(new DocumentVerificationListener(p2x, fv) );
        p2Panel.yField.getDocument().addDocumentListener(new DocumentVerificationListener(p2y, fv) );
        eField.getDocument().addDocumentListener(new DocumentVerificationListener(e, fv) );
        
        add(nameLabel);
        add(nameField);
        add(p1Label);
        add(p1Panel);
        add(p2Label);
        add(p2Panel);
        add(eLabel);
        add(eField);
    }

    @Override
    public void commit() {
        oval.drawingInfo.name = nameField.getText();
        Point np1 = new Point(Double.parseDouble(p1Panel.xField.getText()), Double.parseDouble(p1Panel.yField.getText()));
        Point np2 = new Point(Double.parseDouble(p2Panel.xField.getText()), Double.parseDouble(p2Panel.yField.getText()));
        double ne = Double.parseDouble(eField.getText());
        if(np1.dist(np2) < ne){
            oval.p1 = np1;
            oval.p2 = np2;
            oval.e = ne;
        }
    }
}
