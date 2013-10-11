alfresco-jscript-extensions
===========================

Alfresco repository module with helpful javascript root object extensions which are helpful in much scenarios.

Following root objects are provided:
* **auth**
	*	to change the authentication to another user during the javascript execution
	*	to get the current executing authenticated user
		
* **batch** 
	* to provide s simple batch processing for javascript without any native functions
	 
* **policies** 
	* to temporarly disable or enable policy behavours during javascript extecution
	* policy behaviours can be enabled or disabled on a specific node or a certain aspect/type
	
* **jmxClient** 
	* to provide access to the alfresco global properties and system properties of the system
	* list all propertiy values or get a specific property value
	
* **jobs** 
	* list all alfresco repo background jobs
	* start jobs in javascript via JMX

* **solr** 
	* currently get the index state of a node (currently not working due to a bug in the SolrIndexService)
	
* **trans** 
	* create a new transaction
	* start, commit and rollback (database) transactions during the javascript execution
 




