package com.wakkir.spring.action;

import com.wakkir.spring.model.DbObject;
import com.wakkir.spring.service.IContext;
import com.wakkir.spring.service.IWorkflowAction;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wakkir
 * Date: 08/03/14
 * Time: 00:07
 */
public class CreateDBRecordAction implements IWorkflowAction<DbObject>
{
    //private final Log logger = LogFactory.getLog(CreateDBRecordAction.class);


    @Override
    public List<DbObject> doAction(IContext context) throws Exception
    {
        System.out.println(">.................>");
        System.out.println("CreateDBRecordAction : " + context.getAttribute("myInteger"));

        List<DbObject> flist=new ArrayList<DbObject>();
        flist.add(new DbObject(Integer.valueOf(String.valueOf(context.getAttribute("myInteger"))),context.getAttribute("myString")+":DbObject:CreateDBRecordAction1"));
        flist.add(new DbObject(Integer.valueOf(String.valueOf(context.getAttribute("myInteger"))),context.getAttribute("myString")+":DbObject:CreateDBRecordAction2"));


        return flist;

    }
}
