package properties;

import shapesBase.ShapeBase;

public class EmptyTab extends PropertiesTab {
    
    public EmptyTab(ShapeBase shape){
        this.shape = shape;
    }
    
    @Override
    public void commit() {
        
    }
    
}
