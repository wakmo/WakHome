set AWSC_HOME=C:/Wakkir/Work/TSMConvertor/linux/all-in-wrapper

set MQ_JARS=com.ibm.mq.headers.jar;com.ibm.mq.jmqi.jar;com.ibm.mqjms.jar;dhbcore.jar;jms.jar
set WRAPPER_JARS=
set AWSC_JARS=aconite-ics-wrapper-app.jar;aconite-ics-converter-app.jar

java -Dcom.ibm.msg.client.commonservices.log.status=OFF -Dcom.ibm.msg.client.commonservices.ffst.suppress=1 -Dlog4j.configuration=file:%AWSC_HOME%/log4j.properties -Dmq-converter-config-dir=%AWSC_HOME% -cp %AWSC_JARS%;%WRAPPER_JARS%;%MQ_JARS% net.aconite.wrapper.service.WrapperServerMain




#rem -Dlog4j.configuration=file:C://Wakkir//W3Repo//Workspaces//intelij//home//wrapper//WrapperServcies//src//main//resources//log4j.properties -Dmq-converter-config-dir=C://Wakkir//W3Repo//Workspaces//intelij//home//wrapper//WrapperServcies//src//main//resources -Dmq-converter-log-dir=C://Wakkir//W3Repo//Workspaces//intelij//home//wrapper//WrapperServcies//logs