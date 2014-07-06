call mvn install:install-file -Dfile=jdbc-11.2.0.3.jar -DgroupId=net.aconite.affina -DartifactId=datasource.oracle11g -Dversion=11.2.0.3 -Dpackaging=jar

call mvn install:install-file -Dfile=jms.jar -DgroupId=com.ibm -DartifactId=jms -Dversion=7.5.0.0 -Dpackaging=jar

call mvn install:install-file -Dfile=sqljdbc4-4.0.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.4.0 -Dpackaging=jar

