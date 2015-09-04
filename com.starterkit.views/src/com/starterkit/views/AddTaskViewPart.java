package com.starterkit.views;

import java.sql.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.starterkit.views.dataprovider.DataProvider;
import com.starterkit.views.dataprovider.impl.DataProviderImpl;
import com.starterkit.views.models.Task;
import com.starterkit.views.models.TaskStatus;

public class AddTaskViewPart extends ViewPart {
	private Text taskDescriptionText;

	private DataProvider dataProvider = DataProviderImpl.getInstance();

	public AddTaskViewPart() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Label lblDescription = new Label(parent, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDescription.setText("Description:");

		taskDescriptionText = new Text(parent, SWT.BORDER);
		taskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		Label lblDueDate = new Label(parent, SWT.NONE);
		lblDueDate.setText("Due date:");

		final DateTime dateTime = new DateTime(parent, SWT.BORDER);
		new Label(parent, SWT.NONE);

		Button btnAddTask = new Button(parent, SWT.NONE);
		btnAddTask.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (taskDescriptionText.getText() == null
						|| taskDescriptionText.getText().isEmpty()) {
					return;
				}

				Date dueDate = new Date(dateTime.getYear() - 1900,
						dateTime.getMonth(), dateTime.getDay());
				Task task = new Task(null, taskDescriptionText.getText(),
						TaskStatus.OPENED, dueDate);

				dataProvider.addTask(task);
			}
		});
		btnAddTask.setText("Add task");
	}

	@Override
	public void setFocus() {

	}

}
