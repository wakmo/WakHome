/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.scripting.statusupdate;

import com.platform7.pma.card.SoftCard;
import com.platform7.pma.request.emvscriptrequest.*;
import java.sql.Timestamp;
import java.util.*;
import net.aconite.affina.espinterface.cardselection.CardGenerator;
import net.aconite.affina.espinterface.cardselection.SelectableCardGenerator;
import net.aconite.affina.espinterface.constants.*;
import net.aconite.affina.espinterface.exceptions.*;
import net.aconite.affina.espinterface.helper.*;
import net.aconite.affina.espinterface.model.*;
import net.aconite.affina.espinterface.persistence.GenericPersistentDAO;
import net.aconite.affina.espinterface.persistence.Persistent;
import net.aconite.affina.espinterface.persistence.Workable;
import net.aconite.affina.espinterface.scripting.generic.*;
import net.aconite.affina.espinterface.xmlmapping.sem.*;
import net.aconite.affina.espinterface.xmlmapping.sem.ScriptStatusUpdate.ScriptDeliveryStatus;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;

/**
 *
 * @author thushara.pethiyagoda
 */
public class ScriptUpdateProcessor implements ScriptProcessable<ScriptStatusUpdateDataHolder>, Workable
{

    /**
     * Scriptable card-generator.
     */
    private CardGenerator cardGen;
    /**
     * Persistence framework.
     */
    private static Persistent peristent;
    /**
     * To indicate whether validation is successful or not.
     */
    private boolean isScriptValidationSuccessful = true;
    /**
     * Used to indicate whether script order should be checked or not.
     */
    private boolean isCheckScriptOrder;
    /**
     * Scriptable card.
     */
    private ScriptableCard sc;
    /**
     * A helper Map to store script status life cycles and their processing order.
     */
    private static final Map<String, Integer> statusOrderMap = new HashMap<String, Integer>();
    
    static
    {
        peristent = GenericPersistentDAO.getPersistent();
        statusOrderMap.put("STAGED", 1);
        statusOrderMap.put("SENT", 2);
        statusOrderMap.put("DELETED", 3);
        statusOrderMap.put("DELIVERED", 4);
    }

    /**
     * Constructor.
     */
    private ScriptUpdateProcessor()
    {
        cardGen = new SelectableCardGenerator();
    }

    /**
     * Returns a new instance of ScriptProcessable.
     * <p/>
     * @return
     */
    public static ScriptProcessable<ScriptStatusUpdateDataHolder> getProcessable()
    {
        ScriptProcessable<ScriptStatusUpdateDataHolder> sp = new ScriptUpdateProcessor();
        peristent.addTransactionalWorker((Workable) sp);
        return sp;
    }

    /**
     * 1. Select soft card based on pan,psn and expiry date. (Done) 2. Validate card if its null and valid/Active.(Done)
     * 3. Check to see if tracking ref already exists. And if does then update status or create one. 4. Store Data
     * <p/>
     * This method throws ScriptableCardNotFoundException if a card cannot be found that matches PAN, PSN and the
     * Expiration Date received via the update script which is a RuntimeException.
     * <p/>
     * @param scriptData ScriptStatusUpdateDataHolder which encapsulates the XML object ScriptStatusUpdate
     */
    @Override
    public Result processScript(ScriptStatusUpdateDataHolder scriptData)
    {
        try
        {
            ScriptStatusUpdate data = scriptData.getScriptStatusUpdate();
            //Validate Script
            validateScript(data);
            //If all is well do the transaction and commit.

            peristent.<ScriptStatusUpdate>doTransactionalWorkAndCommit(data);
        }
        catch (Exception esvx)
        {
            return Result.getInstance(isScriptValidationSuccessful, esvx,
                                      "Unable to complete Script processing successfuly.");
        }
        finally
        {
            //peristent = null;
        }
        return Result.getInstance(isScriptValidationSuccessful, null, "Script processing completed successfuly");
    }

