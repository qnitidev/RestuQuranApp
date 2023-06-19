/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 1/4/2022.
 * All rights reserved.
 */

package com.mietrix.restu_digital_quran.utils.exceptions;

public class HttpNotFoundException extends RuntimeException {
    public HttpNotFoundException() {
        this("Not found");
    }

    public HttpNotFoundException(String msg) {
        super(msg);
    }
}
