package net.aconite.affina.espinterface.demo;

/**
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 12:16
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EspServices implements IEspServices
{
    private final int serverSocketPort;
    private final int serverPollRate;

    private static final Logger logger = LoggerFactory.getLogger(EspServices.class);


    EspServices(final int port, final int pollRate)
    {
        this.serverSocketPort = port;
        this.serverPollRate = pollRate;
    }


    @SuppressWarnings("ConstantConditions")
    public void startServer()
    {
        logger.info(serverSocketPort + " " + serverPollRate);
    }


}



