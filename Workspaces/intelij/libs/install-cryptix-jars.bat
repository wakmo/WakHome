call mvn install:install-file -Dfile=cryptix-asn1.jar -DgroupId=cryptix -DartifactId=cryptix-asn1 -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=cryptix-jce-provider-20050328.jar -DgroupId=cryptix-jce -DartifactId=cryptix-jce-provider -Dversion=20050328 -Dpackaging=jar

REM mvn deploy:deploy-file -DgroupId=cryptix -DartifactId=cryptix-asn1 -Dversion=1.0 -Dpackaging=jar -Dfile=J:\modules\Sci\SCI-R1.1.9\Sci\externalJars\cryptix-asn1.jar -DrepositoryId=installed -Durl=file:///J:\build-framework\maven/repos/installed


