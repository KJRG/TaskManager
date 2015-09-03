package com.starterkit3.views;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;

import com.starterkit3.views.models.Person; 

public class ViewPart3 extends ViewPart {
	private Text text;
	private Text text_1;
	private Table table;

	public ViewPart3() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		Label lblFirstName = new Label(parent, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("First name:");
		
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		
		text_1 = new Text(parent, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_1.setEnabled(false);
		new Label(parent, SWT.NONE);
		
		Button btnClick = new Button(parent, SWT.NONE);
		btnClick.setText("Click");
		new Label(parent, SWT.NONE);
		
		Button btnHello = new Button(parent, SWT.NONE);
		btnHello.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnHello.setText("Hello");
		new Label(parent, SWT.NONE);
		
		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		btnClick.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String name = text.getText();
				text_1.setText(name);
				
				MessageDialog.openInformation(null, "Okno", "Hello");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		// TODO Auto-generated method stub
		
		// -------
		
		Person p = new Person();

		// create new context
		DataBindingContext ctx = new DataBindingContext();
		
		// define IObservables
		IObservableValue target = WidgetProperties.text(SWT.Modify).observe(text);

		IObservableValue model = BeanProperties.value(Person.class, "firstName").observe(p);
		
		// connect them
		ctx.bindValue(target, model);
		
        IObservableValue target2 = WidgetProperties.text(SWT.Modify).
                observe(text_1);
        ctx.bindValue(target2, model);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}