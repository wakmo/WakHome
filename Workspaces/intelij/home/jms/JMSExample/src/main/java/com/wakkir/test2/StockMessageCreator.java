package com.wakkir.test2;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 11/11/12
 * Time: 20:06
 * To change this template use File | Settings | File Templates.
 */
public class StockMessageCreator implements MessageCreator
{
    private int MAX_DELTA_PERCENT = 1;
    private Map<Destination, Double> LAST_PRICES =  new Hashtable<Destination, Double>();
    Destination stock;
    public StockMessageCreator(Destination stock)
    {
        this.stock = stock;
    }
    public Message createMessage(Session session) throws JMSException
    {
        Double value = LAST_PRICES.get(stock);
        if (value == null)
        {
            value = new Double(Math.random() * 100);
        }
// lets mutate the value by some percentage
        double oldPrice = value.doubleValue();
        value = new Double(mutatePrice(oldPrice));
        LAST_PRICES.put(stock, value);
        double price = value.doubleValue();
        double offer = price * 1.001;
        boolean up = (price > oldPrice);
        MapMessage message = session.createMapMessage();
        message.setString("stock", stock.toString());
        message.setDouble("price", price);
        message.setDouble("offer", offer);
        message.setBoolean("up", up);
        System.out.println("Sending: " + ((ActiveMQMapMessage)message).getContentMap() + " on destination: " + stock);
        return message;

    }
    protected double mutatePrice(double price)
    {
        double percentChange = (2 * Math.random() * MAX_DELTA_PERCENT) - MAX_DELTA_PERCENT;
        return price * (100 + percentChange) / 100;
    }
}