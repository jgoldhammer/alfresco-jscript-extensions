::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::      Dev environment startup script for Alfresco Community.    ::
::                                                                ::
::      Downloads the spring-loaded lib if not existing and       ::
::      runs the Repo AMP applied to Alfresco WAR.                ::
::      Note. the Share WAR is not deployed.                      ::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@echo off

set MAVEN_OPTS=-Xms256m -Xmx2G

mvn -Dmaven.tomcat.port=8181 integration-test -Pamp-to-war -nsu
:: mvn integration-test -Pamp-to-war 
