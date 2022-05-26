/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.internal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.api.model.subscription.Application;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dao.SubscriptionValidationDAO;
import org.wso2.carbon.apimgt.internal.service.ApplicationsApiService;
import org.wso2.carbon.apimgt.internal.service.utils.SubscriptionValidationDataUtil;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import java.util.List;
import javax.ws.rs.core.Response;

public class ApplicationsApiServiceImpl implements ApplicationsApiService {

    @Override
    public Response applicationsGet(String xWSO2Tenant, Integer appId, MessageContext messageContext) {

        SubscriptionValidationDAO subscriptionValidationDAO = new SubscriptionValidationDAO();
        if (appId != null && appId > 0) {
            List<Application> application = subscriptionValidationDAO.getApplicationById(appId);
            return Response.ok().entity(SubscriptionValidationDataUtil.fromApplicationToApplicationListDTO(application)
            ).build();
        }
        xWSO2Tenant = SubscriptionValidationDataUtil.validateTenantDomain(xWSO2Tenant, messageContext);
        String organization = RestApiUtil.getOrganization(messageContext);
        if (StringUtils.isNotEmpty(organization) && !organization.equalsIgnoreCase(APIConstants.ORG_ALL_QUERY_PARAM)) {
            xWSO2Tenant = SubscriptionValidationDataUtil.validateTenantDomain(organization, messageContext);
        }
        if (organization.equalsIgnoreCase(APIConstants.ORG_ALL_QUERY_PARAM) &&
                xWSO2Tenant.equalsIgnoreCase(MultitenantConstants.SUPER_TENANT_DOMAIN_NAME)) {
            return Response.ok().entity(SubscriptionValidationDataUtil.fromApplicationToApplicationListDTO(
                    subscriptionValidationDAO.getAllApplications())).build();
        }
        if (StringUtils.isNotEmpty(xWSO2Tenant)) {
            return Response.ok().entity(SubscriptionValidationDataUtil.fromApplicationToApplicationListDTO(
                    subscriptionValidationDAO.getAllApplications(xWSO2Tenant)))
                    .build();
        }
        return Response.ok().entity(SubscriptionValidationDataUtil.fromApplicationToApplicationListDTO(
                subscriptionValidationDAO.getAllApplications())).build();
    }
}
