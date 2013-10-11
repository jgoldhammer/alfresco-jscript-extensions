alfresco-jscript-extensions
===========================

Alfresco repository module with helpful javascript root object extensions which are helpful in much scenarios.

Following root objects are provided:
* auth (to change the authentication to another user during the javascript execution)
* batch (to provide s simple batch processing for javascript without any native functions)
* policies (to temporarly disable or enable policy behavours during javascript extecution=
* jmxClient (to provide access to the alfresco global properties and system properties of the system)
* jobs (interface to list all alfresco repo background jobs and to start jobs in javascript)
* solr (currently get the index state of a node- currently not working due to a bug in the SolrIndexService)
* trans (to start, commit and rollback (database) transactions during the javascript execution)
 