    /**
     * Performs the actual persistent related work.
     * <p/>
     * @param <T>
     * @param arg
     */
    @Override
    public <T> void doWork(T arg)
    {
        storeScriptData((ScriptStatusUpdate) arg);
    }

    /**
     * Validates the Script represented by ScriptStatusUpdate. Validates the whether a Card exist with matching PAN, PSN
     * and Expiry date.
     * <p/>
     * This methods throws ScriptValidationException and ScriptableCardNotFoundException which are themselves
     * RuntimeExceptions.
     * <p/>
     * @param ssu ScriptStatusUpdate
     */
    private void validateScript(ScriptStatusUpdate ssu) throws ScriptValidationException
    {
        if (ssu == null)
        {
            isScriptValidationSuccessful = false;
            throw new ScriptValidationException("Invalid Script. Script received is empty.");
        }
        
        if (DataUtil.isEmpty(ssu.getTrackingReference()))
        {
            isScriptValidationSuccessful = false;
            throw new ScriptValidationException("Empty Tracking reference.");
        }
        
        if (DataUtil.isEmpty(ssu.getCard().getPAN()))
        {
            isScriptValidationSuccessful = false;
            throw new ScriptValidationException("Invalid PAN. Empty or no value.");
        }
        
        if (DataUtil.isEmpty(ssu.getCard().getPANSequence()))
        {
            isScriptValidationSuccessful = false;
            throw new ScriptValidationException("Invalid PSN. Empty or no value.");
        }
        
        if (!DataUtil.isDate(ssu.getCard().getExpirationDate()))
        {
            isScriptValidationSuccessful = false;
            throw new ScriptValidationException("Invalid Expiration date. Empty or no value.");
        }
        
        if (ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELETED)
        {
            if (ssu.getDeletionDetails() == null)
            {
                isScriptValidationSuccessful = false;
                throw new ScriptValidationException("Invalid message. Missing deletion "
                        + "details on a script with " + ssu.getScriptUpdateStatus().value() + " state.");
            }
        }
        
        if (ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.SENT
                || ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELIVERED)
        {
            if (ssu.getTransactionDetails() == null)
            {
                isScriptValidationSuccessful = false;
                throw new ScriptValidationException("Invalid message. Missing Transaction "
                        + "details on a script with " + ssu.getScriptUpdateStatus().value() + " state.");
            }
        }
        
        if (ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.SENT
                || ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELIVERED
                || ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELETED)
        {
            if (ssu.getScriptDeliveryStatus() == null)
            {
                isScriptValidationSuccessful = false;
                throw new ScriptValidationException("Invalid message. Missing Delivery "
                        + "status on a script with " + ssu.getScriptUpdateStatus().value() + " state.");
            }
        }

        //Locate Card R.5-1
        long cadExpdate = DateHelper.fromStringTolong(ssu.getCard().getExpirationDate());
        sc = cardGen.generateCard(ssu.getCard().getPAN(), ssu.getCard().getPANSequence(), cadExpdate);
        //If card cannot be located log error.
        if (sc == null)
        {
            isScriptValidationSuccessful = false;
            throw new ScriptableCardNotFoundException("Card not found");
        }
    }

    /**
     * Updates script status.
     */
    private void updateScriptStatus(ESPScriptUpdateStatus data, ScriptStatusUpdate ssu)
    {
        data.setScriptLifecycleStatus(ssu.getScriptUpdateStatus().value());
        if (hasScriptDeliveryStatus(ssu))
        {
            data.setScriptDeliveryStatus(ssu.getScriptDeliveryStatus().getDeliveryStatus());
        }
        //Do store delete Detals and others 
    }

