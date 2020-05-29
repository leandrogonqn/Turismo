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
package domainapp.application.services.homepage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.modules.simple.dom.provincia.Provincia;
import domainapp.modules.simple.dom.provincia.ProvinciaMenu;
import domainapp.modules.simple.dom.reserva.FechasDisponibles;
import domainapp.modules.simple.dom.reserva.ReservaRepository;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "domainapp.application.services.homepage.HomePageViewModel"
)
public class HomePageViewModel {
	
	public String tittle() {
		return "Mutual del Personal de la Policia del Neuquen";
	}
	public TranslatableString title() {
		return TranslatableString.tr("Mutual del Personal de la Policia del Neuquen");
	}
	
	public List<FechasDisponibles> getDisponibilidad(){
		Date fechaDeHoy = new Date();
		Calendar fechaSumada = Calendar.getInstance();
		fechaSumada.add(Calendar.MONTH, 5);
		Date fechaDe5Meses = fechaSumada.getTime();
		return reservaRepository.listarDisponibilidad(fechaDeHoy, fechaDe5Meses);
	}

    @Inject
    ReservaRepository reservaRepository;
}
