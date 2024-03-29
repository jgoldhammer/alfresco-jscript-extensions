alfresco-jscript-extensions
===========================

Alfresco  (Master) [![Build Status](https://travis-ci.org/jgoldhammer/alfresco-jscript-extensions.svg?branch=master)](https://travis-ci.org/jgoldhammer/alfresco-jscript-extensions)
Alfresco repository module with helpful javascript root object extensions which are helpful in much scenarios for Alfresco 5.1 and higher.

Important: Alfresco 4.2 - 5.0 is not supported anymore.

Maven usage
------------------------------

Alfresco SDK 2.x

Add the dependencies to the Alfresco repository POM files of your WAR projects.

    <dependencies>
      ...
      <dependency>
        <groupId>de.jgoldhammer</groupId>
        <artifactId>alfresco-jscript-extension</artifactId>
        <version>1.7</version>
        <type>amp</type>
      </dependency>
      ...
    </dependencies>

    <overlays>
      ...
      <overlay>
        <groupId>de.jgoldhammer</groupId>
        <artifactId>alfresco-jscript-extension</artifactId>
        <version>1.7</version>
        <type>amp</type>
      </overlay>
      ...
    </overlays>

Alfresco SDK 3.x

Add the dependencies to the Alfresco repository POM files of your WAR projects.

	<platformModules>
		<moduleDependency>
			<groupId>de.jgoldhammer</groupId>
	        	<artifactId>alfresco-jscript-extension</artifactId>
	        	<version>1.7</version>
	        	<type>amp</type>
        	</moduleDependency>
	</platformModules>

Alfresco SDK 4.x

Including the AMP artifact into an All-in-One project created from the archetype provided by Alfresco SDK 4, the following dependency must be added to the *-platform-docker sub-module of the generated project:

	<dependency>
		<groupId>de.jgoldhammer</groupId>
		<artifactId>alfresco-jscript-extension</artifactId>
		<version>1.8.0</version>
	</dependency>

# Functionality

The extension provides some root objects for java serivces in the alfresco api.
The root objects provides easier access via js-console or javascript based webscripts.

Following root objects are provided:
* **attributes**
	* get attributes by key
	* create attributes
	* remove attributes
	* example: tbd
	
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
	* example: https://gist.github.com/jgoldhammer/43ef55e32eabc5c281a95948dc7b93c7

* **repoAdmin** 
	* uses the repo admin interpreter from the repo admin console
	* write your commands and get back the result in the js-console
	* example 1: ```print(repoAdmin.exec('help'))``` - show the help
	* example 2: ```print(reopAdmin.exec('show models'));```  - show all additional datamodels
	
	
* **rules** 
	* enable and disable the rulesservice globally
	* enable and disable certain rules
	* enable and disable rules executation for certain nodes
	* count rules
	* example: https://gist.github.com/jgoldhammer/442b18c730438114925f57616205203e
	
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
 
# Build


To build the module and its AMP / JAR files, run the following command from the base 
project directory:

    mvn install

# Release


- Change the version number in pom.xml
- push the release to  sonatype nexus with following command
	

    .deploy.sh
	

# License

The project is using Apache 2.0 license - see See [LICENSE.md](./LICENSE.md)

Original authors:
- Jens Goldhammer, fme AG

