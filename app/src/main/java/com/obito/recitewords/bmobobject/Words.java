package com.obito.recitewords.bmobobject;

import cn.bmob.v3.BmobObject;

/**
 * Created by obito on 17-8-23.
 */

public class Words extends BmobObject {
    private String paraphrase;//单词的意思
    private String words;//单词
    private String note;//注释
    private String imei;//手机串号 标识不同用户

    public Words() {

    }

    public Words(String paraphrase, String words, String note, String imei) {
        this.paraphrase = paraphrase;
        this.words = words;
        this.note = note;
        this.imei = imei;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
