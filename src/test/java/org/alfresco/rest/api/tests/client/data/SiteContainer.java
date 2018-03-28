/*
 * #%L
 * Alfresco Remote API
 * %%
 * Copyright (C) 2005 - 2016 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software. 
 * If the software was purchased under a paid Alfresco license, the terms of 
 * the paid license agreement will prevail.  Otherwise, the software is 
 * provided under the following open source license terms:
 * 
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.alfresco.rest.api.tests.client.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.alfresco.rest.api.tests.client.PublicApiClient.ExpectedPaging;
import org.alfresco.rest.api.tests.client.PublicApiClient.ListResponse;
import org.alfresco.util.json.jackson.AlfrescoDefaultObjectMapper;

public class SiteContainer implements Serializable, ExpectedComparison, Comparable<SiteContainer>
{
	private static final long serialVersionUID = 535206187221924534L;

	private String siteId;
	private String id;
	private String folderId;

	public SiteContainer(String siteId, String folderId, String id)
	{
		super();
		this.siteId = siteId;
		this.folderId = folderId;
		this.id = id;
	}
	
	public String getSiteId()
	{
		return siteId;
	}

	public String getFolderId()
	{
		return folderId;
	}

	public String getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return "SiteContainer [siteId=" + siteId + ", folderId=" + folderId
				+ ", id=" + id + "]";
	}

	public static SiteContainer parseSiteContainer(String siteId, JsonNode json)
	{
		SiteContainer siteContainer = null;

		if(json != null)
		{
			siteContainer = new SiteContainer(siteId, json.get("folderId").textValue(), json.get("id").textValue());
		}

		return siteContainer;
	}

	public static ListResponse<SiteContainer> parseSiteContainers(JsonNode jsonObject)
	{
		List<SiteContainer> siteContainers = new ArrayList<SiteContainer>();

		JsonNode jsonList = jsonObject.get("list");
		assertNotNull(jsonList);

		ArrayNode jsonEntries = (ArrayNode)jsonList.get("entries");
		assertNotNull(jsonEntries);

		for(int i = 0; i < jsonEntries.size(); i++)
		{
			JsonNode jsonEntry = jsonEntries.get(i);
			JsonNode entry = jsonEntry.get("entry");
			siteContainers.add(SiteContainer.parseSiteContainer(null, entry));
		}

		ExpectedPaging paging = ExpectedPaging.parsePagination(jsonList);

		ListResponse<SiteContainer> resp = new ListResponse<SiteContainer>(paging, siteContainers);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public ObjectNode toJSON()
	{
		ObjectNode siteContainerJson = AlfrescoDefaultObjectMapper.createObjectNode();
		siteContainerJson.put("id", id);
		siteContainerJson.put("folderId", folderId);
		return siteContainerJson;
	}

	@Override
	public void expected(Object o)
	{
		assertTrue(o instanceof SiteContainer);
		
		SiteContainer other = (SiteContainer)o;

		AssertUtil.assertEquals("id", id, other.getId());
		AssertUtil.assertEquals("folderId", folderId, other.getFolderId());
	}

	@Override
	public int compareTo(SiteContainer o)
	{
		return folderId.compareTo(o.getFolderId());
	}
	
}
