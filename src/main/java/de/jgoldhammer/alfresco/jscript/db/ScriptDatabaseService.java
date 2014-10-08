package de.jgoldhammer.alfresco.jscript.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * javascript rootobject
 * 
 * @author jgoldhammer
 *
 */
@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code="database", help="the root object for the database service")
public class ScriptDatabaseService extends BaseProcessorExtension implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
				this.applicationContext = applicationContext;
	}

	public int update(String dataSourceName, String sql, Object... params) {
		SimpleJdbcDaoSupport daoSupport = getDaoSupport(dataSourceName);
		
		if (daoSupport != null) {
			return daoSupport.getSimpleJdbcTemplate().update(sql, params);
		} else {
			return 0;
		}
	}

	public Map<String, Object>[] query(String dataSourceName, String sql, Object... params) {
		SimpleJdbcDaoSupport daoSupport = getDaoSupport(dataSourceName);
		
		List<Map<String, Object>> result;
		if (daoSupport != null) {
			 result = daoSupport.getSimpleJdbcTemplate().queryForList(sql, params);
		} else {
			result = new ArrayList();
		}
		
		Map<String, Object>[] arr = new Map[result.size()];
		for (int i=0; i < result.size(); i++) {
			arr[i] = result.get(i);
		}
		return arr;
	}
	
	private SimpleJdbcDaoSupport getDaoSupport(String dataSourceName) {
		Object dsBean = applicationContext.getBean(dataSourceName);
		
		if (dsBean instanceof DataSource) {
			SimpleJdbcDaoSupport daoSupport = new SimpleJdbcDaoSupport();
			daoSupport.setDataSource((DataSource) dsBean);
			return daoSupport;
		}
		else {
			
			throw new AlfrescoRuntimeException("dataSource '" + dataSourceName + "' not found.");
		}
	}
	
}