package com.starterkit.views;

import javax.swing.table.TableColumn;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

import com.starterkit.views.dataprovider.DataProvider;
import com.starterkit.views.dataprovider.impl.DataProviderImpl;
import com.starterkit.views.models.Task;

public class OpenedTasksViewPart extends ViewPart {
	private Text textTaskName;
	private Table tasksTable;
	
	private DataProvider dataProvider = new DataProviderImpl();

	public OpenedTasksViewPart() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		Label lblTaskName = new Label(parent, SWT.NONE);
		lblTaskName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTaskName.setText("Task name:");
		
		textTaskName = new Text(parent, SWT.BORDER);
		textTaskName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		
		Button btnSearch = new Button(parent, SWT.NONE);
		btnSearch.setText("Search");
		new Label(parent, SWT.NONE);
		
		createTasksTable(parent);
	}
	
	private void createTasksTable(Composite parent) {

		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tasksTable = tableViewer.getTable();
		createColumns(parent, tableViewer);
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(dataProvider.findTasks(""));
		getSite().setSelectionProvider(tableViewer);
		
		tasksTable.setHeaderVisible(true);
		tasksTable.setLinesVisible(true);
		
		tasksTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
		
		/*
		 * Create id column.
		 */
		TableViewerColumn idColumn = createViewerColumn(viewer, "Id", 30);
		idColumn.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getId().toString();
			}
		});

		/*
		 * Create description column.
		 */
		TableViewerColumn descriptionColumn = createViewerColumn(viewer, "Description", 150);
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getDescription();
			}
		});

		/*
		 * Create status column.
		 */
		TableViewerColumn statusColumn = createViewerColumn(viewer, "Status", 70);
		statusColumn.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return t.getStatus().toString();
			}
		});

		/*
		 * Create due date column.
		 */
		TableViewerColumn dueDateColumn = createViewerColumn(viewer, "Due date", 70);
		dueDateColumn.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
//				Task t = (Task) element;
				return "Data";
			}
		});
	}
	
	private TableViewerColumn createViewerColumn(TableViewer viewer, String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		
		viewerColumn.getColumn().setText(title);
		viewerColumn.getColumn().setWidth(bound);
		viewerColumn.getColumn().setResizable(true);
		viewerColumn.getColumn().setMoveable(true);
		
		return viewerColumn;
	}

	@Override
	public void setFocus() {

	}

}
