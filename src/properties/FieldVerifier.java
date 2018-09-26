package properties;

import java.util.ArrayList;
import javax.swing.JComponent;
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

    public void partialVerification(StructFieldType field) {
        if(controlled.isEnabled() ){
            if(!verify(field)){
                controlled.setEnabled(false);
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
            // add more cases
            default:
                return false;
        }
    }
}
