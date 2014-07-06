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
public class FooterRow extends MyRow
{
    private List<FooterCell> cells =new ArrayList<FooterCell>();


    public List<FooterCell> getCells() {
        return cells;
    }

    public void setCells(List<FooterCell> cells) {
        this.cells = cells;
    }
}