    /**
     * 1.(Ref3.2.3)Script order needs to be checked because since messages can come out of order, script-order with a
     * value lower than the script-order of a previous script can arrive later, which should be ignored as scripts with
     * a higher script-order takes precedence over one with a lower script-order.
     * <p/>
     * 2.Compare date, script description with the new arrival and they are the same as stored then discard it.
     * <p/>
     * Stores script data. If the script tracking reference already exists then the status is updated or else a new
     * record is created subject to above 1 & 2
     */
    private void storeScriptData(ScriptStatusUpdate ssu)
    {
        //Get the stored record that matches the TrackingReference.
        ESPScriptUpdateStatus statusUpdateData = getExistingStatusUpdateData(ssu.getTrackingReference());
        //1.Get the pan,psnexp date
        //2.See whether another ESPParameter exist with a similar PAN to the one fetched above via tracking ref.
        //3.if does not then fine and proceed. script order does not matter.
        //4.if it does then check the script order

        //Must get the soft card and test the PAN,PSN and EXP Date with the one arrived
        //And check the arived seq and if its greater than stored update else discard.
        Timestamp newDate = getScriptDate(ssu);

        //Null statusUpdateData means that Script with this Tracking Ref does not exists yet. So create it new.
        if (statusUpdateData == null)
        {
            statusUpdateData = peristent.getRegisteredObject(ESPScriptUpdateStatus.class);
            updateScriptStatusUpdateModel(statusUpdateData, ssu, true);
        }

        /**
         * If the Record exists for the given Tracking ref then update Script State if
         * isCurrLifecycleStateEarlierOrRestagedScript returns true.
         */
        if (statusUpdateData != null)
        {
            boolean isCurrLifecycleStateEarlierOrRestagedScript = isCurrLifecycleStateEarlierOrRestagedScript(
                    statusUpdateData.getScriptLifecycleStatus(),
                    ssu.getScriptUpdateStatus(),
                    ssu.getScriptDeliveryStatus());
            //This means that we have a state (via script) later than currently stored in DB.
            if (isCurrLifecycleStateEarlierOrRestagedScript)
            {
                updateScriptStatus(statusUpdateData, ssu);
            }
        }

        //Get ESP Param that matches PAN, PSN and Ex Date of the card in request (ssu above)
        SoftCard softCard = sc.getSoftCard();
        /////////////Review must get the one with heighest SCRIPT ORDER///////////////////////////
        ESPScriptUpdateStatus statusUpdateDataWithSameCardData = getESPScriptUpdateStatusBySoftCardOID(ssu);

        //If it does not exist means, a previous update for this card does not exist,so no need to check script order.
        if (statusUpdateDataWithSameCardData != null)
        {
            isCheckScriptOrder = true;
        }
        //Another record for the card exists (indicated by isCheckScriptOrder above) and script order is heigher.
        if (isCheckScriptOrder && doUpdateScriptStatus(statusUpdateDataWithSameCardData.getScriptOrder(),
                                                       ssu.getScriptOrder().intValue()))
        {
            ////REVIEW*****************************check the mapping.
            //Update just the params..
            updateScriptStatusUpdateModel(statusUpdateData, ssu, false);
        }
    }

