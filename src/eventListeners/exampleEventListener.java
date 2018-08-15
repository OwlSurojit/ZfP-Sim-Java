package eventListeners;

import geometry.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JToggleButton;
import shapesBase.*;
import drawing.DrawPanel;

// Scahut euch hierzu auch den PolygonCreateListener an.
public class exampleEventListener implements MouseListener{

    // Hier alle zwischen Events zu speichernden Daten, wie etwa die bisherigen Punkte bei einem Polygon
    DrawPanel drawPanel; // Das DrawPanel, das überwacht wird
    
    public exampleEventListener(DrawPanel drawPanel){
        this.drawPanel = drawPanel;
    }
    

    @Override
    public void mouseClicked(MouseEvent me) {
        // Eine Maustaste wird geklickt
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // ... gehalten.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // ... losgelassen
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
    // drawPanel.main.body.addDefect(ShapeBase sb); (oder addOutline)
    // drawPanel.drawBody_Edit();
    
    // Welche Informationen ihr aus dem Mouseevent me abgreifen könnt, ist relativ umfangreich, dazu empfehle ich die Dokumentation.
    
}
