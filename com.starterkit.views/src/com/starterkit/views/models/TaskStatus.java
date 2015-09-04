package com.starterkit.views.models;

public enum TaskStatus {
	OPEN("open"),
	CLOSED("closed");
	
	private final String stringValue;
	
	private TaskStatus(final String s) {
		stringValue = s;
	}
	
	public String toString() {
		return stringValue;
	}
}
