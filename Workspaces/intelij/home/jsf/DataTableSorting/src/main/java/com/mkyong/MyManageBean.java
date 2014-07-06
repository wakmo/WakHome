package com.mkyong;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean(name="mymbean")
@SessionScoped
public class MyManageBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	private List<UIOrder> tableData;

	private boolean sortAscending = true;

    private UIOrder rowData;
    private RepeatPaginator paginator;
    private String sort;
    private boolean checkAllIndex;
	
	private static final UIOrder[] dataList =
    {
		new UIOrder("A0002", "Harddisk 100TB", new BigDecimal("500.00"), 3),
		new UIOrder("A0001", "Intel CPU"     , new BigDecimal("4200.00"), 6),
		new UIOrder("A0004", "Samsung LCD"   , new BigDecimal("5200.00"), 10),
		new UIOrder("A0003", "Dell Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0103", "ss Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0203", "rt Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0033", "tyu Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A4003", "fdgsd Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0073", "sdg Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0603", "567 Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0503", "dfh Laptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0083", "Dell dgdf"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0903", "Deleryptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A1003", "Deggjop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A1103", "De364ptop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A1203", "Deasfatop"   , new BigDecimal("11600.00"), 9),
        new UIOrder("A0133", "xdv Laptop"   , new BigDecimal("11600.00"), 9),
		new UIOrder("A0015", "A4Tech Mouse"  , new BigDecimal("200.00"), 20)
	};
	
	public MyManageBean()
    {

		//tableData = new ArrayList<UIOrder>(Arrays.asList(dataList));
        paginator = new RepeatPaginator(new ArrayList<UIOrder>(Arrays.asList(dataList)));
        //tableData =(List<UIOrder>)getPaginator().getModel();

	}

    public boolean isCheckAllIndex() {
        return checkAllIndex;
    }

    public void setCheckAllIndex(boolean checkAllIndex) {
        this.checkAllIndex = checkAllIndex;
    }

    public List<UIOrder> getTableData()
    {
        tableData =(List<UIOrder>)getPaginator().getModel();
		return tableData;
	}


    public UIOrder getRowData()
    {     if(rowData ==null)
                rowData =new UIOrder();
        return rowData;
    }

    public void setRowData(UIOrder rowData)
    {
        this.rowData = rowData;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public RepeatPaginator getPaginator()
    {
        return paginator;
    }

    public void setPaginator(RepeatPaginator paginator)
    {
        this.paginator = paginator;
    }

    //sort by rowData no
	public String sortBy()
    {


        System.out.println("sortByOrderNo-value:"+sort);

		if(sortAscending){
			
			//ascending rowData
			Collections.sort(tableData, new Comparator<UIOrder>()
            {

				@Override
				public int compare(UIOrder o1, UIOrder o2) {
					
					return o1.getOrderNo().compareTo(o2.getOrderNo());
					
				}

			});
			sortAscending = false;
			
		}else{

			//descending rowData
			Collections.sort(tableData, new Comparator<UIOrder>() {

				@Override
				public int compare(UIOrder o1, UIOrder o2) {
					
					return o2.getOrderNo().compareTo(o1.getOrderNo());
					
				}

			});
			sortAscending = true;
		}

		return null;
	}

    public String deleteAction()
    {
        System.out.println("deleteAction:"+ rowData);
        tableData.remove(rowData);
        return null;
    }

    public String editAction()
    {
        System.out.println("editAction:"+ rowData);
        rowData.setEditable(true);
        return null;
    }

    public String saveAction()
    {
        System.out.println("saveAction:"+ rowData);
        rowData.setEditable(false);
        return null;
    }

    public String submitAction()
    {
        System.out.println("submitAction:"+ tableData.size());
        //get all existing value but set "editable" to false
        for (UIOrder order : tableData)
        {
            order.setEditable(false);
            System.out.println(order);
        }
        //return to current page
        return null;
    }

    public String checkAll()
    {
        for (UIOrder order : tableData)
        {
            order.setSelected(checkAllIndex?false:true);
        }
        checkAllIndex=checkAllIndex?false:true;
        return null;
    }

	

}