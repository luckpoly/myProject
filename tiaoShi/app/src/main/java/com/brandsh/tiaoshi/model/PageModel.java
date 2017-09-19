package com.brandsh.tiaoshi.model;

/**
 * Created by libokang on 15/10/26.
 */
public class PageModel {

    private Integer limit;
    private String prePage;
    private String start;
    private Integer currentPage;
    private String nextPage;
    private Integer count;
    private String list;


    public boolean hasNextPage() {
        int maxPage = (count % limit == 0 ? count / limit : count / limit + 1);
        return currentPage < maxPage;
    }


    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
