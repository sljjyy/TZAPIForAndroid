package com.tianzunchina.android.api.widget.form;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * admin
 * 2016/6/21 0021.
 */

public class FormItemTextWatcher implements TextWatcher {
    private TextInputLayout mTextInputLayout;
    private String errorMessage;

    protected FormItemTextWatcher(@NonNull final TextInputLayout textInputLayout, @NonNull final String errorMessage) {
        this.mTextInputLayout = textInputLayout;
        this.errorMessage = errorMessage;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
