package properties;

import enums.VerificationType;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JTextField;
import structures.StructFieldType;

public class FieldVerifier {
    public JComponent controlled;
    public ArrayList<StructFieldType> fields;
    
    public FieldVerifier(JComponent controlled){
        this.controlled = controlled;
        fields = new ArrayList<>();
    }
    
    public FieldVerifier(JComponent controlled, ArrayList<StructFieldType> fields){
        this.controlled = controlled;
        this.fields = fields;
        fullVerification();
    }
    
    public void addField(StructFieldType field){
        partialVerification(field);
        fields.add(field);
    }
    
    public void addField(JTextField tfield, VerificationType type){
        StructFieldType field = new StructFieldType(tfield, type);
        partialVerification(field);
        fields.add(field);
    }

    public void partialVerification(StructFieldType field) {
        if(controlled.isEnabled() ){
            if(!verify(field)){
                controlled.setEnabled(false);
            }
        }
        else{
            if(verify(field)){
                fullVerification();
            }
        }
    }

    public void fullVerification() {
        if(controlled.isEnabled()){
            for(StructFieldType field : fields){
                if(!verify(field)){
                    controlled.setEnabled(false);
                    break;
                }
            }
        }
        else{
            for(StructFieldType field : fields){
                if(!verify(field)){
                    return;
                }
            }
            controlled.setEnabled(true);
        }
    }
    
    public boolean verify(StructFieldType field){
        switch(field.type){
            case NON_NEG_DOUBLE:
                try{
                    double d = Double.parseDouble(field.field.getText());
                    return (d >= 0);
                }
                catch(Exception e){
                    return false;
                }
            case POS_DOUBLE:
                try{
                    double d = Double.parseDouble(field.field.getText());
                    return (d > 0);
                }
                catch(Exception e){
                    return false;
                }
            case NON_ZERO_DOUBLE:
                try{
                    double d = Double.parseDouble(field.field.getText());
                    return (d != 0);
                }
                catch(Exception e){
                    return false;
                }
            case NON_NEG_INTEGER:
                try{
                    int i = Integer.parseInt(field.field.getText());
                    return (i >= 0);
                }
                catch(Exception e){
                    return false;
                }
            case POS_INTEGER:
                try{
                    int i = Integer.parseInt(field.field.getText());
                    return (i > 0);
                }
                catch(Exception e){
                    return false;
                }
            default:
                return false;
        }
    }
}
