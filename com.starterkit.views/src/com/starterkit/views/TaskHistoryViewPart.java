package com.starterkit.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.jface.action.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

import com.starterkit.views.dataprovider.impl.DataProviderImpl;
import com.starterkit.views.filter.TaskFilter;
import com.starterkit.views.models.Task;
import com.starterkit.views.TaskHistoryViewPart;

public class TaskHistoryViewPart extends ViewPart {
	private Text textTaskName;
	private Table tasksTable;
	private TableViewer tableViewer;

	private DataProviderImpl dataProvider = DataProviderImpl.getInstance();

	private TaskFilter taskFilter = new TaskFilter();

	private DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

	private IObservableList input;

	private Action deleteTaskAction;

	public TaskHistoryViewPart() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Label lblTaskName = new Label(parent, SWT.NONE);
		lblTaskName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblTaskName.setText("Task name:");

		textTaskName = new Text(parent, SWT.BORDER);
		textTaskName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		new Label(parent, SWT.NONE);

		textTaskName.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				taskFilter.setSearchString(textTaskName.getText());
				tableViewer.refresh();
			}

			@Override
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			}
		});

		createTasksTable(parent);

		tableViewer.addFilter(taskFilter);

		// Create the help context id for the viewer's control
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(tableViewer.getControl(),
						"com.starterkit.views.tableViewer");
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}

	private void createTasksTable(Composite parent) {

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tasksTable = tableViewer.getTable();
		
		/*
		 * Create table columns.
		 */
		createViewerColumn(tableViewer, "Id", 30);
		createViewerColumn(tableViewer, "Description", 150);
		createViewerColumn(tableViewer, "Status", 60);
		TableViewerColumn dueDateColumn = createViewerColumn(tableViewer, "Due date", 80);

		tableViewer.setContentProvider(new ObservableListContentProvider());
		input = DataProviderImpl.getInstance().getWritable();
		ViewerSupport.bind(
				tableViewer,
				input,
				BeanProperties.values(new String[] { "id", "description",
						"status", "dueDate" }));
		
		/*
		 * Set date formatting in due date column.
		 */
		dueDateColumn.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				Task t = (Task) element;
				return dateFormatter.format(t.getDueDate());
			}
		});

		tasksTable.setHeaderVisible(true);
		tasksTable.setLinesVisible(true);

		tasksTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(tasksTable);
		tasksTable.setMenu(menu);
		getSite().registerContextMenu(menuManager, tableViewer);
		getSite().setSelectionProvider(tableViewer);
	}

	private TableViewerColumn createViewerColumn(TableViewer viewer,
			String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);

		viewerColumn.getColumn().setText(title);
		viewerColumn.getColumn().setWidth(bound);
		viewerColumn.getColumn().setResizable(true);
		viewerColumn.getColumn().setMoveable(true);

		return viewerColumn;
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TaskHistoryViewPart.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tableViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(deleteTaskAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(deleteTaskAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(deleteTaskAction);
	}

	private void makeActions() {
		deleteTaskAction = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) tableViewer
						.getSelection();
				if (selection == null || selection.isEmpty()) {
					return;
				}

				Task task = (Task) selection.getFirstElement();

				boolean isDeleteDecisionConfirmed = MessageDialog.openQuestion(
						getSite().getShell(),
						"Delete task",
						"Are you sure you want to delete task \""
								+ task.getDescription() + "\"?");
				
				if(isDeleteDecisionConfirmed) {
					dataProvider.removeTask(task.getId());
				}
			}
		};
		deleteTaskAction.setText("Delete");
		deleteTaskAction.setToolTipText("Delete task tooltip");
	}

	@Override
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}
}
