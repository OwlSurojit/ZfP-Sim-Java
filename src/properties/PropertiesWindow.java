package properties;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import shapesBase.ShapeBase;
import zfP_Sim.EditorWindow;

public class PropertiesWindow extends javax.swing.JFrame{
    public ShapeBase shape;
    public EditorWindow main;
    
    public PropertiesTabbedPane tabs;
    public JPanel lowerPanel;
    public javax.swing.JButton saveButton;
    public javax.swing.JButton cancelButton;
    
    public PropertiesWindow(ShapeBase shape, EditorWindow main){
        this.shape = shape;
        this.main = main;
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setTitle("Eigenschaften");
        setSize(600, 280);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(1, 2));
        lowerPanel.setSize(600, 30);
        
        saveButton = new JButton("Übernehmen");
        saveButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            saveButtonActionPerformed(evt);
        });
        
        cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            cancelButtonActionPerformed(evt);
        });
        
        lowerPanel.add(saveButton);
        lowerPanel.add(cancelButton);
        
        FieldVerifier fv = new FieldVerifier(saveButton);
        
        tabs = new PropertiesTabbedPane(shape, fv);
        tabs.setSize(600, 250);
        
        add(tabs);
        add(lowerPanel);
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        tabs.constructionTab.commit();
        tabs.drawingTab.commit();
        
        main.body.refreshDragPoints();
        if(main.body.outline.contains(shape)){
            main.outlineChanged();
        }
        main.drawPanel.drawBody_Edit();
        main.shapesList.updateUI();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
