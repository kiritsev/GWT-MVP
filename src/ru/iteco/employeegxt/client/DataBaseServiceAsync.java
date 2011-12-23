package ru.iteco.employeegxt.client;

import java.util.List;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataBaseServiceAsync {
	void getAllEmployees( AsyncCallback<List<EmployeeDTO>> callback );
	void saveAllEmployees( List<EmployeeDTO> employees, AsyncCallback<Integer> callback );
}
