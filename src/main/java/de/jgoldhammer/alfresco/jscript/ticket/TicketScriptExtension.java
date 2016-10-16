/*
    Copyright (C) 2007-2011  BlueXML - www.bluexml.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/


package de.jgoldhammer.alfresco.jscript.ticket;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.security.authentication.TicketComponent;
import org.alfresco.service.ServiceRegistry;
import org.apache.log4j.Logger;

/**
 * retrieves the ticket for the current user.
 */
public class TicketScriptExtension extends BaseScopableProcessorExtension {
	/**
	 * @return the serviceRegistry
	 */
	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	/**
	 * @param serviceRegistry
	 *            the serviceRegistry to set
	 */
	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	ServiceRegistry serviceRegistry;
	TicketComponent ticketComponent;

	/**
	 * @return the ticketComponent
	 */
	public TicketComponent getTicketComponent() {
		return ticketComponent;
	}

	/**
	 * @param ticketComponent
	 *            the ticketComponent to set
	 */
	public void setTicketComponent(TicketComponent ticketComponent) {
		this.ticketComponent = ticketComponent;
	}

	private Logger logger = Logger.getLogger(getClass());

	public String getCurrentTicket() {
		return serviceRegistry.getAuthenticationService().getCurrentTicket();
	}

}