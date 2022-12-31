package com.nor.qldiemdanh.common;

import android.widget.EditText;

import com.nor.qldiemdanh.R;

import java.util.regex.Pattern;

public class StringUtils {
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public static boolean isEmpty(EditText... editTexts) {
        boolean isEmpty = false;
        for (EditText edt : editTexts) {
            if (edt.getText().toString().isEmpty()) {
                isEmpty = true;
                edt.setError(edt.getContext().getResources().getString(R.string.data_empty));
            }
        }
        return isEmpty;
    }


    public static boolean isEmail(EditText edt){
        boolean check = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE)
                .matcher(edt.getText().toString()).matches();
        if (!check){
            edt.setError(edt.getContext().getResources().getString(R.string.email_invalid));
        }
        return check;
    }
}
