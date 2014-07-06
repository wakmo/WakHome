#!/bin/bash

export AWSC_HOME=/home/awsc/aconite/converter

export MQ_JARS=com.ibm.mq.headers.jar:com.ibm.mq.jmqi.jar:com.ibm.mqjms.jar:dhbcore.jar:jms.jar
export WRAPPER_JARS=
export AWSC_JARS=aconite-ics-wrapper-app.jar:aconite-ics-converter-app.jar

java -Dcom.ibm.msg.client.commonservices.log.status=OFF -Dcom.ibm.msg.client.commonservices.ffst.suppress=1 -Dlog4j.configuration=file:$AWSC_HOME/log4j.properties -Dmq-converter-config-dir=$AWSC_HOME -cp $AWSC_JARS:$WRAPPER_JARS:$MQ_JARS net.aconite.wrapper.service.WrapperServerMain &



