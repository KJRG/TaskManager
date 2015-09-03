package com.starterkit.views.models;

import org.eclipse.swt.widgets.DateTime;

public class Task extends ModelObject {
	private Long id;
	private String description;
	private TaskStatus status;
	private DateTime dueDate;

	public Task(Long id, String name, TaskStatus status, DateTime dueDate) {
		this.id = id;
		this.description = name;
		this.status = status;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		firePropertyChange("id", this.id, this.id = id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		firePropertyChange("description", this.description, this.description = description);
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		firePropertyChange("status", this.status, this.status = status);
	}

	public DateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateTime dueDate) {
		firePropertyChange("dueDate", this.dueDate, this.dueDate = dueDate);
	}
}