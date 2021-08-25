package de.jgoldhammer.alfresco.jscript.db;

import com.google.common.base.Preconditions;
import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

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

	@ScriptMethod()
	public int update(String dataSourceName, String sql, Object... params) {
		JdbcDaoSupport daoSupport = getDaoSupport(dataSourceName);
		Preconditions.checkNotNull(daoSupport," daosupport is null- please check the datasource name");
		return daoSupport.getJdbcTemplate().update(sql, params);
	}

	public Map<String, Object>[] query(String dataSourceName, String sql, Object... params) {
		JdbcDaoSupport daoSupport = getDaoSupport(dataSourceName);
		Preconditions.checkNotNull(daoSupport," daosupport is null- please check the datasource name");

		List<Map<String, Object>> result = daoSupport.getJdbcTemplate().queryForList(sql, params);
		Map<String, Object>[] arr = new Map[result.size()];
		for (int i=0; i < result.size(); i++) {
			arr[i] = result.get(i);
		}
		return arr;
	}
	
	private JdbcDaoSupport getDaoSupport(String dataSourceName) {
		Object dsBean = applicationContext.getBean(dataSourceName);
		
		if (dsBean instanceof DataSource) {
			JdbcDaoSupport daoSupport = new NamedParameterJdbcDaoSupport();
			daoSupport.setDataSource((DataSource) dsBean);
			return daoSupport;
		}
		else {
			
			throw new AlfrescoRuntimeException("dataSource '" + dataSourceName + "' not found.");
		}
	}
	
}