package properties;

import shapesBase.*;

public class PropertiesTabbedPane extends javax.swing.JTabbedPane{
    public PropertiesTab constructionTab;
    public PropertiesTab drawingTab;
    
    public PropertiesTabbedPane(ShapeBase shape){
        if(shape instanceof Line){
            constructionTab = new EmptyTab(shape);
        }
        else if (shape instanceof Polygon){
            constructionTab = new EmptyTab(shape);
        }
        else if(shape instanceof Circle){
            constructionTab = new EmptyTab(shape);
        }
        else if(shape instanceof CircleArc){
            constructionTab = new EmptyTab(shape);
        }
        else if (shape instanceof Oval){
            constructionTab = new EmptyTab(shape);
        }
        else{
            constructionTab = new EmptyTab(shape);
        }
        drawingTab = new DrawingTab(shape);
        addTab("Konstruktion", constructionTab);
        addTab("Darstellung", drawingTab);
    }
}
