package dev.jmvg.imsystem.util;

import com.jfoenix.controls.JFXTextField;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class TextFieldFormatter {
    private final MaskFormatter mf;
    private JFXTextField tf;
    private String CaracteresValidos;
    private String mask;

    public TextFieldFormatter() {
        mf = new MaskFormatter();
    }

    public void formatter(JFXTextField tf, String CaracteresValidos, String mask) {
        try {
            mf.setMask(mask);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        mf.setValidCharacters(CaracteresValidos);
        mf.setValueContainsLiteralCharacters(false);
        String text = tf.getText().replaceAll("[\\W]", "");

        boolean repetir = true;
        while (repetir) {

            char ultimoCaractere;

            if (text.equals("")) {
                break;
            } else {
                ultimoCaractere = text.charAt(text.length() - 1);
            }

            try {
                text = mf.valueToString(text);
                repetir = false;
            } catch (ParseException ex) {
                text = text.replace(ultimoCaractere + "", "");
                repetir = true;
            }

        }

        tf.setText(text);

        if (!text.equals("")) {
            for (int i = 0; tf.getText().charAt(i) != ' ' && i < tf.getLength() - 1; i++) {
                tf.forward();
            }
        }
    }

    public void formatter(){
        formatter(this.tf, this.CaracteresValidos, this.mask);
    }

    /**
     * @return the tf
     */
    public JFXTextField getTf() {
        return tf;
    }

    /**
     * @param tf the tf to set
     */
    public void setTf(JFXTextField tf) {
        this.tf = tf;
    }

    /**
     * @return the CaracteresValidos
     */
    public String getCaracteresValidos() {
        return CaracteresValidos;
    }

    /**
     * @param CaracteresValidos the CaracteresValidos to set
     */
    public void setCaracteresValidos(String CaracteresValidos) {
        this.CaracteresValidos = CaracteresValidos;
    }

    public String getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(String mask) {
        this.mask = mask;
    }
}
