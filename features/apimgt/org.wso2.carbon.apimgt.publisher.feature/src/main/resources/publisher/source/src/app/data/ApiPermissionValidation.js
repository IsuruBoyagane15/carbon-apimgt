/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
"use strict";

import React from 'react';

const permissionType = {
    READ : "READ",
    UPDATE : "UPDATE",
    DELETE : "DELETE",
    MANAGE_SUBSCRIPTION : "MANAGE_SUBSCRIPTION"
}

class ApiPermissionValidation extends React.Component {
    constructor(props){
        super(props);
        this.state = {};
    }

    render() {
        var checkingPermissionType = this.props.checkingPermissionType;
        var userPermissions = this.props.userPermissions;

        if(userPermissions.includes(checkingPermissionType)) {
            return (this.props.children);
        }
        return null;
    }
}

module.exports = {ApiPermissionValidation, permissionType};