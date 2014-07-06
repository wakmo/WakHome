package com.wakkir.spring.action;

import com.wakkir.spring.model.RequestObject;
import com.wakkir.spring.service.IContext;
import com.wakkir.spring.service.IWorkflowAction;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wakkir
 * Date: 08/03/14
 * Time: 00:07
 */
public class SendRequestMessageAction implements IWorkflowAction<RequestObject>
{
    //private final Log logger = LogFactory.getLog(CreateDBRecordAction.class);
    @Override
    public List<RequestObject> doAction(IContext context) throws Exception
    {
        System.out.println(">.................>");
        System.out.println("SendRequestMessageAction : " + context.getAttribute("myInteger"));

        List<RequestObject> flist=new ArrayList<RequestObject>();
        flist.add(new RequestObject(Integer.valueOf(String.valueOf(context.getAttribute("myInteger"))),context.getAttribute("myString")+":RequestObject:SendRequestMessageAction"));


        return flist;

    }
}