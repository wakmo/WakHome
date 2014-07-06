call mvn install:install-file -Dfile=json-lib-2.4.jar -DgroupId=net.sf.json -DartifactId=json-lib -Dversion=2.4 -Dpackaging=jar

call mvn install:install-file -Dfile=ehcache-1.2.3.jar -DgroupId=net.sf.ehcache -DartifactId=ehcache -Dversion=1.2.3 -Dpackaging=jar

call mvn install:install-file -Dfile=ezmorph-1.0.6.jar -DgroupId=net.sf.ezmorph -DartifactId=ezmorph -Dversion=1.0.6 -Dpackaging=jar

call mvn install:install-file -Dfile=image/metadata-extractor-2.6.4.jar -DgroupId=com.drew.imaging -DartifactId=metadata-extractor -Dversion=2.6.4 -Dpackaging=jar

call mvn install:install-file -Dfile=image/xmpcore-1.0.jar -DgroupId=com.adobe.xmp -DartifactId=xmpcore -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=barcode/BarcodeEngine v1.3.jar -DgroupId=com.tm.barcode -DartifactId=BarcodeEngine -Dversion=1.3 -Dpackaging=jar
call mvn install:install-file -Dfile=barcode/core.jar -DgroupId=com.google.zxing -DartifactId=aztec -Dversion=1.0 -Dpackaging=jar
call mvn install:install-file -Dfile=barcode/javase.jar -DgroupId=com.google.zxing -DartifactId=client -Dversion=1.0 -Dpackaging=jar