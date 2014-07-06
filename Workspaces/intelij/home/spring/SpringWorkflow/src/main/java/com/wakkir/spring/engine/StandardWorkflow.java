package com.wakkir.spring.engine;

/**
 * User: wakkir
 * Date: 07/03/14
 * Time: 23:23
 */

import com.wakkir.spring.service.IContext;
import com.wakkir.spring.service.IWorkflow;
import com.wakkir.spring.service.IWorkflowAction;
import com.wakkir.spring.context.StandardContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StandardWorkflow implements IWorkflow<Object>//, ApplicationContextAware
{
    //private static final Logger logger = LoggerFactory.getLogger(StandardWorkflow.class.getName());

    private Map<String, List<IWorkflowAction>> workflowActions;


    @Override
    public List<Object> processWorkflow(String workflofName, Map<String, Object> parameters)
    {
        List<Object> objList = new ArrayList<Object>();

        IContext context = new StandardContext(parameters);

        List<IWorkflowAction> actions = getWorkflowActions(workflofName);

        for (IWorkflowAction action : actions)
        {
            try
            {
                objList.add(action.doAction(context));
            }
            catch (Exception e)
            {
                StringBuilder message = new StringBuilder("Failed to complete action:" + action.toString());
                message.append('\n');
                message.append(e.getMessage());
                System.err.println(message.toString());
                return objList;
            }
        }

        return objList;

    }

    private List<IWorkflowAction> getWorkflowActions(String actionName)
    {
        List<IWorkflowAction> actions = workflowActions.get(actionName);
        if (actions == null || actions.isEmpty())
        {
            System.err.println("There is no defined action for " + actionName);
            throw new IllegalArgumentException("There is no defined action for " + actionName);
        }
        return actions;
    }


    // Getter/Setter

    public Map<String, List<IWorkflowAction>> getWorkflowActions()
    {
        return workflowActions;
    }

    public void setWorkflowActions(Map<String, List<IWorkflowAction>> workflowActions)
    {
        this.workflowActions = workflowActions;
    }


}