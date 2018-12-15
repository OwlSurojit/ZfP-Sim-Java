package zfP_Sim;

import control.Body;
import drawing.DragPoint;
import static enums.VerificationType.*;
import eventListeners.*;
import java.awt.Desktop;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import properties.FieldVerifier;
import properties.PropertiesWindow;
import shapesBase.ShapeBase;
import properties.ShapesCellRenderer;
import structures.StructFieldType;

public class EditorWindow extends BodyWindow {
    
    MainWindow mainWindow;
    public int rotationSpeed;

    public EditorWindow(MainWindow mw) {
        mainWindow = mw;
        body = new Body(); body.exampleLongBar();

        initComponents();
        ListSelectionModel listSelectionModel = shapesList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionHandler(this, shapesList));
        drawPanel.main = this;
        drawPanel.drawBody_Edit();
        cursorToggleButton.setSelected(true);
        rotationSpeed = 1;
        
        //KeyEventListeners
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent evt) {
                if(drawPanel.main.isFocused()){
                    int type = evt.getID();
                    if(type == KeyEvent.KEY_PRESSED){
                        int keyCode = evt.getKeyCode();
                        switch(keyCode){
                            case KeyEvent.VK_RIGHT:
                                MouseListener[] listenersR = drawPanel.getMouseListeners();
                                if(listenersR.length == 1 && listenersR[0] instanceof DragDropListener && lit != null){
                                    lit.rotate(getRotationSpeed());
                                    body.refreshDragPoints();
                                    if( body.outline.contains(lit) ){
                                        outlineChanged();
                                    }
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_LEFT:
                                MouseListener[] listenersL = drawPanel.getMouseListeners();
                                if(listenersL.length == 1 && listenersL[0] instanceof DragDropListener && lit != null){
                                    lit.rotate(-getRotationSpeed());
                                    body.refreshDragPoints();
                                    if( body.outline.contains(lit) ){
                                        outlineChanged();
                                    }
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_DELETE:
                                MouseListener[] listenersD = drawPanel.getMouseListeners();
                                if(listenersD.length == 1 && listenersD[0] instanceof DragDropListener && lit != null){
                                    if(body.removeOutline(lit)){
                                        outlineChanged();
                                    }
                                    else{
                                        body.removeDefect(lit);
                                    }
                                    setLit(null);
                                    body.refreshDragPoints();
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_P:
                                if(lit != null){
                                    PropertiesWindow pw = new PropertiesWindow(lit, (EditorWindow) drawPanel.main);
                                    pw.setVisible(true);
                                    body.refreshDragPoints();
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_F1:
                                cursorToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F2:
                                polygonToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F3:
                                rectangleToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F4:
                                circleToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F5:
                                carcToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F6:
                                ovalToggleButton.setSelected(true);
                                return false;
                            default:
                                return false;
                        }
                    }
                    else if(type == KeyEvent.KEY_RELEASED){
                        rotationSpeed = 1;
                        return false;
                    }
                }
                return false;
            }
        });
    }
    
    public EditorWindow(MainWindow mw, Body _body) {
        mainWindow = mw;
        body = _body;

        initComponents();
        ListSelectionModel listSelectionModel = shapesList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionHandler(this, shapesList));
        drawPanel.main = this;
        drawPanel.drawBody_Edit();
        cursorToggleButton.setSelected(true);
        rotationSpeed = 1;
        
        //KeyEventListeners
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent evt) {
                if(drawPanel.main.isFocused()){
                    int type = evt.getID();
                    if(type == KeyEvent.KEY_PRESSED){
                        int keyCode = evt.getKeyCode();
                        switch(keyCode){
                            case KeyEvent.VK_RIGHT:
                                MouseListener[] listenersR = drawPanel.getMouseListeners();
                                if(listenersR.length == 1 && listenersR[0] instanceof DragDropListener && lit != null){
                                    lit.rotate(getRotationSpeed());
                                    body.refreshDragPoints();
                                    if( body.outline.contains(lit) ){
                                        outlineChanged();
                                    }
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_LEFT:
                                MouseListener[] listenersL = drawPanel.getMouseListeners();
                                if(listenersL.length == 1 && listenersL[0] instanceof DragDropListener && lit != null){
                                    lit.rotate(-getRotationSpeed());
                                    body.refreshDragPoints();
                                    if( body.outline.contains(lit) ){
                                        outlineChanged();
                                    }
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_DELETE:
                                MouseListener[] listenersD = drawPanel.getMouseListeners();
                                if(listenersD.length == 1 && listenersD[0] instanceof DragDropListener && lit != null){
                                    if(body.removeOutline(lit)){
                                        outlineChanged();
                                    }
                                    else{
                                        body.removeDefect(lit);
                                    }
                                    setLit(null);
                                    body.refreshDragPoints();
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_P:
                                if(lit != null){
                                    PropertiesWindow pw = new PropertiesWindow(lit, (EditorWindow) drawPanel.main);
                                    pw.setVisible(true);
                                    body.refreshDragPoints();
                                    drawPanel.drawBody_Edit();
                                }
                                return false;
                            case KeyEvent.VK_F1:
                                cursorToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F2:
                                polygonToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F3:
                                rectangleToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F4:
                                circleToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F5:
                                carcToggleButton.setSelected(true);
                                return false;
                            case KeyEvent.VK_F6:
                                ovalToggleButton.setSelected(true);
                                return false;
                            default:
                                return false;
                        }
                    }
                    else if(type == KeyEvent.KEY_RELEASED){
                        rotationSpeed = 1;
                        return false;
                    }
                }
                return false;
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolButtonGroup = new javax.swing.ButtonGroup();
        addButtonGroup = new javax.swing.ButtonGroup();
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
        jSeparator6 = new javax.swing.JToolBar.Separator();
        outlineAddRadioButton = new javax.swing.JRadioButton();
        defectAddRadioButton = new javax.swing.JRadioButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 32767));
        exactInputField1 = new javax.swing.JTextField();
        exactInputField2 = new javax.swing.JTextField();
        ReadInputButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jSplitPane2 = new javax.swing.JSplitPane();
        drawPanel = new drawing.DrawPanel();
        shapesScrollPane = new javax.swing.JScrollPane();
        shapesList = new javax.swing.JList<>();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        returnMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpPDFMenuItem = new javax.swing.JMenuItem();

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
        geomToolBar.add(jSeparator6);

        addButtonGroup.add(outlineAddRadioButton);
        outlineAddRadioButton.setSelected(true);
        outlineAddRadioButton.setText("Outline");
        outlineAddRadioButton.setFocusable(false);
        outlineAddRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        outlineAddRadioButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        geomToolBar.add(outlineAddRadioButton);

        addButtonGroup.add(defectAddRadioButton);
        defectAddRadioButton.setText("Defekte");
        defectAddRadioButton.setFocusable(false);
        defectAddRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        defectAddRadioButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        geomToolBar.add(defectAddRadioButton);
        geomToolBar.add(filler1);

        exactInputField1.setPreferredSize(new java.awt.Dimension(150, 30));
        exactInputField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                exactInputField1KeyPressed(evt);
            }
        });
        geomToolBar.add(exactInputField1);

        exactInputField2.setPreferredSize(new java.awt.Dimension(150, 30));
        exactInputField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                exactInputField2KeyPressed(evt);
            }
        });
        geomToolBar.add(exactInputField2);

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
        returnMenuItem.setText("Übernehmen");
        returnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(returnMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText("Hilfe");

        helpPDFMenuItem.setText("PDF");
        helpPDFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpPDFMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpPDFMenuItem);

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
            exactInputField1.setEditable(false);
            exactInputField2.setEditable(false);
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
            exactInputField1.setEditable(true);
            exactInputField2.setEditable(true);
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
            exactInputField1.setEditable(true);
            exactInputField2.setEditable(true);
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
            exactInputField1.setEditable(true);
            exactInputField2.setEditable(true);
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
            exactInputField1.setEditable(true);
            exactInputField2.setEditable(true);
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
    
    private void ovalToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {                                              
        if(ovalToggleButton.isSelected()){
            exactInputField1.setEditable(true);
            exactInputField2.setEditable(true);
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

    private void returnMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnMenuItemActionPerformed
        this.setVisible(false);
        mainWindow.body = this.body;
        mainWindow.getSenderPositions();
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
            String input1 = exactInputField1.getText();
            String input2 = exactInputField2.getText();
            if (input1 != "" && input2 != "") {
                ((PolygonCreateListener)listener).exactInput(Double.parseDouble(input1), Double.parseDouble(input2));
            } else {
                ((PolygonCreateListener)listener).finish();
            }
        } else if (listener instanceof CircleCreateListener) {
            if (((CircleCreateListener)listener).center == null) {
                String input1 = exactInputField1.getText();
                String input2 = exactInputField2.getText();
                ((CircleCreateListener)listener).exactInput(Double.parseDouble(input1), Double.parseDouble(input2));
                exactInputField2.setEditable(false);
            } else {
                String input = exactInputField1.getText();
                ((CircleCreateListener)listener).exactInput(Double.parseDouble(input));
                exactInputField2.setEditable(true);
            }
        } else if (listener instanceof CircleArcCreateListener) {
            String input1 = exactInputField1.getText();
            String input2 = exactInputField2.getText();
            ((CircleArcCreateListener) listener).exactInput(Double.parseDouble(input1), Double.parseDouble(input2));
        } else if (listener instanceof OvalCreateListener) {
            if (((OvalCreateListener)listener).P2 == null) {
                String input1 = exactInputField1.getText();
                String input2 = exactInputField2.getText();
                ((OvalCreateListener)listener).exactInput(Double.parseDouble(input1), Double.parseDouble(input2));
                if (((OvalCreateListener)listener).P2 != null) exactInputField2.setEditable(false);
            } else {
                String input = exactInputField1.getText();
                ((OvalCreateListener)listener).exactInput(Double.parseDouble(input));
                exactInputField2.setEditable(true);
            }
        } else if (listener instanceof RectangleCreateListener) {
            String input1 = exactInputField1.getText();
            String input2 = exactInputField2.getText();
            ((RectangleCreateListener) listener).exactInput(Double.parseDouble(input1), Double.parseDouble(input2));
        }
        exactInputField1.setText("");
        exactInputField2.setText("");
        exactInputField1.requestFocus();
    }//GEN-LAST:event_ReadInputButtonMouseClicked

    private void helpPDFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpPDFMenuItemActionPerformed
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("resources/help.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        } 
    }//GEN-LAST:event_helpPDFMenuItemActionPerformed

    private void exactInputField1KeyPressed(java.awt.event.KeyEvent evt) {                                            
        if (evt.getKeyCode() == 10) ReadInputButtonMouseClicked(null);
    }
  
    private void exactInputField2KeyPressed(java.awt.event.KeyEvent evt) {                                            
        if (evt.getKeyCode() == 10) ReadInputButtonMouseClicked(null);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ReadInputButton;
    private javax.swing.ButtonGroup addButtonGroup;
    private javax.swing.JToggleButton carcToggleButton;
    private javax.swing.JToggleButton circleToggleButton;
    private javax.swing.JToggleButton cursorToggleButton;
    public javax.swing.JRadioButton defectAddRadioButton;
    public drawing.DrawPanel drawPanel;
    private javax.swing.JTextField exactInputField1;
    private javax.swing.JTextField exactInputField2;
    private javax.swing.JMenu fileMenu;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JToolBar geomToolBar;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpPDFMenuItem;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JMenuBar menuBar;
    public javax.swing.JRadioButton outlineAddRadioButton;
    private javax.swing.JToggleButton ovalToggleButton;
    private javax.swing.JToggleButton polygonToggleButton;
    private javax.swing.JToggleButton rectangleToggleButton;
    private javax.swing.JMenuItem returnMenuItem;
    public javax.swing.JList<ShapeBase> shapesList;
    private javax.swing.JScrollPane shapesScrollPane;
    private javax.swing.ButtonGroup toolButtonGroup;
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
    
    public int getRotationSpeed() {
        if(rotationSpeed < 15){
            return ++rotationSpeed;
        }
        else{return rotationSpeed;}
    }
    
    @Override
    public void outlineChanged(){
        
    }
}
