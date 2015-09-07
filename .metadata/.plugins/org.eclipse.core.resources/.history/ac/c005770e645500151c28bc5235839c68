package com.starterkit.views.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.starterkit.views.models.Task;

public class TaskFilter extends ViewerFilter {

	private String searchString;
	
	public TaskFilter() {
		searchString = "";
	}
	
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(searchString == null || searchString.isEmpty()) {
			return true;
		}
		
		Task task = (Task) element;
		if(task.getDescription().toLowerCase().startsWith(searchString.toLowerCase())) {
			return true;
		}
		
		return false;
	}
}
