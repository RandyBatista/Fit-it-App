package edu.wit.mobileapp.fit_it_app;
import java.text.DecimalFormat;

public class Size {
    double min;
    double max;

    private static DecimalFormat f = new DecimalFormat("##.00");

    public Size(double min, double max){
        this.min = Double.parseDouble(f.format(min));
        this.max = Double.parseDouble(f.format(max));
    }

    public Size toInches(){
        double converter = 0.3937008;
        return new Size(min*converter, max*converter);
    }

}
