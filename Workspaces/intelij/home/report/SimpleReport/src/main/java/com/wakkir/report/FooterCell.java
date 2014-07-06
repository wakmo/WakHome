package com.wakkir.report;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 01:04
 * To change this template use File | Settings | File Templates.
 */
public class FooterCell extends  MyCell
{
   private int colspan=1;

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }
}
