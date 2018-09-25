package properties;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import shapesBase.ShapeBase;

public class ShapesCellRenderer extends JLabel implements ListCellRenderer<ShapeBase> {
    
    public ShapesCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ShapeBase> list,
                                                  ShapeBase value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        if(value.drawingInfo.name.isEmpty()){
            setText(value.toString());
        }
        else{
            setText(value.drawingInfo.name);
        }
        
        

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

        // check if this cell is selected
        } else if (isSelected) {
            background = Color.RED;
            foreground = Color.WHITE;

        // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        };

        setBackground(background);
        setForeground(foreground);

        return this;
    }
 }