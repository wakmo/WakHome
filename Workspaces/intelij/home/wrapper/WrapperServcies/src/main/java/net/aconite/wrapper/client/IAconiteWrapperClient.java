package net.aconite.wrapper.client;

/**
 * User: wakkir.muzammil
 * Date: 18/10/13
 * Time: 17:24
 */
public interface IAconiteWrapperClient
{
    public void sendCommand(String command) throws WrapperClientException;
}
