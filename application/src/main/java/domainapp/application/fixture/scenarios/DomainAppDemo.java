/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.application.fixture.scenarios;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifest2;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.metamodel.MetaModelService4;

//import domainapp.modules.simple.fixture.SimpleObject_persona;

public class DomainAppDemo extends FixtureScript {

//    public DomainAppDemo() {
//        withDiscoverability(Discoverability.DISCOVERABLE);
//    }

    @Override
    protected void execute(final ExecutionContext ec) {
//        AppManifest2 appManifest2 = metaModelService4.getAppManifest2();
//        ec.executeChild(this, appManifest2.getTeardownFixture());
//        ec.executeChild(this, appManifest2.getRefDataSetupFixture());
//        zec.executeChild(this, new SimpleObject_persona.PersistAll());
    }

//    @Inject
//    MetaModelService4 metaModelService4;
}
