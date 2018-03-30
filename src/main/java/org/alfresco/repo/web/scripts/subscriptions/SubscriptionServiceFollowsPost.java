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
package org.alfresco.repo.web.scripts.subscriptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

import org.alfresco.util.json.jackson.AlfrescoDefaultObjectMapper;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

public class SubscriptionServiceFollowsPost extends AbstractSubscriptionServiceWebScript
{
    @SuppressWarnings("unchecked")
    public JsonNode executeImpl(String userId, WebScriptRequest req, WebScriptResponse res) throws IOException
    {
        ArrayNode jsonUsers = (ArrayNode) AlfrescoDefaultObjectMapper.getReader().readTree(req.getContent().getContent());

        ArrayNode result = AlfrescoDefaultObjectMapper.createArrayNode();

        for (JsonNode o : jsonUsers)
        {
            String user = (o == null ? null : o.textValue());
            if (user != null)
            {
                ObjectNode item = AlfrescoDefaultObjectMapper.createObjectNode();
                item.put(user, subscriptionService.follows(userId, user));
                result.add(item);
            }
        }

        return result;
    }
}
