package ru.iteco.employeegxt.client.presenter;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ru.iteco.employeegxt.client.DataBaseServiceAsync;
import ru.iteco.employeegxt.client.EmployeeBaseModelData;
import ru.iteco.employeegxt.client.EmployeeDTO;
import ru.iteco.employeegxt.client.event.AddEmployeeEvent;
import ru.iteco.employeegxt.client.event.UpdateEmployeesEvent;

public class EmployeesPresenter implements Presenter {

	public interface Display {
		// HasClickHandlers getAddButton();
		// HasClickHandlers getSaveButton();
		Button getAddButton();

		Button getSaveButton();

		Button getResetButton();

		void setData(ListStore<EmployeeBaseModelData> data);

		Widget asWidget();

		void addNewRecord(EmployeeBaseModelData employee);

		ListStore<EmployeeBaseModelData> getStore();
	}

	private final DataBaseServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public EmployeesPresenter(DataBaseServiceAsync rpcService,
			HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {

		display.getAddButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						
						EmployeeBaseModelData employee = new EmployeeBaseModelData();

						employee.setEMPNO(Long.valueOf(1));
						employee.setENAME("Employee");
						employee.setJOB("");
						employee.setMGR(Long.valueOf(0));
						employee.setHIREDATE(DateTimeFormat
								.getFormat("MM/dd/y").format(
										new DateWrapper().clearTime().asDate()));
						employee.setSAL(Long.valueOf(0));
						employee.setCOMM(Long.valueOf(0));
						employee.setDEPTNO(Long.valueOf(0));

						display.addNewRecord(employee);
						
						eventBus.fireEvent(new AddEmployeeEvent());
					}

				});

		display.getSaveButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						// List<EmployeeBaseModelData> modifiedRecords =
						// store.getModifiedRecords();
						List<Record> modifiedRecords = display.getStore()
								.getModifiedRecords();

						final List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();

						Iterator<Record> iterator = modifiedRecords.iterator();

						while (iterator.hasNext()) {
							employeeDTOs.add(createEmployeeDTO(iterator.next()));
						}

						rpcService.saveAllEmployees(employeeDTOs,
								new AsyncCallback<Integer>() {
									public void onFailure(Throwable caught) {
										MessageBox.info("Message", "ERROR!!!"
												+ caught, null);
									}

									public void onSuccess(
											Integer processedRecordsCount) {
										eventBus.fireEvent(new UpdateEmployeesEvent(employeeDTOs));
										MessageBox.info("Message",
												"Data was saved successfully. Processed "
														+ processedRecordsCount
														+ " records.", null);
									}
								});
						display.getStore().commitChanges();
					}
				});

		display.getResetButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						display.getStore().rejectChanges();
					}
				});

	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		fetchEmployees();
	}

	private EmployeeDTO createEmployeeDTO(Record employeeBaseModelData) {
		EmployeeDTO employeeDTO = new EmployeeDTO();

		employeeDTO.setCOMM((Long) employeeBaseModelData.get("comm"));
		employeeDTO.setDEPTNO((Long) employeeBaseModelData.get("deptno"));
		employeeDTO.setEMPNO((Long) employeeBaseModelData.get("empno"));
		employeeDTO.setENAME((String) employeeBaseModelData.get("ename"));
		employeeDTO.setHIREDATE((Date) employeeBaseModelData.get("hiredate"));
		employeeDTO.setJOB((String) employeeBaseModelData.get("job"));
		employeeDTO.setMGR((Long) employeeBaseModelData.get("mgr"));
		employeeDTO.setSAL((Long) employeeBaseModelData.get("sal"));

		return employeeDTO;
	}

	private EmployeeBaseModelData createEmployeeBaseModelData(
			EmployeeDTO employeeDTO) {
		EmployeeBaseModelData employeeBaseModelData = new EmployeeBaseModelData();

		employeeBaseModelData.setCOMM(employeeDTO.getCOMM());
		employeeBaseModelData.setDEPTNO(employeeDTO.getDEPTNO());
		employeeBaseModelData.setEMPNO(employeeDTO.getEMPNO());
		employeeBaseModelData.setENAME(employeeDTO.getENAME());
		if (employeeDTO.getHIREDATE() != null) {
			employeeBaseModelData.setHIREDATE(DateTimeFormat.getFormat(
					"MM/dd/y").format(employeeDTO.getHIREDATE()));
		}
		employeeBaseModelData.setJOB(employeeDTO.getJOB());
		employeeBaseModelData.setMGR(employeeDTO.getMGR());
		employeeBaseModelData.setSAL(employeeDTO.getSAL());

		return employeeBaseModelData;
	}

	private void fetchEmployees() {
		rpcService.getAllEmployees(new AsyncCallback<List<EmployeeDTO>>() {

			public void onFailure(Throwable caught) {
				MessageBox.info("Error", "Fetching employee details!" + caught,
						null);
			}

			public void onSuccess(List<EmployeeDTO> result) {
				ListStore<EmployeeBaseModelData> data = new ListStore<EmployeeBaseModelData>();

				if (result != null) {
					Iterator<EmployeeDTO> employeeDTO = result.iterator();

					while (employeeDTO.hasNext()) {
						data.add(createEmployeeBaseModelData(employeeDTO.next()));
					}
					display.setData(data);
				}
			}
		});
	}

}
