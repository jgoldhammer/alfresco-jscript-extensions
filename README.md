alfresco-jscript-extensions
===========================

Alfresco repository module with helpful javascript root object extensions which are helpful in much scenarios.
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
	 
* **policies** 
	* to temporarly disable or enable policy behavours during javascript extecution
	* policy behaviours can be enabled or disabled on a specific node or a certain aspect/type
	* example: https://gist.github.com/jgoldhammer/6941414
	
* **jmxClient** 
	* to provide access to the alfresco global properties and system properties of the system
	* list all propertiy values or get a specific property value
	* example: https://gist.github.com/jgoldhammer/6941512
	
* **jobs** 
	* list all alfresco repo background jobs
	* start jobs in javascript via JMX
	* example: https://gist.github.com/jgoldhammer/6941374

* **solr** 
	* currently get the index state of a node (currently not working due to a bug in the SolrIndexService)
	* example: tbd
	
* **trans** 
	* create a new transaction
	* start, commit and rollback (database) transactions during the javascript execution
	* example: tbd
 
Building
--------

To build the module and its AMP / JAR files, run the following command from the base 
project directory:

    mvn install