    /**
     * Updates the Database mapped script update date model with the relevant changes.
     * <p/>
     * @param scriptUpdateData The Model
     * @param ssu              The XML Data
     * @param createNew        Indicates whether its a new Model creation or and Update of an existing Model.
     */
    private void updateScriptStatusUpdateModel(ESPScriptUpdateStatus scriptUpdateData, ScriptStatusUpdate ssu,
                                               boolean createNew)
    {
        Timestamp newDate = getScriptDate(ssu);
        if (createNew)
        {
            scriptUpdateData.setTrackingReference(ssu.getTrackingReference());
            //scriptUpdateData.setSoftCard(sc.getSoftCard());
            scriptUpdateData.setSource(ssu.getSource());
            scriptUpdateData.setTarget(ssu.getTarget());
            scriptUpdateData.setScriptSequenceNumber(ssu.getScriptSequenceNumber().intValue());
            scriptUpdateData.setAutoRetryCount(ssu.getAutoRetryCount().intValue());
            scriptUpdateData.setStatus(ssu.getScriptUpdateStatus().name());            
            scriptUpdateData.setDateCreated(newDate);
            scriptUpdateData.setPan(ssu.getCard().getPAN());
            scriptUpdateData.setPsn(ssu.getCard().getPANSequence());
            scriptUpdateData.setExpiryDate(DateHelper.getTimestampUSFormat(DateHelper.fromStringTolong(
                    ssu.getCard().getExpirationDate())));
            
        }
        scriptUpdateData.setDatePublished(DateHelper.fromLongStringToTimeStamp(ssu.getDatePublished()));
        scriptUpdateData.setScriptDate(newDate);

//        if (!DataUtil.isNull(ssu.getBusinessFunction()))
//        {
//            Vector bfList = getESPBusinessFunctionByAlias(ssu.getBusinessFunction().getFunctionName());
//            if (bfList != null && !bfList.isEmpty())
//            {
//                ESPBusinessFunction bf = (ESPBusinessFunction) bfList.get(0);
//                scriptUpdateData.setBusinessFunction(bf);
//            }
//        }

        if (!DataUtil.isNull(ssu.getScriptOrder()))
        {
            scriptUpdateData.setScriptOrder(ssu.getScriptOrder().intValue());
        }

        //For parameter based Scripts.
        changeParameterValues(scriptUpdateData, ssu);

        //This should simply be SCRIPT STATUS i.e setScriptStatus.
        if (!DataUtil.isNull(ssu.getScriptUpdateStatus()))
        {
            scriptUpdateData.setScriptLifecycleStatus(ssu.getScriptUpdateStatus().value());
        }
        if (hasScriptDeliveryStatus(ssu))
        {
            scriptUpdateData.setScriptDeliveryStatus(ssu.getScriptDeliveryStatus().getDeliveryStatus());
        }
    }

    /**
     * This method updates the EMV Parameter values fro a particular Business Function.
     * <p/>
     * @param ssu The XML Object received via the script.
     */
    private void changeParameterValues(ESPScriptUpdateStatus scriptUpdateData, ScriptStatusUpdate ssu)
    {
        if (isParameterScript(ssu))
        {
            boolean docontinue;
            List<NVPType> paramData = ssu.getScriptDataItem();
            Vector bfList = getESPBusinessFunctionByAlias(ssu.getBusinessFunction().getFunctionName());
            docontinue = !bfList.isEmpty();
            if (docontinue)
            {
                //This is what we have configured.
                ESPBusinessFunction bf = (ESPBusinessFunction) bfList.get(0);
                Vector paramDefinition = bf.getESPBusinessParameters();
                docontinue = !paramDefinition.isEmpty();
                //TO DO : Refactor this to pass in isDeleted(ssu) results to updateEMVParamvalues and remove the if test.
                if (docontinue)
                {
                    ESPScriptStatusParameter storeParams = scriptUpdateData.getScriptStatusParameters();
                    if (isDeleted(ssu))
                    {
                        //IF delete then remove params if the params already exist, stored from STAGED script status.
                        //(so check staged and params exist)
                        Iterator<NVPType> it = paramData.iterator();
                        
                        updateEMVParamvalues(scriptUpdateData, storeParams, it, paramDefinition, 1);
                        bf.setESPBusinessParameters(paramDefinition);
                    }
                    //And if STAGED, SENT or DELIVERED Then store that parameters.
                    Iterator<NVPType> it = paramData.iterator();
                    updateEMVParamvalues(scriptUpdateData, storeParams, it, paramDefinition, 2);
                }
            }
        }
    }

