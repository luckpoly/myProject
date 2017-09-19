package com.goodfood86.tiaoshi.order121Project.verticalbanner;

/**
 * Description:
 * <p/>
 * Created by rowandjj(chuyi)<br/>
 * Date: 16/1/7<br/>
 * Time: 下午2:06<br/>
 */
public class Model01 {
    public String title;
    public String url;
    public String link;
    public String content;

    public Model01(String title, String url,String link) {
        this.title = title;
        this.url = url;
        this.link=link;
    }
    public Model01(String title, String url,String link,String content) {
        this.title = title;
        this.url = url;
        this.link=link;
        this.content=content;
    }
}
