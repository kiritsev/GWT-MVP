package ru.iteco.employeegxt.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AddEmployeeEventHandler extends EventHandler {
	void onAddEmployee(AddEmployeeEvent event);
}
