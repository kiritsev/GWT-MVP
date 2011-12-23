package ru.iteco.employeegxt.client;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EmployeeGxt  extends LayoutContainer implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
	    DataBaseServiceAsync rpcService = GWT.create(DataBaseService.class);
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(rpcService, eventBus);
	    appViewer.go(RootPanel.get());
	}
}
