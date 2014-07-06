/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.data;

import org.junit.Test;

/**
 * @author wakkir.muzammil
 */

public class TestData
{

    public static final String CARD_SETUP_ALERT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CardSetupAlert><TrackingReference>CardSetupAlert_TtrackId_01</TrackingReference></CardSetupAlert>";

    public static final String STAGE_SCRIPT_ALERT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><StageScriptAlert><TrackingReference>StageScriptAlert_TrackId_01</TrackingReference></StageScriptAlert>";

    public static final String CARD_SETUP_RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CardSetupResponse><TrackingReference>CardSetupResponse_TrackId_01</TrackingReference><status>STATUS_OK</status></CardSetupResponse>";

    public static final String STAGE_SCRIPT_RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ScriptStatusResponse><TrackingReference>CardSetupResponse_TrackId_01</TrackingReference><status>STATUS_OK</status></ScriptStatusResponse>";

    @Test
    public void testNone()
    {
        assert CARD_SETUP_ALERT_XML.length() > 0 && CARD_SETUP_ALERT_XML.contains("CardSetupAlert");
        assert STAGE_SCRIPT_ALERT_XML.length() > 0 && STAGE_SCRIPT_ALERT_XML.contains("StageScriptAlert");
        assert CARD_SETUP_RESPONSE_XML.length() > 0 && CARD_SETUP_RESPONSE_XML.contains("CardSetupResponse");
        assert STAGE_SCRIPT_RESPONSE_XML.length() > 0 && STAGE_SCRIPT_RESPONSE_XML.contains("ScriptStatusResponse");
    }
}



