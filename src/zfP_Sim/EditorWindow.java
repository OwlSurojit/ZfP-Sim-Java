package zfP_Sim;

import control.Body;
import drawing.DragPoint;
import eventListeners.*;
import java.awt.MouseInfo;
import java.awt.event.MouseListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import properties.PropertiesWindow;
import shapesBase.ShapeBase;
import properties.ShapesCellRenderer;

public class EditorWindow extends BodyWindow {
    
    MainWindow mainWindow;

    public EditorWindow(MainWindow mw) {
        mainWindow = mw;
        body = new Body(); body.exampleLongBar();
        
        initComponents();
        ListSelectionModel listSelectionModel = shapesList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionHandler(this, shapesList));
        drawPanel.main = this;
        drawPanel.drawBody_Edit();
        cursorToggleButton.doClick();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolButtonGroup = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        geomToolBar = new javax.swing.JToolBar();
        cursorToggleButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        polygonToggleButton = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        rectangleToggleButton = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        circleToggleButton = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        carcToggleButton = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        ovalToggleButton = new javax.swing.JToggleButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 32767));
        exactInputField = new javax.swing.JTextField();
        ReadInputButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jSplitPane2 = new javax.swing.JSplitPane();
        drawPanel = new drawing.DrawPanel();
        shapesScrollPane = new javax.swing.JScrollPane();
        shapesList = new javax.swing.JList<>();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        returnMenuItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Körpereditor");

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane1.setDividerLocation(40);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setEnabled(false);

        geomToolBar.setBorder(null);
        geomToolBar.setFloatable(false);
        geomToolBar.setRollover(true);
        geomToolBar.setMaximumSize(new java.awt.Dimension(1940, 40));
        geomToolBar.setPreferredSize(new java.awt.Dimension(1940, 40));

        toolButtonGroup.add(cursorToggleButton);
        cursorToggleButton.setText("Freie Maus");
        cursorToggleButton.setFocusable(false);
        cursorToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cursorToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cursorToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cursorToggleButtonStateChanged(evt);
            }
        });
        geomToolBar.add(cursorToggleButton);
        geomToolBar.add(jSeparator1);

        toolButtonGroup.add(polygonToggleButton);
        polygonToggleButton.setText("Polygon/Polyline");
        polygonToggleButton.setFocusable(false);
        polygonToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        polygonToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        polygonToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                polygonToggleButtonStateChanged(evt);
            }
        });
        geomToolBar.add(polygonToggleButton);
        geomToolBar.add(jSeparator2);

        toolButtonGroup.add(rectangleToggleButton);
        rectangleToggleButton.setText("Rechteck");
        rectangleToggleButton.setFocusable(false);
        rectangleToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rectangleToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rectangleToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rectangleToggleButtonStateChanged(evt);
            }
        });
        geomToolBar.add(rectangleToggleButton);
        geomToolBar.add(jSeparator3);

        toolButtonGroup.add(circleToggleButton);
        circleToggleButton.setText("Kreis");
        circleToggleButton.setFocusable(false);
        circleToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        circleToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        circleToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                circleToggleButtonStateChanged(evt);
            }
        });
        geomToolBar.add(circleToggleButton);
        geomToolBar.add(jSeparator4);

        toolButtonGroup.add(carcToggleButton);
        carcToggleButton.setText("Kreisbogen");
        carcToggleButton.setFocusable(false);
        carcToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carcToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        carcToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                carcToggleButtonStateChanged(evt);
            }
        });
        geomToolBar.add(carcToggleButton);
        geomToolBar.add(jSeparator5);

        toolButtonGroup.add(ovalToggleButton);
        ovalToggleButton.setText("Ellipse");
        ovalToggleButton.setFocusable(false);
        ovalToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ovalToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ovalToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ovalToggleButtonStateChanged(evt);
            }
        });
        geomToolBar.add(ovalToggleButton);
        geomToolBar.add(filler1);

        exactInputField.setPreferredSize(new java.awt.Dimension(150, 30));
        exactInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                exactInputFieldKeyPressed(evt);
            }
        });
        geomToolBar.add(exactInputField);

        ReadInputButton.setMnemonic('h');
        ReadInputButton.setText("Enter");
        ReadInputButton.setFocusable(false);
        ReadInputButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ReadInputButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ReadInputButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReadInputButtonMouseClicked(evt);
            }
        });
        ReadInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadInputButtonActionPerformed(evt);
            }
        });
        geomToolBar.add(ReadInputButton);
        geomToolBar.add(filler2);

        jSplitPane1.setTopComponent(geomToolBar);

        jSplitPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane2.setDividerLocation(140);
        jSplitPane2.setDividerSize(0);
        jSplitPane2.setEnabled(false);

        drawPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(drawPanel);

        shapesScrollPane.setName(""); // NOI18N
        shapesScrollPane.setPreferredSize(new java.awt.Dimension(200, 1040));

        shapesList.setModel(body.shapes);
        shapesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        shapesList.setCellRenderer(new ShapesCellRenderer());
        shapesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shapesListMouseClicked(evt);
            }
        });
        shapesScrollPane.setViewportView(shapesList);

        jSplitPane2.setLeftComponent(shapesScrollPane);

        jSplitPane1.setRightComponent(jSplitPane2);

        fileMenu.setText("Datei");

        returnMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
        returnMenuItem.setText("Return");
        returnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(returnMenuItem);

        menuBar.add(fileMenu);

        viewMenu.setText("Ansicht");

        jMenuItem2.setText("T.B.D.");
        viewMenu.add(jMenuItem2);

        menuBar.add(viewMenu);

        helpMenu.setText("Hilfe");

        jMenuItem1.setText("T.B.D.");
        helpMenu.add(jMenuItem1);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1920, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cursorToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cursorToggleButtonStateChanged
        if(cursorToggleButton.isSelected()){
            MouseListener[] listeners = drawPanel.getMouseListeners();
            if(listeners.length == 1){
                drawPanel.removeMouseListener(listeners[0]);
            }
            drawPanel.addMouseListener(new DragDropListener(drawPanel, this));
            body.refreshDragPoints();
            drawPanel.drawBody_Edit();
        }
    }//GEN-LAST:event_cursorToggleButtonStateChanged

    private void polygonToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_polygonToggleButtonStateChanged
        if(polygonToggleButton.isSelected()){
            MouseListener[] listeners = drawPanel.getMouseListeners();
            if(listeners.length == 1){
                drawPanel.removeMouseListener(listeners[0]);
            }
            drawPanel.addMouseListener(new PolygonCreateListener(drawPanel));
            body.refreshDragPoints();
            setLit(null);
            drawPanel.drawBody_Edit();
        }
    }//GEN-LAST:event_polygonToggleButtonStateChanged

    private void rectangleToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rectangleToggleButtonStateChanged
        if(rectangleToggleButton.isSelected()){
            MouseListener[] listeners = drawPanel.getMouseListeners();
            if(listeners.length == 1){
                drawPanel.removeMouseListener(listeners[0]);
            }
            drawPanel.addMouseListener(new RectangleCreateListener(drawPanel));
            body.refreshDragPoints();
            setLit(null);
            drawPanel.drawBody_Edit();
        }
    }//GEN-LAST:event_rectangleToggleButtonStateChanged

    private void circleToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_circleToggleButtonStateChanged
        if(circleToggleButton.isSelected()){
            MouseListener[] listeners = drawPanel.getMouseListeners();
            if(listeners.length == 1){
                drawPanel.removeMouseListener(listeners[0]);
            }
            drawPanel.addMouseListener(new CircleCreateListener(drawPanel));
            body.refreshDragPoints();
            setLit(null);
            drawPanel.drawBody_Edit();
        }
    }//GEN-LAST:event_circleToggleButtonStateChanged

    private void carcToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_carcToggleButtonStateChanged
        if(carcToggleButton.isSelected()){
            MouseListener[] listeners = drawPanel.getMouseListeners();
            if(listeners.length == 1){
                drawPanel.removeMouseListener(listeners[0]);
            }
            drawPanel.addMouseListener(new CircleArcCreateListener(drawPanel));
            body.refreshDragPoints();
            setLit(null);
            drawPanel.drawBody_Edit();
        }
    }//GEN-LAST:event_carcToggleButtonStateChanged

    private void returnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnMenuItemActionPerformed
        this.setVisible(false);
        mainWindow.body = this.body;
        mainWindow.setVisible(true);
    }//GEN-LAST:event_returnMenuItemActionPerformed

    private void shapesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shapesListMouseClicked
        if(evt.getButton() == 3){
            java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(p, shapesList);
            int index = shapesList.locationToIndex(p);
            
            if (index > -1){
                PropertiesWindow pw = new PropertiesWindow(shapesList.getModel().getElementAt(index), this);
                pw.setVisible(true);
            }
        }
    }//GEN-LAST:event_shapesListMouseClicked

    private void ReadInputButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReadInputButtonMouseClicked
        MouseListener listener = drawPanel.getMouseListeners()[0];
        if (listener instanceof PolygonCreateListener) {
            String[] input = exactInputField.getText().split(",");
            if (input.length == 2) {
                ((PolygonCreateListener)listener).exactInput(Double.parseDouble(input[0]), Double.parseDouble(input[1]));
            } else {
                ((PolygonCreateListener)listener).finish();
            }
        } else if (listener instanceof CircleCreateListener) {
            if (((CircleCreateListener)listener).center == null) {
                String[] input = exactInputField.getText().split(",");
                ((CircleCreateListener)listener).exactInput(Double.parseDouble(input[0]), Double.parseDouble(input[1]));
                
            } else {
                String input = exactInputField.getText();
                ((CircleCreateListener)listener).exactInput(Double.parseDouble(input));
            }
        } else if (listener instanceof CircleArcCreateListener) {
            String[] input = exactInputField.getText().split(",");
            ((CircleArcCreateListener) listener).exactInput(Double.parseDouble(input[0]), Double.parseDouble(input[1]));
        } else if (listener instanceof OvalCreateListener) {
            if (((OvalCreateListener)listener).P2 == null) {
                String[] input = exactInputField.getText().split(",");
                ((OvalCreateListener)listener).exactInput(Double.parseDouble(input[0]), Double.parseDouble(input[1]));
            } else {
                String input = exactInputField.getText();
                ((OvalCreateListener)listener).exactInput(Double.parseDouble(input));
            }
        } else if (listener instanceof RectangleCreateListener) {
            String[] input = exactInputField.getText().split(",");
            ((RectangleCreateListener) listener).exactInput(Double.parseDouble(input[0]), Double.parseDouble(input[1]));
        }
        exactInputField.setText("");
    }//GEN-LAST:event_ReadInputButtonMouseClicked

    private void exactInputFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exactInputFieldKeyPressed
        if (evt.getKeyCode() == 10) ReadInputButtonMouseClicked(null);
    }//GEN-LAST:event_exactInputFieldKeyPressed

    private void ReadInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReadInputButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReadInputButtonActionPerformed
    
    private void ovalToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {                                              
        if(ovalToggleButton.isSelected()){
            MouseListener[] listeners = drawPanel.getMouseListeners();
            if(listeners.length == 1){
                drawPanel.removeMouseListener(listeners[0]);
            }
            drawPanel.addMouseListener(new OvalCreateListener(drawPanel));
            body.refreshDragPoints();
            setLit(null);
            drawPanel.drawBody_Edit();
        }
    }                                             

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ReadInputButton;
    private javax.swing.JToggleButton carcToggleButton;
    private javax.swing.JToggleButton circleToggleButton;
    private javax.swing.JToggleButton cursorToggleButton;
    public drawing.DrawPanel drawPanel;
    private javax.swing.JTextField exactInputField;
    private javax.swing.JMenu fileMenu;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JToolBar geomToolBar;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JToggleButton ovalToggleButton;
    private javax.swing.JToggleButton polygonToggleButton;
    private javax.swing.JToggleButton rectangleToggleButton;
    private javax.swing.JMenuItem returnMenuItem;
    public javax.swing.JList<ShapeBase> shapesList;
    private javax.swing.JScrollPane shapesScrollPane;
    private javax.swing.ButtonGroup toolButtonGroup;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setLit(ShapeBase shape) {
        if(shape == null){
            shapesList.clearSelection();
            lit = null;
            if(drawPanel.getMouseListeners()[0] instanceof DragDropListener){
                DragDropListener ddl = (DragDropListener) drawPanel.getMouseListeners()[0];
                if(ddl.lit != null){
                    ddl.lit.highlight = false;
                    ddl.dragging = false;
                    ddl.lit = null;
                }
            }
        }
        else if(drawPanel.getMouseListeners()[0] instanceof DragDropListener){
            lit = shape;
            if(shapesList.getSelectedValue() != lit){
                for(int i = 0; i < shapesList.getModel().getSize(); i++){
                    if(shapesList.getModel().getElementAt(i) == lit){
                        shapesList.getSelectionModel().setSelectionInterval(i, i);
                        break;
                    }
                }
            }
            
            DragDropListener ddl = (DragDropListener) drawPanel.getMouseListeners()[0];
            if(ddl.lit != null && ddl.lit.bindings.get(ddl.lit.highlight_index).shape != shape){
                    cursorToggleButton.doClick();
            }
            else{
                drawPanel.drawBody_Edit();
            }
            
        }
        else{
            lit = shape;
            if(shapesList.getSelectedValue() != lit){
                for(int i = 0; i < shapesList.getModel().getSize(); i++){
                    if(shapesList.getModel().getElementAt(i) == lit){
                        shapesList.getSelectionModel().setSelectionInterval(i, i);
                        break;
                    }
                }
            }
            
            cursorToggleButton.doClick();
            
            
        }
        
    }
}
