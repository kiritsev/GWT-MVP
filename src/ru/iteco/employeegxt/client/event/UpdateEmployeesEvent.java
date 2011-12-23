package ru.iteco.employeegxt.client.event;

import java.util.List;

import ru.iteco.employeegxt.client.EmployeeDTO;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateEmployeesEvent extends GwtEvent<UpdateEmployeesEventHandler> {
	public static Type<UpdateEmployeesEventHandler> TYPE = new Type<UpdateEmployeesEventHandler>();
	private final List<EmployeeDTO> updatedEmployee;

	public UpdateEmployeesEvent(List<EmployeeDTO> updatedEmployees) {
		this.updatedEmployee = updatedEmployees;
	}

	public List<EmployeeDTO> getUpdatedEmployees() {
		return updatedEmployee;
	}

	@Override
	public Type<UpdateEmployeesEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateEmployeesEventHandler handler) {
		handler.onEmployeesUpdated(this);
	}
}
