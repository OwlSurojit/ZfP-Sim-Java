package eventListeners;

import drawing.DragPoint;
import drawing.DrawPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import zfP_Sim.BodyWindow;

public class DragDropListener implements MouseListener{
    
    BodyWindow main;
    DrawPanel drawPanel;
    public DragPoint lit;
    public boolean dragging;
    
    public DragDropListener(DrawPanel drawPanel, BodyWindow main){
        this.drawPanel = drawPanel;
        this.main = main;
        lit = null;
        dragging = false;
    }
    

    @Override
    public void mouseClicked(MouseEvent me) {
        for(DragPoint dp : drawPanel.main.body.dragPoints){
            if(dp.cont(me.getX(), me.getY(), 3)){
                if(dp == lit){
                    dp.next();
                    main.setLit(dp.bindings.get(dp.highlight_index).shape);
                }
                else{
                    if(lit != null){
                        lit.highlight = false;
                    }
                    dp.light();
                    lit = dp;
                    main.setLit(dp.bindings.get(dp.highlight_index).shape);
                }
                drawPanel.drawBody_Edit();
                return;
            }
        }
        if(lit != null){
            lit.highlight = false;
            lit = null;
        }
        main.setLit(null);
        drawPanel.drawBody_Edit();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if(lit!= null && lit.cont(me.getX(), me.getY(), 3)){
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if(dragging){
            dragging = false;
            if(!lit.cont(me.getX(), me.getY(), 3)){
                lit.bindings.get(lit.highlight_index).shape.refactor(lit.bindings.get(lit.highlight_index), me.getX(), me.getY());
                drawPanel.main.body.refreshDragPoints();
                lit = null;
                main.setLit(null);
                drawPanel.drawBody_Edit();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // Die Maus betritt das betreffende Objekt (hier das DrawPanel).
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // Die Maus verlässt das betreffende Objekt (hier das DrawPanel).
    }
    
    // Wichtige Infos:
    // Die Mouse-Buttons haben Nummern: 1-links, 2-mitte (Mausrad), 3-rechts
    // Aktuell gilt allgemein (Könnt ihr jederzeit ändern, solange ihr es auch im PolygonListener durchzieht):
      // links für Konstruktion
      // mitte für bestätigen, wo nicht offensichtlich (etwa ein offener Polygonzug)
      // rechts für abbrechen
    
    // Wann immer ihr Änderungen an eurem temporären Körper (den ihr während der Konstruktion anzeigt) macht, nutzt ihr "drawPanel.drawBody_Edit(ArrayList<ShapeBase> temp);"
    // Wenn ihr fertig seid und das Temp loswerden wollt:
    // drawPanel.main.body.addDefect(ShapeBase sb); (Um euren Körper dauerhaft zu speichern) (Es geht auch body.addOutline)
    // drawPanel.drawBody_Edit(); (Ohne das temp-Argument)
    
    // Welche Informationen ihr aus dem Mouseevent me abgreifen könnt, ist relativ umfangreich, dazu empfehle ich die Dokumentation.
    
}