    /**
     * Updates EMV parameters.
     * <p/>
     * @param paramListFromRequest
     * @param storedParamsIt
     * @param updateType     Delete (1) or Update (2)
     */
    private void updateEMVParamvalues(ESPScriptUpdateStatus scriptUpdateData,
                                      ESPScriptStatusParameter storedparams,
                                      Iterator<NVPType> paramListFromRequest,
                                      Vector configuredParamList, int updateType)
    {
        while (paramListFromRequest.hasNext())
        {
            NVPType requestParamItem = paramListFromRequest.next();
            Iterator definedParams = configuredParamList.iterator();
            
            while (definedParams.hasNext())
            {
                ESPBusinessParameter definedInstance = (ESPBusinessParameter) definedParams.next();
                NVPType definedNVP = createNewType(definedInstance.getName(), definedInstance.getValueDefault());
                boolean isParamInRequestDefined = definedNVP.getName().equals(requestParamItem.getName());
                //if defined params is in the request.
                if (isParamInRequestDefined)
                {
                    if (storedparams == null)
                    {
                        ESPScriptStatusParameter pInstance = getBusinessParameterStatusInstance(true);
                        pInstance.setEspBusinessParameters(definedInstance);
                        pInstance.setParameterValue(requestParamItem.getValue());
                        pInstance.setScriptUpdateStatus(scriptUpdateData);
                        pInstance.setDateCreated(DateHelper.today());
                        pInstance.setDateModified(DateHelper.today());
                        scriptUpdateData.setScriptStatusParameters(pInstance);
                    }
                    else
                    {
                        //Iterator storedIt = storedparams.iterator();
                        //while (storedIt.hasNext())
                        //{
                            ESPScriptStatusParameter storedInstance = storedparams;//(ESPScriptStatusParameter) storedIt.next();            //Convert it to NVPType so that we can compare easily.
                            NVPType storedNVP = createNewType(storedInstance.getEspBusinessParameters().getName(),
                                                              storedInstance.getParameterValue());
                            boolean storedParamIsDefined = storedNVP.equals(definedNVP);
                            boolean isStoredSameAsInRequest = storedNVP.equals(requestParamItem);
                            //if both request and store params are defined
                            if (storedParamIsDefined)
                            {
                                if (isStoredSameAsInRequest)
                                {
                                    //If deleted then remove
                                    if (updateType == 1)
                                    {
                                        //storedparams.remove(storedInstance);
                                        //Ideally to default
                                        ESPScriptStatusParameter statsuParam = scriptUpdateData.getScriptStatusParameters();
                                        ESPBusinessParameter defaultParam = statsuParam.getEspBusinessParameters();
                                        statsuParam.setParameterValue(defaultParam.getValueDefault());
                                        //scriptUpdateData.setScriptStatusParameters(storedparams.getEspBusinessParameters());
                                    }
                                    else
                                    {
                                        //Else change the param value
                                        storedInstance.setParameterValue(requestParamItem.getValue());                                        
                                    }                                    
                                }
                                else
                                {
                                    //this means the request param is defined but not stored for updates.
                                    //for this tracking ref and card
                                    ESPScriptStatusParameter pInstance = getBusinessParameterStatusInstance(true);
                                    pInstance.setEspBusinessParameters(definedInstance);
                                    pInstance.setParameterValue(requestParamItem.getValue());
                                    scriptUpdateData.setScriptStatusParameters(pInstance);
                                    //storedparams.add(pInstance);
                                }
                            }
                        //}
                    }
                    
                }
            }
        }
        
    }
    
//    private boolean 

    /**
     *
     * @param name
     * @param value
     * <
     * p/>
     * @return
     */
    private NVPType createNewType(String name, String value)
    {
        NVPType newNVP = new NVPType();
        newNVP.setName(name);
        newNVP.setValue(value);
        return newNVP;
    }

    /**
     *
     * @param createNew
     * <
     * p/>
     * @return
     */
    private ESPScriptStatusParameter getBusinessParameterStatusInstance(boolean createNew)
    {
        ESPScriptStatusParameter parameterInstance = null;
        if (createNew)
        {
            parameterInstance = peristent.getRegisteredObject(ESPScriptStatusParameter.class);
        }
        return parameterInstance;
        
    }

    /**
     * R.8-1 Generate Script date based on Script life cycle statuses.
     * <p/>
     * @param ssu ScriptStatusUpdate
     * <p/>
     * @return Timestamp
     */
    private Timestamp getScriptDate(ScriptStatusUpdate ssu)
    {
        Timestamp dateTime = null;
        //Date For Staged scripts
        if (isStaged(ssu))
        {
            dateTime = DateHelper.getTimestampDefaultFormat(DateHelper.fromStringTolong(ssu.getDatePublished()));
        }
        //For Deleted scripts
        if (isDeleted(ssu))
        {
            dateTime = DateHelper.getTimestampDefaultFormat(DateHelper.fromStringTolong(
                    ssu.getDeletionDetails().getDeletedDate()));
        }
        //For Delivered Scripts
        if (isDelivered(ssu))
        {
            dateTime = DateHelper.getTimestampDefaultFormat(DateHelper.fromStringTolong(
                    ssu.getTransactionDetails().getTransactionDate()));
        }
        //If no status yet
        if (dateTime == null)
        {
            Date d = new Date();
            dateTime = DateHelper.getTimestampUSFormat(d);
        }
        return dateTime;
    }

