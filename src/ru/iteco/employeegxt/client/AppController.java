package ru.iteco.employeegxt.client;

import ru.iteco.employeegxt.client.event.AddEmployeeEvent;
import ru.iteco.employeegxt.client.event.AddEmployeeEventHandler;
import ru.iteco.employeegxt.client.event.UpdateEmployeesEvent;
import ru.iteco.employeegxt.client.event.UpdateEmployeesEventHandler;
import ru.iteco.employeegxt.client.presenter.EmployeesPresenter;
import ru.iteco.employeegxt.client.presenter.Presenter;
import ru.iteco.employeegxt.client.view.EmployeesView;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements ValueChangeHandler<String> {

	private final HandlerManager eventBus;
	private final DataBaseServiceAsync rpcService;
	private HasWidgets container;

	public AppController(DataBaseServiceAsync rpcService,
			HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(AddEmployeeEvent.TYPE,
				new AddEmployeeEventHandler() {
					public void onAddEmployee(AddEmployeeEvent event) {
						doAddNewEmployee();
					}
				});

		eventBus.addHandler(UpdateEmployeesEvent.TYPE,
				new UpdateEmployeesEventHandler() {

					@Override
					public void onEmployeesUpdated(UpdateEmployeesEvent event) {
						doEmployeeUpdated();
					}
				});
		
	}

	private void doAddNewEmployee() {
		History.newItem("add");
	}

	private void doEmployeeUpdated() {
		History.newItem("list");
	}
	
	public void go(final HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("list");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			//if (token.equals("list")) {
				//presenter = new EmployeesPresenter(rpcService, eventBus,
				//		new EmployeesView());
			//} else if (token.equals("add")) {
				// presenter = new EditEmployeePresenter(rpcService, eventBus,
				// new EditEmployeeView());
			//}

			presenter = new EmployeesPresenter(rpcService, eventBus,
					new EmployeesView());
			
			if (presenter != null) {
				presenter.go(container);
			}
		}
	}
}
