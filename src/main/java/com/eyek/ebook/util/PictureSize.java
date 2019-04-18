package com.eyek.ebook.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PictureSize {
    static final Pattern matcherPattern = Pattern.compile("^\\s?(\\d+)\\s+(\\d+)\\s?$");
    private int w, h;

    public PictureSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return String.format("%d %d", w, h);
    }

    public static PictureSize fromString(String s) throws Exception {
        Matcher matcher = matcherPattern.matcher(s);
        if(!matcher.matches())
            throw new Exception(String.format("\"%s\" is not a valid PictureSize string.", s));
        return new PictureSize(
                Integer.valueOf(matcher.group(1)),
                Integer.valueOf(matcher.group(2)));
    }
}
