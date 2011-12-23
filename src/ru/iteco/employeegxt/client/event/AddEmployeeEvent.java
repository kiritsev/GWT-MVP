package ru.iteco.employeegxt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class AddEmployeeEvent extends GwtEvent<AddEmployeeEventHandler> {
	public static Type<AddEmployeeEventHandler> TYPE = new Type<AddEmployeeEventHandler>();

	@Override
	public Type<AddEmployeeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddEmployeeEventHandler handler) {
		handler.onAddEmployee(this);
	}
}