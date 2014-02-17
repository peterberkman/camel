/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.mock;

import org.apache.camel.CamelContext;
import org.apache.camel.ComponentConfiguration;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.EndpointConfiguration;
import org.apache.camel.component.bean.BeanComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

public class MockComponentConfigurationAndDocumentation extends ContextTestSupport {

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    public void testComponentConfiguration() throws Exception {
        MockComponent comp = context.getComponent("mock", MockComponent.class);
        EndpointConfiguration conf = comp.createConfiguration("mock:foo?retainFirst=10");

        assertEquals("10", conf.getParameter("retainFirst"));

        ComponentConfiguration compConf = comp.createComponentConfiguration();
        String json = compConf.createParameterJsonSchema();
        assertNotNull(json);

        assertTrue(json.contains("\"expectedCount\": { \"type\": \"int\" }"));
        assertTrue(json.contains("\"retainFirst\": { \"type\": \"int\" }"));
    }

    @Test
    public void testComponentDocumentation() throws Exception {
        // cannot be tested on java 1.6
        if (isJavaVersion("1.6")) {
            return;
        }

        CamelContext context = new DefaultCamelContext();
        String html = context.getComponentDocumentation("mock");
        assertNotNull("Should have found some auto-generated HTML if on Java 7", html);
    }

}