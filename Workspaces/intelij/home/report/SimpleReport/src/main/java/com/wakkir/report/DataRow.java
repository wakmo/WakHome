package com.wakkir.report;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 01:02
 * To change this template use File | Settings | File Templates.
 */
public class DataRow  extends MyRow
{
    private List<DataCell> cells =new ArrayList<DataCell>();


    public List<DataCell> getCells() {
        return cells;
    }

    public void setCells(List<DataCell> cells) {
        this.cells = cells;
    }
}
