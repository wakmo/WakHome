package net.aconite.wrapper.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

/**
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 12:08
 */
public class AconiteWrapperClient implements IAconiteWrapperClient
{
    private final String wrapperServiceHost;
    private final int wrapperServicePort;

    private static final Logger LOGGER = LoggerFactory.getLogger(AconiteWrapperClient.class);

    public AconiteWrapperClient(final String host, final int port)
    {
        this.wrapperServiceHost=host;
        this.wrapperServicePort=port;
    }

    public void sendCommand(String command) throws WrapperClientException
    {
        String serverResponse;
        Socket clientSocket=null;
        DataOutputStream outToServer=null;
        BufferedReader inFromServer=null;
        try
        {
            clientSocket = new Socket(wrapperServiceHost, wrapperServicePort);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(command + '\n');
            serverResponse = inFromServer.readLine();
            LOGGER.info(serverResponse);

        }
        catch(ConnectException ex)
        {
            LOGGER.error("Error!. Connection refused : ",ex);
            throw new  WrapperClientException("Error!. Connection refused : ",ex);
        }
        catch (IOException ex)
        {
            LOGGER.error("Error! while sending command :", ex);
            throw new  WrapperClientException("Error! while sending command :", ex);
        }
        finally
        {
            try
            {
                if(inFromServer!=null)
                {
                    inFromServer.close();
                }
                if(outToServer!=null)
                {
                    outToServer.close();
                }
                if(clientSocket!=null)
                {
                    clientSocket.close();
                }
            }
            catch (IOException ex)
            {
                LOGGER.error("Error while connection closing! ", ex);
            }
        }
    }



}
