/*
 * Copyright (C) 2008-2015 Citeck LLC.
 *
 * This file is part of Citeck EcoS
 *
 * Citeck EcoS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Citeck EcoS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Citeck EcoS. If not, see <http://www.gnu.org/licenses/>.
 */
package de.jgoldhammer.alfresco.jscript.permission;

import com.google.common.base.Preconditions;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.security.AccessPermission;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.PermissionService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * encapsulates some permissionService methods related to nodes.
 * <p>
 * copied from https://github.com/Citeck/ecos-community/blob/166a54543d770caf8153297f4fbe1ff6868f9f2a/idocs-repo/source/java/ru/citeck/ecos/security/ScriptPermissionService.java
 */
public class ScriptPermissionService extends BaseScopableProcessorExtension {

	private PermissionService permissionService;


	public boolean hasReadPermission(String nodeRef){
		Preconditions.checkNotNull(nodeRef);
		return permissionService.hasReadPermission(new NodeRef(nodeRef))==AccessStatus.ALLOWED;
	}

	public boolean hasPermission(String nodeRef, String permission){
		Preconditions.checkNotNull(nodeRef);
		Preconditions.checkNotNull(permission);

		return permissionService.hasPermission(new NodeRef(nodeRef), permission)==AccessStatus.ALLOWED;

	}

	/**
	 * sets a new permission for a certain authority on a certain node by specifying if the permission is allowed or denied.
	 *
	 * @param nodeRefId the node to set the permission on
	 * @param permission the name of the permission (see https://github.com/Alfresco/community-edition/blob/master/projects/repository/config/alfresco/model/permissionDefinitions.xml for the names)
	 * @param authority the name of the authority- a username or a group name!
	 * @param allow true if the permission should be allowed, false if denied
	 */
	public void setPermission(
			String nodeRefId,
			String permission,
			String authority,
			boolean allow) {

		Preconditions.checkNotNull(nodeRefId);
		Preconditions.checkNotNull(permission);
		Preconditions.checkNotNull(authority);

		NodeRef nodeRef = new NodeRef(nodeRefId);
		permissionService.setPermission(
				nodeRef,
				authority,
				permission,
				allow
		);
	}

	/**
	 * delete a certain permission for a certain authority on a certain node.
	 *
	 * @param nodeRefId the node to delete the permission from
	 * @param permission the name of the permission (see https://github.com/Alfresco/community-edition/blob/master/projects/repository/config/alfresco/model/permissionDefinitions.xml for the names)
	 * @param authority the name of the authority- a username or a group name!
	 */

	public void deletePermission(
			final String nodeRefId,
			final String permission,
			final String authority) {

		Preconditions.checkNotNull(nodeRefId);
		Preconditions.checkNotNull(permission);
		Preconditions.checkNotNull(authority);

		NodeRef nodeRef = new NodeRef(nodeRefId);
		permissionService.deletePermission(
				nodeRef,
				authority,
				permission
		);
	}

	/**
	 * delete all permissions on a certain node.
	 *
	 * @param nodeRefId the noderef - cannot be null
	 */
	public void deletePermissions(String nodeRefId){
		Preconditions.checkNotNull(nodeRefId);
		NodeRef nodeRef = new NodeRef(nodeRefId);
		permissionService.deletePermissions(
				nodeRef);
	}

	/**
	 * removes all permissions of an authority on a certain node.
	 *
	 * @param nodeRefId the node
	 * @param authority the authority which must exist
	 */

	public void clearPermissions(
			final String nodeRefId,
			final String authority) {
		Preconditions.checkNotNull(nodeRefId);
		Preconditions.checkNotNull(authority);

		NodeRef nodeRef = new NodeRef(nodeRefId);
		permissionService.clearPermission(
				nodeRef,
				authority
		);
	}


	public void deleteStorePermissions(
			final String storeProtocol,
			final String storeId) {
		StoreRef storeRef = new StoreRef(storeProtocol, storeId);
		permissionService.deletePermissions(
				storeRef
		);
	}

	public void clearStorePermission(
			final String storeProtocol,
			final String storeId,
			final String authority) {
		StoreRef storeRef = new StoreRef(storeProtocol, storeId);
		permissionService.clearPermission(
				storeRef,
				authority
		);
	}


	public void setInheritParentPermissions(
			final String nodeRefId,
			final String inheritParentPermissions) {
		NodeRef nodeRef = new NodeRef(nodeRefId);
		permissionService.setInheritParentPermissions(
				nodeRef,
				new Boolean(inheritParentPermissions)
		);
	}

	public boolean isInheritParentPermissions(final String nodeRefId) {
		NodeRef nodeRef = new NodeRef(nodeRefId);
		Boolean result = permissionService.getInheritParentPermissions(nodeRef);
		return result.booleanValue();
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
}