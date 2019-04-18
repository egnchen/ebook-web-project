package com.eyek.ebook.facade;

import com.eyek.ebook.EbookApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerFacade {

    public static Logger logger = LoggerFactory.getLogger(EbookApplication.class);

    public static Logger getLogger() {
        return logger;
    }
}
