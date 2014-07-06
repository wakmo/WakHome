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
public class HeaderRow   extends MyRow
{
    private List<HeaderCell> cells =new ArrayList<HeaderCell>();


    public List<HeaderCell> getCells() {
        return cells;
    }

    public void setCells(List<HeaderCell> cells) {
        this.cells = cells;
    }
}
