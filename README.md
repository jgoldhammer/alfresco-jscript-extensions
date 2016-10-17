alfresco-jscript-extensions
===========================

[![Build Status](https://travis-ci.org/jgoldhammer/alfresco-jscript-extensions.svg?branch=master)](https://travis-ci.org/jgoldhammer/alfresco-jscript-extensions)

Alfresco repository module with helpful javascript root object extensions which are helpful in much scenarios.

Usage
--------

Add the dependencies to the Alfresco repository and share POM files of your WAR projects.

Alfresco SDK 2.x

    <dependencies>
      ...
      <dependency>
        <groupId>de.jgoldhammer</groupId>
        <artifactId>alfresco-jscript-extension</artifactId>
        <version>1.1</version>
        <type>amp</type>
      </dependency>
      ...
    </dependencies>

    <overlays>
      ...
      <overlay>
        <groupId>de.jgoldhammer</groupId>
        <artifactId>alfresco-jscript-extension</artifactId>
        <version>1.1</version>
        <type>amp</type>
      </overlay>
      ...
    </overlays>

Alfresco SDK 3.x

	<platformModules>
		<moduleDependency>
			<groupId>de.jgoldhammer</groupId>
	        	<artifactId>alfresco-jscript-extension</artifactId>
	        	<version>1.1</version>
	        	<type>amp</type>
        	</moduleDependency>
	</platformModules>
   
Following root objects are provided:
* **auth**
	* to change the authentication to another user during the javascript execution
	* to get the current executing authenticated user
	* example: tbd
		
* **batch** 
	* to provide a simple batch processing for javascript without any native functions
	* example: https://gist.github.com/jgoldhammer/6941273
	
* **database** 
	* to provide access to datasources (contributed by Florian Maul)
	* run queries on the alfresco registered datasources like the standard sql database
	* update data on the alfresco registered datasources like the standard sql database
	* example: https://gist.github.com/jgoldhammer/e9aa16554e8cfe644d2fa9f85d63fbb3

* **downloads**
	* create downloads for nodes in the repository 
	* example: https://gist.github.com/jgoldhammer/5b751b1c31e88d555b17aba9e6944d0f

* **favorites**
	* add a favorite
	* remove a favorite
	* check if a node is a favorite
	* get all favorites of a user
	* example: https://gist.github.com/jgoldhammer/2ff5df55407c79c3240f318873888b00

* **fileWriter**
	* allows to create files during a longer processing on the server filesystem and persist it afterwards in the alfresco 
	repository or send the attachment by mail 
	* example: https://gist.github.com/jgoldhammer/77feac7db51a8bfba0033625108bf029
	
* **policies** 
	* to temporarly disable or enable policy behavours during javascript extecution
	* policy behaviours can be enabled or disabled on a specific node or a certain aspect/type
	* example: https://gist.github.com/jgoldhammer/6941414
	
* **jobs** 
	* list all alfresco repo background jobs
	* start jobs in javascript via quartz Scheduler
	* create new temporary jobs which execute javascript logic
	
	* example to list jobs:
	* example to create a temporary quartz job with javascript execution: 
	 https://gist.github.com/jgoldhammer/e6a91672da18ba90fd3a978b8737fb8e
	
* **permissions**
	* root object which exposes the permission service to javascript
	* ask for permissions on nodes
	* set permissions on nodes
	* clear permissions on nodes
	* example:

* **repoAdmin** 
	* uses the repo admin interpreter from the repo admin console
	* write your commands and get back the result in the js-console
	* example 1: ```print(repoAdmin.exec('help'))``` - show the help
	* example 2: ```print(reopAdmin.exec('show models'));```  - show all additional datamodels
	
* **tenantAdmin** 
	* uses the repo admin interpreter from the repo admin console
	* write your commands and get back the result in the js-console
	* example 1: ```print(tenantAdmin.exec('help'))``` - show the help
	* example 2: ```print(tenantAdmin.exec('show tenants'));```  - show all tenants (if you create one before!)
	
* **trans** 
	* create a new transaction
	* start, commit and rollback (database) transactions during the javascript execution
	* example: tbd
	
* **workflowAdmin** 
	* uses the workflowinterpreter from the workflow console
	* write your commands and get back the result in the js-console
	* example 1: ```print(workflowAdmin.exec('help'))``` - show the help
	* example 2: ```print(workflowAdmin.exec('show definitions'));```  - show all workflow definitions
 
Building
--------

To build the module and its AMP / JAR files, run the following command from the base 
project directory:

    mvn install




