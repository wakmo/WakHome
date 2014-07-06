package com.wakkir.test2;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 11/11/12
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
import java.text.DecimalFormat;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class Listener implements MessageListener
{

    public void onMessage(Message message)
    {
        try
        {
            MapMessage map = (MapMessage)message;
            String stock = map.getString("stock");
            double price = map.getDouble("price");
            double offer = map.getDouble("offer");
            boolean up = map.getBoolean("up");
            DecimalFormat df = new DecimalFormat( "#,###,###,##0.00" );
            System.out.println("wakkir "+stock + "\t" + df.format(price) + "\t" + df.format(offer) + "\t" + (up?"up":"down"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}