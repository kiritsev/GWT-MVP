package ru.iteco.employeegxt.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UpdateEmployeesEventHandler extends EventHandler{
  void onEmployeesUpdated(UpdateEmployeesEvent event);
}
