package eventListeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import properties.FieldVerifier;
import structures.StructFieldType;

public class DocumentVerificationListener implements DocumentListener{
    
    public StructFieldType field;
    public FieldVerifier fv;
    
    public DocumentVerificationListener(StructFieldType field, FieldVerifier fv){
        this.field = field;
        this.fv = fv;
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        fv.partialVerification(field);
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        fv.partialVerification(field);
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        fv.partialVerification(field);
    }
    
}
