/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.iteco.employeegxt.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.iteco.employeegxt.client.DataBaseService;
import ru.iteco.employeegxt.client.EmployeeBaseModelData;
import ru.iteco.employeegxt.client.EmployeeDTO;
import service.EmployeeServiceInteface;


import Hibernate.Employee;
import Hibernate.EmployeeDaoInterface;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataBaseServiceImpl extends RemoteServiceServlet implements DataBaseService {

	private static final long serialVersionUID = 1L;

	private static ApplicationContext context;

	private static EmployeeDaoInterface employeeDao;
	private static EmployeeServiceInteface employeeService;

	public static ApplicationContext getContext() {
		if ( context == null ) {
			context = new ClassPathXmlApplicationContext(
					new String[] { "HibernateDaoBeans.xml" }); 
		}
		return context;
	}
	
	static {
		employeeDao = (EmployeeDaoInterface) getContext().getBean("employeeDao");
		employeeService = (EmployeeServiceInteface) getContext().getBean("employeeService");	
	}

	public List<EmployeeDTO> getAllEmployees() {		
		EmployeeDTO employeeDTO = null;
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

		List<Employee> allEmployees = employeeService.getAllEmployees();

		Mapper mapper = new DozerBeanMapper();
		Iterator<Employee> iterator = allEmployees.iterator();
		while( iterator.hasNext() ) {
			employeeDTO = mapper.map(iterator.next(), EmployeeDTO.class);
			employeeDTOs.add( employeeDTO );
		}

		return employeeDTOs;
	}

	@Override
	public Integer saveAllEmployees(List<EmployeeDTO> employees) {
		Employee employee;
		Iterator<EmployeeDTO> iterator = employees.iterator();
		Mapper mapper = new DozerBeanMapper();
		while( iterator.hasNext() ) {
			employee = mapper.map(iterator.next(), Employee.class );
			try {
			if ( employeeDao.getEmployee( employee.getEMPNO() ) == null ) {
				Log.debug("BEGIN INSERT");
				employeeDao.insertEmployee(employee);
				Log.debug("END INSERT");
			}
			} catch (Exception e) {
				System.out.println("ERROR");
			}
			
			employeeDao.updateEmployee(employee);
		}
		return employees.size();
	}
}
