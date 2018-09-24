package zfP_Sim;

import control.Body;
import drawing.DragPoint;
import eventListeners.*;
import java.awt.event.MouseListener;

public class EditorWindow extends BodyWindow {
    
    MainWindow mainWindow;

    public EditorWindow(MainWindow mw) {
        initComponents();
        mainWindow = mw;
        body = new Body(); body.exampleLongBar();
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
        jSplitPane2 = new javax.swing.JSplitPane();
        drawPanel = new drawing.DrawPanel();
        objToolBar = new javax.swing.JToolBar();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            .addGap(0, 1770, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 969, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(drawPanel);

        objToolBar.setBorder(null);
        objToolBar.setFloatable(false);
        objToolBar.setOrientation(javax.swing.SwingConstants.VERTICAL);
        objToolBar.setRollover(true);
        objToolBar.setMaximumSize(new java.awt.Dimension(140, 1040));
        objToolBar.setPreferredSize(new java.awt.Dimension(140, 1040));
        jSplitPane2.setLeftComponent(objToolBar);

        jSplitPane1.setRightComponent(jSplitPane2);

        fileMenu.setText("Datei");

        jMenuItem3.setText("Return");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem3);

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
            drawPanel.addMouseListener(new DragDropListener(drawPanel));
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
            drawPanel.drawBody_Edit();
        }
    }//GEN-LAST:event_carcToggleButtonStateChanged

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.setVisible(false);
        mainWindow.body = this.body;
        mainWindow.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void ovalToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ovalToggleButtonStateChanged
        if(ovalToggleButton.isSelected()){
            MouseListener[] listeners = drawPanel.getMouseListeners();
            if(listeners.length == 1){
                drawPanel.removeMouseListener(listeners[0]);
            }
            drawPanel.addMouseListener(new OvalCreateListener(drawPanel));
            body.refreshDragPoints();
            drawPanel.drawBody_Edit();
        }
    }//GEN-LAST:event_ovalToggleButtonStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton carcToggleButton;
    private javax.swing.JToggleButton circleToggleButton;
    private javax.swing.JToggleButton cursorToggleButton;
    private drawing.DrawPanel drawPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JToolBar geomToolBar;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JToolBar objToolBar;
    private javax.swing.JToggleButton ovalToggleButton;
    private javax.swing.JToggleButton polygonToggleButton;
    private javax.swing.JToggleButton rectangleToggleButton;
    private javax.swing.ButtonGroup toolButtonGroup;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables
}
