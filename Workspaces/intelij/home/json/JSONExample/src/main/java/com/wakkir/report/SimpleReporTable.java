package com.wakkir.report;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 00:56
 * To change this template use File | Settings | File Templates.
 */
public class SimpleReporTable
{
   private List<DataColumn> dataColumns =new ArrayList<DataColumn>();
   private List<TitleRow> titleRows =new ArrayList<TitleRow>();
   private List<HeaderRow> headerRows =new ArrayList<HeaderRow>();
   private List<DataRow> dataRows =new ArrayList<DataRow>();
   private List<FooterRow> footerRows =new ArrayList<FooterRow>();

    public List<DataRow> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }

    public List<HeaderRow> getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(List<HeaderRow> headerRows) {
        this.headerRows = headerRows;
    }

    public List<FooterRow> getFooterRows() {
        return footerRows;
    }

    public void setFooterRows(List<FooterRow> footerRows) {
        this.footerRows = footerRows;
    }

    public List<TitleRow> getTitleRows() {
        return titleRows;
    }

    public void setTitleRows(List<TitleRow> titleRows) {
        this.titleRows = titleRows;
    }

    public List<DataColumn> getDataColumns() {
        return dataColumns;
    }

    public void setDataColumns(List<DataColumn> dataColumns) {
        this.dataColumns = dataColumns;
    }
}
