package structures;

import enums.VerificationType;
import javax.swing.JTextField;

public class StructFieldType {
    public JTextField field;
    public VerificationType type;
    
    public StructFieldType(JTextField field, VerificationType type){
        this.field = field;
        this.type = type;
    }
}
