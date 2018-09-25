package properties;

import shapesBase.*;

public class PropertiesTabbedPane extends javax.swing.JTabbedPane{
    public PropertiesTab constructionTab;
    public PropertiesTab drawingTab;
    
    public PropertiesTabbedPane(ShapeBase shape){
        if (shape instanceof Polygon){
            constructionTab = new PolygonTab((Polygon) shape);
        }
        else if(shape instanceof Circle){
            constructionTab = new CircleTab((Circle) shape);
        }
        else if(shape instanceof CircleArc){
            constructionTab = new CircleArcTab((CircleArc) shape);
        }
        else if (shape instanceof Oval){
            constructionTab = new OvalTab((Oval) shape);
        }
        else{
            constructionTab = new EmptyTab();
        }
        drawingTab = new DrawingTab(shape);
        addTab("Konstruktion", constructionTab);
        addTab("Darstellung", drawingTab);
    }
}
