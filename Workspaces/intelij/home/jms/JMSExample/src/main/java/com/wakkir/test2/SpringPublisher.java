package com.wakkir.test2;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 11/11/12
 * Time: 20:06
 * To change this template use File | Settings | File Templates.
 */
public class SpringPublisher
{
    private JmsTemplate template;
    private int count = 1;
    private int total;
    private Destination[] destinations;
    private HashMap<Destination,StockMessageCreator> creators = new HashMap<Destination,StockMessageCreator>();

    public void start()
    {
        while (total < 1000)
        {
            for (int i = 0; i < count; i++)
            {
                sendMessage();
            }
            total += count;
            //System.out.println("Published '" + count + "' of '" + total + "' price messages");
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException x)
            {
            }
        }
    }

    protected void sendMessage()
    {
        int idx = 0;
        while (true)
        {
            idx = (int)Math.round(destinations.length * Math.random());
            if (idx < destinations.length)
            {
                break;
            }
        }
        Destination destination = destinations[idx];
        //System.out.println(destination+":"+idx);
        template.send(destination, getStockMessageCreator(destination));
    }
    private StockMessageCreator getStockMessageCreator(Destination dest)
    {
        if (creators.containsKey(dest))
        {
              return creators.get(dest);
        }
        else
        {
            StockMessageCreator creator = new StockMessageCreator(dest);
            creators.put(dest, creator);
            return creator;
        }
    }


    public Destination[] getDestinations()
    {
        return destinations;
    }

    public void setDestinations(Destination[] destinations)
    {
        this.destinations = destinations;
    }

    public JmsTemplate getTemplate()
    {
        return template;
    }

    public void setTemplate(JmsTemplate template)
    {
        this.template = template;
    }
}