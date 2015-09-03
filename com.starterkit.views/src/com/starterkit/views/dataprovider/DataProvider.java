package com.starterkit.views.dataprovider;

import java.util.Collection;

import com.starterkit.views.models.Task;

public interface DataProvider {
	Collection<Task> findTasks(String description);
	void closeTask(Long id);
}
