package com.eyek.ebook.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerFacade {

    public static Logger getLogger(Object object) {
        return LoggerFactory.getLogger(object.getClass().getName());
    }
}
