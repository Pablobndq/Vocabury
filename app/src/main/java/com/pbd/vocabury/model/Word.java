package com.pbd.vocabury.model;

public class Word {

    private int id;
    private int languageId;

    private String spanish;
    private String foreign;

    public Word() {
    }

    public Word(int languageId, String spanish, String foreign) {
        this.languageId = languageId;
        this.spanish = spanish;
        this.foreign = foreign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getSpanish() {
        return spanish;
    }

    public void setSpanish(String spanish) {
        this.spanish = spanish;
    }

    public String getForeign() {
        return foreign;
    }

    public void setForeign(String foreign) {
        this.foreign = foreign;
    }

    @Override
    public String toString() {
        return spanish + " → " + foreign;
    }
}