    /**
     * 1.(Ref3.2.3)Script order needs to be checked because since messages can come out of order, script-order with a
     * value lower than the script-order of a previous script can arrive later, which should be ignored as scripts with
     * a higher script-order takes precedence over one with a lower script-order.
     * <p/>
     * @param trackingRef < p/>
     * <p/>
     * @return
     */
    private ESPScriptUpdateStatus getExistingStatusUpdateData(String trackingRef)
    {
        Vector existingUpdateDataList = getESPScriptUpdateStatusByTrackingReference(trackingRef);
        if (existingUpdateDataList.isEmpty())
        {
            return null;
        }
        ESPScriptUpdateStatus existingUpdateData = (ESPScriptUpdateStatus) existingUpdateDataList.get(0);
        return existingUpdateData;
    }

    /**
     * Non-parameter based scripts have the empty element <ScriptDataItem/>, whereas parameter based scripts have data
     * for this element.
     * <p/>
     * This method returns true if Script represented by ScriptStatusUpdate is a Parameter script or else returns false.
     * <p/>
     * @param data ScriptStatusUpdate XML Object
     * <p/>
     * @return True if data is a parameter script
     */
    private boolean isParameterScript(ScriptStatusUpdate data)
    {
        return data != null && data.getScriptDataItem() != null && !data.getScriptDataItem().isEmpty();
    }

    /**
     * Returns true if current script order is less than the newly arrived script order.
     * <p/>
     * @param currBusFunction Current Business function recorded.
     * @param newBusFunction  Newly arrived status via SriptStatusUpdate message alert.
     * <p/>
     * @return true as described above.
     */
    private boolean doUpdateScriptStatus(int currentScriptOrder, int arrivedScriptOrder)
    {
        return (currentScriptOrder < arrivedScriptOrder);
    }

    /**
     * Checks to see if the currently stored script status is in an earlier life cycle state to the newly arrived state.
     * Also it checks to see if the new script is re-staged, which amounts to the current state being earlier than new.
     * <p/>
     * @param curStatus Status stored
     * @param newStatus New status as part of the request.
     * @param sds       < p/>
     * <p/>
     * @return true or false as above.
     */
    private boolean isCurrLifecycleStateEarlierOrRestagedScript(String curStatus, ScriptStatusUpdateType newStatus,
                                                                ScriptDeliveryStatus sds)
    {
        int currStatusOrder = statusOrderMap.get(curStatus);
        int newStatusOrder = statusOrderMap.get(newStatus.name());
        boolean isCurrStateEarlier = currStatusOrder <= newStatusOrder;
        return isCurrStateEarlier
                || sds.getDeliveryStatus().equals(SEMScriptDeliveryStatus.DELIVERY_FAILED_SCRIPT_RESTAGED.toString());
    }

    /**
     *
     * @param ssu < p/>
     * <p/>
     * @return
     */
    private boolean isDeliveryScript(ScriptStatusUpdate ssu)
    {
        return isDelivered(ssu);
    }

    /**
     *
     * @param ssu < p/>
     * <p/>
     * @return
     */
    private boolean isStaged(ScriptStatusUpdate ssu)
    {
        return ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.STAGED;
    }

    /**
     * REVIEW
     * <p/>
     * @param ssu < p/>
     * <p/>
     * @return
     */
    private boolean isPreStaged(ScriptStatusUpdate ssu)
    {
        return ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.STAGED;
    }

