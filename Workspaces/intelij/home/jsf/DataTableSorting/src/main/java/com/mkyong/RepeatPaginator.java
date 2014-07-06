package com.mkyong;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 02/12/12
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class RepeatPaginator  implements Serializable
{
    private static final long serialVersionUID = 456456456456L;

    private static final int DEFAULT_RECORDS_NUMBER = 5;
    private static final int DEFAULT_PAGE_INDEX = 1;

    private int records;
    private int recordsTotal;
    private int pageIndex;
    private int pages;
    private List<?> origModel;
    private List<?> model;

    private int fromIndex;
    private int toIndex;

    public RepeatPaginator(List<?> model)
    {
        this.origModel = model;
        this.records = DEFAULT_RECORDS_NUMBER;
        this.pageIndex = DEFAULT_PAGE_INDEX;
        this.recordsTotal = model.size();

        if (records > 0)
        {
            pages = records <= 0 ? 1 : recordsTotal / records;

            if (recordsTotal % records > 0)
            {
                pages++;
            }

            if (pages == 0)
            {
                pages = 1;
            }
        }
        else
        {
            records = 1;
            pages = 1;
        }

        updateModel();
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }

    public void updateModel()
    {
        fromIndex = getFirst()+1;
        toIndex = getFirst() + records;

        if(toIndex > this.recordsTotal)
        {
            toIndex = this.recordsTotal;
        }

        this.model = origModel.subList(fromIndex-1, toIndex);
    }

    public void next()
    {
        System.out.println("b4Click:next:" + pageIndex);
        if(this.pageIndex < pages)
        {
            this.pageIndex++;
        }

        updateModel();
    }

    public void prev()
    {
        System.out.println("b4Click:prev:"+pageIndex);
        if(this.pageIndex > 1)
        {
            this.pageIndex--;
        }

        updateModel();
    }

    public int getRecords()
    {
        return records;
    }

    public int getRecordsTotal()
    {
        return recordsTotal;
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public int getPages()
    {
        return pages;
    }

    public int getFirst()
    {
        return (pageIndex * records) - records;
    }

    public List<?> getModel()
    {
        return model;
    }

    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
}
