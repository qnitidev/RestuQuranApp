/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 1/4/2022.
 * All rights reserved.
 */

package com.mietrix.restu_digital_quran.utils.exceptions;

public class NoInternetException extends RuntimeException {
    public NoInternetException() {
        this("No internet");
    }

    public NoInternetException(String msg) {
        super(msg);
    }
}
