package com.mkyong;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 02/12/12
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
public class UIOrder implements Serializable
{

    private static final long serialVersionUID = 7457457457L;
    private    String orderNo;
    private    String productName;
    private    BigDecimal price;
    private    int qty;
    private    boolean isSelected;

    private boolean editable;

    public UIOrder()
    {

    }
        public UIOrder(String orderNo, String productName, BigDecimal price, int qty)
        {
            this.orderNo = orderNo;
            this.productName = productName;
            this.price = price;
            this.qty = qty;
        }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

        public String getOrderNo()
        {
            return orderNo;
        }

        public void setOrderNo(String orderNo)
        {
            this.orderNo = orderNo;
        }

        public String getProductName()
        {
            return productName;
        }

        public void setProductName(String productName)
        {
            this.productName = productName;
        }

        public BigDecimal getPrice()
        {
            return price;
        }

        public void setPrice(BigDecimal price)
        {
            this.price = price;
        }

        public int getQty()
        {
            return qty;
        }

        public void setQty(int qty)
        {
            this.qty = qty;
        }

        public String toString()
        {
            StringBuffer sb=new StringBuffer();
            sb.append("\norderNo    :"+orderNo);
            sb.append("\nproductName:"+productName);
            sb.append("\nprice      :"+price);
            sb.append("\nqty        :"+qty);
            sb.append("\nisSelected :"+isSelected);
            return  sb.toString();
        }



}