    /**
     *
     * @param ssu < p/>
     * <p/>
     * @return
     */
    private boolean isDeleted(ScriptStatusUpdate ssu)
    {
        return ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELETED;
    }

    /**
     *
     * @param ssu < p/>
     * <p/>
     * @return
     */
    private boolean isDelivered(ScriptStatusUpdate ssu)
    {
        return ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELIVERED;
    }

    /**
     *
     * @param ssu < p/>
     * <p/>
     * @return
     */
    private boolean hasScriptDeliveryStatus(ScriptStatusUpdate ssu)
    {
        boolean isDeliveryScript = ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELIVERED
                || ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.SENT
                || ssu.getScriptUpdateStatus() == ScriptStatusUpdateType.DELETED;
        return isDeliveryScript && ssu.getScriptDeliveryStatus() != null;
    }

    /**
     * Helper method to extract ESPParameter.
     * <p/>
     * @param trackingRef < p/>
     * <p/>
     * @return Collection of matching ESPParameter objects.
     */
    public static Vector getESPScriptUpdateStatusByTrackingReference(String trackingRef)
    {
        return peristent.getObjectByName(trackingRef, ESPScriptUpdateStatus.class, "trackingReference");
    }

    /**
     *
     * @param softCard < p/>
     * <p/>
     * @return
     */
    public static ESPScriptUpdateStatus getESPScriptUpdateStatusBySoftCardOID(SoftCard softCard)
    {
        return peristent.getObjectByReferencingObject(softCard, ESPScriptUpdateStatus.class, "softCard");
    }

    /**
     *
     * @param ssu
     * <
     * p/>
     * @return
     */
    public static ESPScriptUpdateStatus getESPScriptUpdateStatusBySoftCardOID(ScriptStatusUpdate ssu)
    {
        ExpressionBuilder builder = new ExpressionBuilder();
        Expression expPAN = builder.get("pan").equal(ssu.getCard().getPAN());        
        Expression expPSN = builder.get("psn").equal(ssu.getCard().getPANSequence());        
        long expDate = DateHelper.fromStringTolong(ssu.getCard().getExpirationDate());
        Expression expEXPD = builder.get("expiryDate").equal(DateHelper.getTimestampUSFormat(expDate));        
        Expression expAll = expPAN.and(expPSN).and(expEXPD);
        Vector v = peristent.executeReadQuery(expAll, ESPScriptUpdateStatus.class, null, (String[]) null);
        if (v != null && !v.isEmpty())
        {
            return (ESPScriptUpdateStatus) v.get(0);
        }
        return null;
    }

    /**
     *
     * @param bf < p/>
     * <p/>
     * @return
     */
    public static Vector getESPBusinessParameterByBusinessFunction(ESPBusinessFunction bf)
    {
        return peristent.getAllObjectsByReferencingObject(bf, ESPBusinessParameter.class, "businessFunction");
    }

    /**
     *
     * @param bfName < p/>
     * <p/>
     * @return
     */
    public static Vector getESPBusinessFunctionByAlias(String bfName)
    {
        return peristent.getObjectByName(bfName, ESPBusinessFunction.class, "alias");
    }
}
/**
 *
 * else { while (fromRequest.hasNext()) { NVPType requestParamItem = fromRequest.next(); Iterator storedParamsIt =
 * paramDefinition.iterator(); while (storedParamsIt.hasNext()) { //ESPScriptStatusParameter ESPBusinessParameter emvP =
 * (ESPBusinessParameter) storedParamsIt.next(); boolean nameIsSame =
 * emvP.getName().equalsIgnoreCase(requestParamItem.getName()); boolean valueIsSame =
 * emvP.getValueDefault().equalsIgnoreCase(requestParamItem.getValue()); String value = requestParamItem.getValue(); if
 * (updateType == 2) { if (nameIsSame) { emvP.setValueDefault(value); break; } } else if (updateType == 1) { if
 * (nameIsSame && valueIsSame) { //Set this to null or default value. Presently it is set to null.
 * emvP.setValueDefault(null); break; } } } } }
 * <p/>
 */
