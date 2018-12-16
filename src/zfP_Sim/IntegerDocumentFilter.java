/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zfP_Sim;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Joris
 */
public class IntegerDocumentFilter extends DocumentFilter {

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                try {
                    StringBuilder sb = new StringBuilder();
                    Document document = fb.getDocument();
                    sb.append(document.getText(0, offset));
                    sb.append(text);
                    sb.append(document.getText(offset, document.getLength()));

                    Integer.parseInt(sb.toString());
                    super.insertString(fb, offset, text, attr);
                } catch (NumberFormatException exp) {
                    System.err.println("Can not insert " + text + " into document");
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                if (length > 0) {
                    fb.remove(offset, length);
                }
                insertString(fb, offset, string, attr);
            }
        }
