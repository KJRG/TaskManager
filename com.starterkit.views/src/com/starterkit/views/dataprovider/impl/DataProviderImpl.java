package com.starterkit.views.dataprovider.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.starterkit.views.dataprovider.DataProvider;
import com.starterkit.views.models.Task;
import com.starterkit.views.models.TaskStatus;

public class DataProviderImpl implements DataProvider {
	private List<Task> tasks = new ArrayList<>();
	
	public DataProviderImpl() {
		/*
		 * Initialize the list with sample tasks.
		 */
		
		tasks.add(new Task(1L, "Task 1", TaskStatus.OPENED, null));
		tasks.add(new Task(2L, "Task 2", TaskStatus.OPENED, null));
		tasks.add(new Task(3L, "Task 3", TaskStatus.OPENED, null));
		tasks.add(new Task(4L, "Task 4", TaskStatus.OPENED, null));
		tasks.add(new Task(5L, "Task 5", TaskStatus.OPENED, null));
	}

	@Override
	public Collection<Task> findTasks(String description) {
		List<Task> result = new ArrayList<>();
		
		for(Task t : tasks) {
			if(t.getDescription().toLowerCase().startsWith(description.toLowerCase())) {
				result.add(t);
			}
		}
		
		return result;
	}
}