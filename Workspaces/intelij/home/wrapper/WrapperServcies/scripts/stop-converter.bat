@rem set WRAPPER_JARS=aopalliance-1.0.jar;commons-logging-1.1.1.jar;quartz-1.8.6.jar;slf4j-api-1.6.0.jar;spring-aop-3.1.2.RELEASE.jar;spring-asm-3.1.2.RELEASE.jar;spring-beans-3.1.2.RELEASE.jar;spring-context-3.1.2.RELEASE.jar;spring-context-support-3.1.2.RELEASE.jar;spring-core-3.1.2.RELEASE.jar;spring-expression-3.1.2.RELEASE.jar;spring-tx-3.1.2.RELEASE.jar
set WRAPPER_JARS=
set AWSC_JARS=aconite-ics-wrapper-app.jar;aconite-ics-converter-app.jar

java -cp %AWSC_JARS%;%WRAPPER_JARS% net.aconite.wrapper.client.WrapperClientMain stop