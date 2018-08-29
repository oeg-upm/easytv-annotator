#!/bin/bash
call mvn install:install-file -Dfile=lib/babelnet-api-3.7.1.jar -DgroupId=it.uniroma1.lcl.babelnet -DartifactId=babelnet-api -Dversion=3.7.1 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/jltutils-2.2.jar -DgroupId=it.uniroma1.lcl.jlt -DartifactId=jltutils -Dversion=2.2 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/babelfy-online-1.0.jar -DgroupId=it.uniroma1.lcl.babelfy -DartifactId=babelfy-online -Dversion=1.0 -Dpackaging=jar
call mvn install:install-file -Dfile=lib/babelfy-commons-1.0.jar -DgroupId=it.uniroma1.lcl.babelfy.commons -DartifactId=babelfy-commons -Dversion=1.0 -Dpackaging=jar