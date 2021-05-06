package com.revature.trms.beans;

public enum EventType {
	UNIVERSITY_COURSE(0.8), 
	SEMINAR(0.6), 
	CERTIFICATION_PREPARATION_CLASS(0.75), 
	CERTIFICATION(1.0), 
	TECHNICAL_TRAINING(0.9), 
	OTHER(0.3);
	
	public final double reimbursementPercentage;
	
	private EventType(double reimbursementPercentage) {
		this.reimbursementPercentage = reimbursementPercentage;
	}
}
