package com.starterkit.views.dataprovider.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

import com.starterkit.views.dataprovider.DataProvider;
import com.starterkit.views.models.Task;
import com.starterkit.views.models.TaskStatus;

public class DataProviderImpl extends Observable implements DataProvider {
	
	private static DataProviderImpl instance = new DataProviderImpl();
	
	public static DataProviderImpl getInstance() {
		return instance;
	}
	
	private List<Task> tasks = new ArrayList<>();
	
	private DataProviderImpl() {
		/*
		 * Initialize the list with sample tasks.
		 */
		
		tasks.add(new Task(1L, "Task 1", TaskStatus.OPENED, Date.valueOf("2015-09-07")));
		tasks.add(new Task(2L, "Task 2", TaskStatus.OPENED, Date.valueOf("2015-09-08")));
		tasks.add(new Task(3L, "Task 3", TaskStatus.OPENED, Date.valueOf("2015-09-18")));
		tasks.add(new Task(4L, "Task 4", TaskStatus.OPENED, Date.valueOf("2015-09-21")));
		tasks.add(new Task(5L, "Task 5", TaskStatus.OPENED, Date.valueOf("2015-11-08")));
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

	@Override
	public Collection<Task> findOpenedTasks() {
		List<Task> result = new ArrayList<>();
		
		for(Task t : tasks) {
			if(t.getStatus() == TaskStatus.OPENED) {
				result.add(t);
			}
		}
		
		return result;
	}

	@Override
	public Task addTask(Task task) {
		/*
		 * Find next task id.
		 */
		Long nextTaskId = 0L;
		for(Task t : tasks) {
			if(t.getId() > nextTaskId) {
				nextTaskId = t.getId();
			}
		}
		nextTaskId++;
		
		/*
		 * Set the id and add task.
		 */
		task.setId(nextTaskId);
		tasks.add(task);
		
		/*
		 * Notify observers.
		 */
		setChanged();
		notifyObservers();
		
		return task;
	}

	@Override
	public void closeTask(Long id) {
		for(Task t : tasks) {
			if(t.getId() == id) {
				t.setStatus(TaskStatus.CLOSED);
			}
		}
		
		/*
		 * Notify observers.
		 */
		setChanged();
		notifyObservers();
		
		/*
		 * For Java 8:
		 */
		// tasks.removeIf(t -> t.getId() == id);
	}


}
