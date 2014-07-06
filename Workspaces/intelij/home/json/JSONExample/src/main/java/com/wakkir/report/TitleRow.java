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
public class TitleRow  extends MyRow
{
    private List<TitleCell> cells =new ArrayList<TitleCell>();


    public List<TitleCell> getCells() {
        return cells;
    }

    public void setCells(List<TitleCell> cells) {
        this.cells = cells;
    }
}
