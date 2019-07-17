/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.helper;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarGenerator {

    private View view;

    public SnackBarGenerator(View view) {
        this.view = view;
    }

    public void generate(String message, int duration) {

        int dur;
        switch (duration) {
            case 1: dur = Snackbar.LENGTH_SHORT;
                    break;
            case 2: dur = Snackbar.LENGTH_LONG;
                    break;
            case 3: dur = Snackbar.LENGTH_INDEFINITE;
                    break;
            default: dur = Snackbar.LENGTH_LONG;
                     break;
        }
        Snackbar.make(view, message, dur).show();
    }
}
