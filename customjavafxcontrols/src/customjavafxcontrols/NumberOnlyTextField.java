package customjavafxcontrols;


import javafx.scene.control.TextField;

/**
 *
 * Thanks to: http://stackoverflow.com/a/18959399 Modified by Kevin Forest 22
 * March 2016 Added default value of 0.
 */
public class NumberOnlyTextField extends TextField {

    public NumberOnlyTextField() {
        super.setText("0");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
        if (super.getText().isEmpty()) {
            super.setText("0");
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }
}
