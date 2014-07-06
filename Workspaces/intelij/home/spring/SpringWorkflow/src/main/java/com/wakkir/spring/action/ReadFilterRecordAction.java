package com.wakkir.spring.action;

import com.wakkir.spring.model.FilterObject;
import com.wakkir.spring.service.IContext;
import com.wakkir.spring.service.IWorkflowAction;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wakkir
 * Date: 08/03/14
 * Time: 00:07
 */
public class ReadFilterRecordAction implements IWorkflowAction<FilterObject>
{
    //private final Log logger = LogFactory.getLog(CreateDBRecordAction.class);
    @Override
    public List<FilterObject> doAction(IContext context) throws Exception
    {
        System.out.println(">.................>");
        System.out.println("ReadFilterRecordAction : " + context.getAttribute("myInteger"));

        List<FilterObject> flist=new ArrayList<FilterObject>();
        flist.add(new FilterObject(Integer.valueOf(String.valueOf(context.getAttribute("myInteger"))),context.getAttribute("myString")+":FilterObject:ReadFilterRecordAction"));
        //flist.add(new FilterObject(20,"FilterObject2"));
        //flist.add(new FilterObject(30,"FilterObject3"));
        //flist.add(new FilterObject(40,"FilterObject4"));

        return flist;

    }
}
