#!/bin/bash

export WRAPPER_JARS=
export AWSC_JARS=aconite-ics-wrapper-app.jar:aconite-ics-converter-app.jar

java -cp $AWSC_JARS:$WRAPPER_JARS net.aconite.wrapper.client.WrapperClientMain stop

export PID_LOG=$AWSC_HOME/awsc*.pid


while : ; do
    [[ ! -f $PID_LOG ]] && break
    sleep 2
done


echo "AWSC services stopped"