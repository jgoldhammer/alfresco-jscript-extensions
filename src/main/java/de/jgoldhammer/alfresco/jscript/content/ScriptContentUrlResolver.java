package de.jgoldhammer.alfresco.jscript.content;

import com.google.common.base.Preconditions;
import org.alfresco.repo.domain.node.Node;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * helps to get the content url of a node.
 *
 * https://github.com/magnus-larsson/my-alfresco/blob/97538c73268e3ca77e39e8cc37d6f61f8f90b4c5/repo/src/main/java/se/vgregion/alfresco/repo/scripts/ContentUrlResolver.java
 *
 */
public class ScriptContentUrlResolver extends BaseScopableProcessorExtension implements InitializingBean {

  private final static Logger LOG = Logger.getLogger(ScriptContentUrlResolver.class);

  private FileFolderService fileFolderService;

  public void setFileFolderService(final FileFolderService fileFolderService) {
    this.fileFolderService = fileFolderService;
  }

  public String getContentUrl(final String node) {
    Preconditions.checkNotNull(node);
    NodeRef nodeRef = new NodeRef(node);

    final FileInfo fileInfo = fileFolderService.getFileInfo(nodeRef);

    if (fileInfo == null || fileInfo.getContentData() == null) {
      throw new IllegalArgumentException("Cannot get the content date for this node");
    }
    return fileFolderService.getFileInfo(nodeRef).getContentData().getContentUrl();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(fileFolderService);
  }

}