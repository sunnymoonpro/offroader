package com.offroader.core;

public class ORException extends Exception {
	private static final long serialVersionUID = 1L;

	public ORException(String errDesc) {
		super(errDesc);
	}

	public ORException(Throwable e) {
		super(e);
	}

}
