package ru.iteco.employeegxt.client.view;

import java.util.ArrayList;
import java.util.List;


import ru.iteco.employeegxt.client.EmployeeBaseModelData;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.fx.Resizable;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Widget;

import ru.iteco.employeegxt.client.presenter.EmployeesPresenter;

public class EmployeesView extends Composite implements
		EmployeesPresenter.Display {
	private final Button addButton;
	private final Button saveButton;
	private final Button resetButton;

	private static ListStore<EmployeeBaseModelData> store;
	private RowEditor<EmployeeBaseModelData> re;

	public EmployeesView() {

		DecoratorPanel contentTableDecorator = new DecoratorPanel();
		initWidget(contentTableDecorator);
		contentTableDecorator.setWidth("100%");
		contentTableDecorator.setWidth("18em");

		// Config init
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		ColumnConfig column;

		// Column 01
		column = new ColumnConfig();
		column.setId("empno");
		column.setHeader("EmpNo");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(70);

		NumberField numberFieldEmpNo = new NumberField();
		numberFieldEmpNo.getPropertyEditor().setFormat(
				NumberFormat.getDecimalFormat());
		numberFieldEmpNo.setPropertyEditorType(Long.class);

		column.setNumberFormat(NumberFormat.getDecimalFormat());
		column.setEditor(new CellEditor(numberFieldEmpNo));

		configs.add(column);

		// Column 02
		column = new ColumnConfig();
		column.setId("ename");
		column.setHeader("EName");
		column.setWidth(70);

		TextField<String> textEName = new TextField<String>();
		textEName.setAllowBlank(false);
		column.setEditor(new CellEditor(textEName));

		configs.add(column);

		// Column 03
		column = new ColumnConfig();
		column.setId("mgr");
		column.setHeader("Mgr");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(70);

		NumberField numberFieldMgr = new NumberField();
		numberFieldMgr.getPropertyEditor().setFormat(
				NumberFormat.getDecimalFormat());
		numberFieldMgr.setPropertyEditorType(Long.class);

		column.setNumberFormat(NumberFormat.getDecimalFormat());
		column.setEditor(new CellEditor(numberFieldMgr));

		configs.add(column);

		// Column 04
		DateField dateField = new DateField();
		dateField.getPropertyEditor().setFormat(
				DateTimeFormat.getFormat("dd-MM-y"));

		column = new ColumnConfig();
		column.setId("hiredate");
		column.setHeader("Hiredate");
		column.setWidth(95);

		column.setDateTimeFormat(DateTimeFormat.getFormat("dd MMM yyyy"));
		column.setEditor(new CellEditor(dateField));
		configs.add(column);

		// Column 05
		column = new ColumnConfig();
		column.setId("sal");
		column.setHeader("Sal");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(70);

		NumberField numberFieldSal = new NumberField();
		numberFieldSal.getPropertyEditor().setFormat(
				NumberFormat.getDecimalFormat());
		numberFieldSal.setPropertyEditorType(Long.class);

		column.setNumberFormat(NumberFormat.getDecimalFormat());
		column.setEditor(new CellEditor(numberFieldSal));

		configs.add(column);

		// Column 06
		column = new ColumnConfig();
		column.setId("comm");
		column.setHeader("Comm");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(70);

		NumberField numberFieldComm = new NumberField();
		numberFieldComm.getPropertyEditor().setFormat(
				NumberFormat.getDecimalFormat());
		numberFieldComm.setPropertyEditorType(Long.class);

		column.setNumberFormat(NumberFormat.getDecimalFormat());
		column.setEditor(new CellEditor(numberFieldComm));

		configs.add(column);

		// Column 07
		column = new ColumnConfig();
		column.setId("deptno");
		column.setHeader("DeptNo");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(70);

		NumberField numberFieldDeptNo = new NumberField();
		numberFieldDeptNo.getPropertyEditor().setFormat(
				NumberFormat.getDecimalFormat());
		numberFieldDeptNo.setPropertyEditorType(Long.class);

		column.setNumberFormat(NumberFormat.getDecimalFormat());
		column.setEditor(new CellEditor(numberFieldDeptNo));

		configs.add(column);

		store = new ListStore<EmployeeBaseModelData>();

		// Window
		ColumnModel cm = new ColumnModel(configs);

		ContentPanel cp = new ContentPanel();
		new Resizable(cp);
		cp.setHeading("Edit Employees with RowEditor");
		cp.setFrame(true);
		cp.setSize(600, 400);
		cp.setLayout(new FitLayout());

		re = new RowEditor<EmployeeBaseModelData>();
		final Grid<EmployeeBaseModelData> grid = new Grid<EmployeeBaseModelData>(
				store, cm);
		grid.setAutoExpandColumn("ename");
		grid.setBorders(true);
		grid.addPlugin(re);
		grid.getAriaSupport().setLabelledBy(cp.getHeader().getId() + "-label");
		cp.add(grid);

		// top toolbar
		ToolBar toolBar = new ToolBar();
		cp.setTopComponent(toolBar);
		cp.setButtonAlign(HorizontalAlignment.CENTER);
		
		addButton = new Button("Add Employee");
		toolBar.add(addButton);
		
		resetButton = new Button("Reset");
		cp.addButton(resetButton);

		saveButton = new Button("Save");
		cp.addButton(saveButton);

		contentTableDecorator.add(cp);
	}
	
	public Button getAddButton() {
		return addButton;
	}

	public Button getSaveButton() {
		return saveButton;
	}
	
	public Button getResetButton() {
		return resetButton;
	}

	public void setData(ListStore<EmployeeBaseModelData> data) {
		for( int i=0; i < data.getCount(); i++ ) {
			store.add( data.getAt(i) ); 
		}
	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public void addNewRecord(EmployeeBaseModelData employee) {
		 re.stopEditing(false);
		 store.insert(employee, 0);
		 re.startEditing(store.indexOf(employee), true);
	}

	@Override
	public ListStore<EmployeeBaseModelData> getStore() {
		return store;
	}

}
