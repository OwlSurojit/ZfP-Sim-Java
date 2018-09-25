package properties;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import shapesBase.ShapeBase;
import structures.StructDrawingInfo;
import zfP_Sim.EditorWindow;

public class PropertiesWindow extends javax.swing.JFrame{
    public ShapeBase shape;
    public EditorWindow main;
    
    public javax.swing.JLabel nameLabel;
    public javax.swing.JLabel lineColorLabel;
    public javax.swing.JLabel lineColorLitLabel;
    public javax.swing.JLabel fillLabel;
    public javax.swing.JLabel fillColorLabel;
    public javax.swing.JButton saveButton;
    
    public javax.swing.JTextField nameField;
    public javax.swing.JButton lineColorButton;
    public javax.swing.JButton lineColorLitButton;
    public javax.swing.JCheckBox fillCheckBox;
    public javax.swing.JButton fillColorButton;
    public javax.swing.JButton cancelButton;
    
    public PropertiesWindow(ShapeBase shape, EditorWindow main){
        this.shape = shape;
        this.main = main;
        
        setLayout(new GridLayout(6, 2));
        setTitle("Eigenschaften");
        setSize(600, 300);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        /*NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        new JFormattedTextField(formatter);*/
        
        nameLabel = new JLabel("Name");
        lineColorLabel = new JLabel("Linienfarbe");
        lineColorLitLabel = new JLabel("Linienfarbe [markiert]");
        fillLabel = new JLabel("Füllen");
        fillColorLabel = new JLabel("Füllfarbe");
        
        saveButton = new JButton("Übernehmen");
        saveButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            saveButtonActionPerformed(evt);
        });
        
        nameField = new JTextField(shape.drawingInfo.name);
        
        lineColorButton = new JButton("");
        lineColorButton.setBackground(shape.drawingInfo.lineColor);
        lineColorButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            lineColorButtonActionPerformed(evt);
        });
        
        lineColorLitButton = new JButton("");
        lineColorLitButton.setBackground(shape.drawingInfo.lineColorLit);
        lineColorLitButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            lineColorLitButtonActionPerformed(evt);
        });
        
        fillCheckBox = new JCheckBox("");
        fillCheckBox.setSelected(shape.drawingInfo.fill);
        
        fillColorButton = new JButton("");
        fillColorButton.setBackground(shape.drawingInfo.fillColor);
        fillColorButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            fillColorButtonActionPerformed(evt);
        });
        
        cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            cancelButtonActionPerformed(evt);
        });
        
        add(nameLabel);
        add(nameField);
        add(lineColorLabel);
        add(lineColorButton);
        add(lineColorLitLabel);
        add(lineColorLitButton);
        add(fillLabel);
        add(fillCheckBox);
        add(fillColorLabel);
        add(fillColorButton);
        add(saveButton);
        add(cancelButton);
        
    }
    
    private void lineColorButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        lineColorButton.setBackground(JColorChooser.showDialog(
                     this,
                     "Choose Background Color",
                     lineColorButton.getBackground()));
    }
    
    private void lineColorLitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        lineColorLitButton.setBackground(JColorChooser.showDialog(
                     this,
                     "Choose Background Color",
                     lineColorLitButton.getBackground()));
    }
    
    private void fillColorButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        fillColorButton.setBackground(JColorChooser.showDialog(
                     this,
                     "Choose Background Color",
                     fillColorButton.getBackground()));
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        shape.drawingInfo.name = nameField.getText();
        shape.drawingInfo.lineColor = lineColorButton.getBackground();
        shape.drawingInfo.lineColorLit = lineColorLitButton.getBackground();
        shape.drawingInfo.fill = fillCheckBox.isSelected();
        shape.drawingInfo.fillColor = fillColorButton.getBackground();
        
        main.drawPanel.drawBody_Edit();
        main.shapesList.updateUI();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}