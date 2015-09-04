package com.starterkit.views.dataprovider;

import java.util.Collection;

import com.starterkit.views.models.Task;

public interface DataProvider {
	Collection<Task> findAllTasks();
	Collection<Task> findOpenedTasks();
	Task addTask(Task task);
	void closeTask(Long id);
}
