package com.hx.base.util;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Ace
 * on 17/4/11.
 */

public class InputFilterMinMax implements InputFilter {

    private Double min, max;
    private int maxCount;

    public InputFilterMinMax(Double min, Double max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max, String maxCount) {
        this.min = Double.parseDouble(min);
        this.max = Double.parseDouble(max);
        this.maxCount = Integer.parseInt(maxCount);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {

            if (source.equals("-")&dest.toString().equals(""))
            {
                return"-";
            }
            else if (!dest.toString().contains("-")&source.toString().equals("-"))
            {
                String strInut = source.toString()+dest.toString();
                if (strInut.replace(".", "").replace("-", "").length() <= maxCount) {
                    Double input = Double.parseDouble(strInut);
                    if (isInRange(min, max, input))
                        return null;
                } else {
                    return "";
                }
            }
            else {
                String strInut = dest.toString() + source.toString();
                if (strInut.replace(".", "").replace("-", "").length() <= maxCount) {
                    Double input = Double.parseDouble(strInut);
                    if (isInRange(min, max, input))
                        return null;
                } else {
                    return "";
                }
            }
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(Double a, Double b, Double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
