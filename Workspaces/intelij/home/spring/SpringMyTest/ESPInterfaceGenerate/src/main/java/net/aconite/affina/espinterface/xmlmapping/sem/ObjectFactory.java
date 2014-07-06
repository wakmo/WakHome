
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the net.aconite.affina.espinterface.xmlmapping.sem package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory
{


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.aconite.affina.espinterface.xmlmapping.sem
     */
    public ObjectFactory()
    {
    }
    //====================CardSetupRequest==========================================

    /**
     * Create an instance of {@link ScriptType }
     */
    public ScriptType createScriptType()
    {
        return new ScriptType();
    }

    /**
     * Create an instance of {@link ScriptType.ScriptCommands }
     */
    public ScriptType.ScriptCommands createScriptTypeScriptCommands()
    {
        return new ScriptType.ScriptCommands();
    }

    /**
     * Create an instance of {@link ScriptType.ScriptCommands.ScriptCommand }
     */
    public ScriptType.ScriptCommands.ScriptCommand createScriptTypeScriptCommandsScriptCommand()
    {
        return new ScriptType.ScriptCommands.ScriptCommand();
    }

    /**
     * Create an instance of {@link CardSetupRequest }
     */
    public CardSetupRequest createCardSetupRequest()
    {
        return new CardSetupRequest();
    }

    /**
     * Create an instance of {@link CardType }
     */
    public CardType createCardType()
    {
        return new CardType();
    }

    /**
     * Create an instance of {@link AppType }
     */
    public AppType createAppType()
    {
        return new AppType();
    }

    /**
     * Create an instance of {@link ErrorType }
     */
    public ErrorType createErrorType()
    {
        return new ErrorType();
    }

    /**
     * Create an instance of {@link PANType }
     */
    public PANType createPANType()
    {
        return new PANType();
    }

    /**
     * Create an instance of {@link CardIdentificationType }
     */
    public CardIdentificationType createCardIdentificationType()
    {
        return new CardIdentificationType();
    }

    /**
     * Create an instance of {@link ApplicationType }
     */
    public ApplicationType createApplicationType()
    {
        return new ApplicationType();
    }

    /**
     * Create an instance of {@link TransactionType }
     */
    public TransactionType createTransactionType()
    {
        return new TransactionType();
    }

    /**
     * Create an instance of {@link DeviceType }
     */
    public DeviceType createDeviceType()
    {
        return new DeviceType();
    }

    /**
     * Create an instance of {@link TagType }
     */
    public TagType createTagType()
    {
        return new TagType();
    }

    /**
     * Create an instance of {@link CardIdType }
     */
    public CardIdType createCardIdType()
    {
        return new CardIdType();
    }

    /**
     * Create an instance of {@link HeaderType }
     */
    public HeaderType createHeaderType()
    {
        return new HeaderType();
    }

    /**
     * Create an instance of {@link ScriptIdType }
     */
    public ScriptIdType createScriptIdType()
    {
        return new ScriptIdType();
    }

    /**
     * Create an instance of {@link NVPType }
     */
    public NVPType createNVPType()
    {
        return new NVPType();
    }

    /**
     * Create an instance of {@link ScriptType.ValidPeriod }
     */
    public ScriptType.ValidPeriod createScriptTypeValidPeriod()
    {
        return new ScriptType.ValidPeriod();
    }

    /**
     * Create an instance of {@link ScriptType.ScriptCommands.ScriptCommand.DataItems }
     */
    public ScriptType.ScriptCommands.ScriptCommand.DataItems createScriptTypeScriptCommandsScriptCommandDataItems()
    {
        return new ScriptType.ScriptCommands.ScriptCommand.DataItems();
    }
    //=========================CardSetupResponse======================================


    /**
     * Create an instance of {@link CardSetupResponse }
     */
    public CardSetupResponse createCardSetupResponse()
    {
        return new CardSetupResponse();
    }


    //=========================ScriptStatusResponse======================================

    /**
     * Create an instance of {@link ScriptStatusResponse }
     */
    public ScriptStatusResponse createScriptStatusResponse()
    {
        return new ScriptStatusResponse();
    }


    //=========================ScriptStatusUpdate======================================

    /**
     * Create an instance of {@link ScriptStatusUpdate }
     */
    public ScriptStatusUpdate createScriptStatusUpdate()
    {
        return new ScriptStatusUpdate();
    }


    /**
     * Create an instance of {@link ScriptStatusUpdate.BusinessFunction }
     */
    public ScriptStatusUpdate.BusinessFunction createScriptStatusUpdateBusinessFunction()
    {
        return new ScriptStatusUpdate.BusinessFunction();
    }

    /**
     * Create an instance of {@link ScriptStatusUpdate.DeletionDetails }
     */
    public ScriptStatusUpdate.DeletionDetails createScriptStatusUpdateDeletionDetails()
    {
        return new ScriptStatusUpdate.DeletionDetails();
    }

    /**
     * Create an instance of {@link ScriptStatusUpdate.TransactionDetails }
     */
    public ScriptStatusUpdate.TransactionDetails createScriptStatusUpdateTransactionDetails()
    {
        return new ScriptStatusUpdate.TransactionDetails();
    }

    /**
     * Create an instance of {@link ScriptStatusUpdate.DeviceDetails }
     */
    public ScriptStatusUpdate.DeviceDetails createScriptStatusUpdateDeviceDetails()
    {
        return new ScriptStatusUpdate.DeviceDetails();
    }

    /**
     * Create an instance of {@link ScriptStatusUpdate.ScriptDeliveryStatus }
     */
    public ScriptStatusUpdate.ScriptDeliveryStatus createScriptStatusUpdateScriptDeliveryStatus()
    {
        return new ScriptStatusUpdate.ScriptDeliveryStatus();
    }


    //=========================StageScriptRequest======================================

    /**
     * Create an instance of {@link StageScriptRequest }
     */
    public StageScriptRequest createStageScriptRequest()
    {
        return new StageScriptRequest();
    }


    /**
     * Create an instance of {@link StageScriptRequest.BusinessFunction }
     */
    public StageScriptRequest.BusinessFunction createStageScriptRequestBusinessFunction()
    {
        return new StageScriptRequest.BusinessFunction();
    }

    /**
     * Create an instance of {@link StageScriptRequest.Action }
     */
    public StageScriptRequest.Action createStageScriptRequestAction()
    {
        return new StageScriptRequest.Action();
    }


    //=========================StageScriptResponse======================================


    /**
     * Create an instance of {@link StageScriptResponse }
     */
    public StageScriptResponse createStageScriptResponse()
    {
        return new StageScriptResponse();
    }


}
