package com.acl.utils.mongo;

import java.util.List;


	/** 
	 * @author: chenwei
	 * @version：1.0 
	 * @创建时间：2016年7月28日 下午2:47:31 
	 * @说明：mongdb分页类
	 *
	 */ 
public class Pagination<T> {

    /**
     * 一页数据默认20条
     */
    private int pageSize = 20;
    /**
     * 当前页码
     */
    private int pageNo;

    /**
     * 上一页
     */
    private int upPage;

    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 一共有多少条数据
     */
    private long total;

    /**
     * 一共有多少页
     */
    private long totalPage;
    /**
     * 数据集合
     */
    private List<T> rows;

    /**
     * 分页的url
     */
    private String pageUrl;

    /**
     * 获取第一条记录位置
     * 
     * @return
     */
    public int getFirstResult() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }

    /**
     * 获取最后记录位置
     * 
     * @return
     */
    public int getLastResult() {
        return this.getPageNo() * this.getPageSize();
    }

    /**
     * 计算一共多少页
     */
    public void setTotalPage() {
        this.totalPage = (int) ((this.total % this.pageSize > 0) ? (this.total / this.pageSize + 1)
                : this.total / this.pageSize);
    }

    /**
     * 设置 上一页
     */
    public void setUpPage() {
        this.upPage = (this.pageNo > 1) ? this.pageNo - 1 : this.pageNo;
    }

    /**
     * 设置下一页
     */
    public void setNextPage() {
        this.nextPage = (this.pageNo == this.totalPage) ? this.pageNo : this.pageNo + 1;
    }

    public int getNextPage() {
        return nextPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public int getUpPage() {
        return upPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Pagination(int pageNo, int pageSize, long total) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
        this.setTotal(total);
        this.init();
    }

    /** 
     * 处理查询后的结果数据 
     *  
     * @param items 
     *            查询结果集 
     * @param count 
     *            总数 
     */  
//    public void build(List<T> items) {  
//        this.setDatas(items);  
//        long count =  this.getTotalCount();  
//        long divisor = count / this.getPageSize();  
//        long remainder = count % this.getPageSize();  
//        this.setTotalPage(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);  
//    }  
    
    /**
     * 初始化计算分页
     */
    private void init() {
        this.setTotalPage();// 设置一共页数
        this.setUpPage();// 设置上一页
        this.setNextPage();// 设置下一页
    }
    
    
}