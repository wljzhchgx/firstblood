/**
 * 
 */
package cn.net.firstblood.dal.param;

import java.util.List;

import cn.net.firstblood.dal.enums.DirType;

/**
 * @author gangxiang.chengx
 *
 */
public class PageParam<E>{

	/**
	 * 默认页大小 
	 */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 最大页大小
     */
    public static final int MAX_PAGE_SIZE = 3000;

    /**
     * 页大小
     */
    protected int pageSize;
    
    /**
     * 总记录数
     */
    private int totalRecord;
    
    /**
     * 当前页
     */
    private int pageIndex;

    /**
     * 总页数
     */
    private int totalPage;
    
    /**
     * 返回记录
     */
    private List<E> data;
    
    /**
	 * 排序字段
	 */
	private String orderField;

	/**
	 * 排序方式
	 */
	private DirType dir;


    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if(pageSize > MAX_PAGE_SIZE){
        	throw new RuntimeException("pageSize too large");
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalPage = (totalRecord + getPageSize() - 1) / getPageSize();
        this.totalRecord = totalRecord;
    }

    public int getPageIndex() {
        if (pageIndex <= 1) {
            pageIndex = 1;
        } else if (pageIndex > getTotalPage() && getTotalPage() != 0) {
            pageIndex = getTotalPage();
        }
        return pageIndex;
    }

    public boolean hasNextPage() {
        if (pageIndex <= getTotalPage()) {
            return true;
        }
        return false;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getStartIndex() {
        int cPage = getPageIndex();

        if (cPage <= 1) {
            return 1;
        }

        cPage--;

        return (getPageSize() * cPage) + 1;
    }

    public int getEndIndex() {
        return getPageIndex() * getPageSize();
    }

    public int getTotalPage() {
        return totalPage;
    }

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public DirType getDir() {
		return dir;
	}

	public void setDir(DirType dir) {
		this.dir = dir;
	}
	
	public int getMySqlStartIndex(){
		return this.getStartIndex()-1;
	}
	
	public int getMySqlLength(){
		return this.getPageSize();
	}
}
