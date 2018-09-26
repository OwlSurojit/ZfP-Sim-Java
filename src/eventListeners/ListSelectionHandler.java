/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventListeners;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import shapesBase.ShapeBase;
import zfP_Sim.BodyWindow;

public class ListSelectionHandler implements ListSelectionListener {
    
    public BodyWindow main;
    public JList parent;
    
    public ListSelectionHandler(BodyWindow main, JList parent){
        this.main = main;
        this.parent = parent;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        if (lsm.isSelectionEmpty()) {
            main.setLit(null);
        }
        else {
            int index = lsm.getMinSelectionIndex();
            main.setLit((ShapeBase) parent.getModel().getElementAt(index));
        }
    }
}
