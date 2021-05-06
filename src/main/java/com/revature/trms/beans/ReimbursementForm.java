package com.revature.trms.beans;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class ReimbursementForm {
	private UUID id;
	private String name;
	private String username;
	private String dateSubmitted;
	private String dateOfEvent;
	private String time;
	private String location;
	private String description;
	private double cost;
	private double reimbursement;
	private Format format;
	private EventType type;
	private String justification;
	private Department department;
	private String supervisor;
	private String benCo;
	private Status supervisorApproval;
	private Status deptHeadApproval;
	private Status benCoApproval;
	private String reasonForDeclining;
	private boolean urgent;
	private boolean reimbursed;
	private List<String> attachments;
	
	private double projectedReimbursement;
	private String benCoReason;
	private boolean exceedsFundsAvailable;
	private int daysTillEvent;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDateSubmitted() {
		return dateSubmitted;
	}
	public void setDateSubmitted(String dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}
	public String getDateOfEvent() {
		return dateOfEvent;
	}
	public void setDateOfEvent(String dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getReimbursement() {
		return reimbursement;
	}
	public void setReimbursement(double reimbursement) {
		this.reimbursement = reimbursement;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	public String getBenCo() {
		return benCo;
	}
	public void setBenCo(String benCo) {
		this.benCo = benCo;
	}
	public Status getSupervisorApproval() {
		return supervisorApproval;
	}
	public void setSupervisorApproval(Status supervisorApproval) {
		this.supervisorApproval = supervisorApproval;
	}
	public Status getDeptHeadApproval() {
		return deptHeadApproval;
	}
	public void setDeptHeadApproval(Status deptHeadApproval) {
		this.deptHeadApproval = deptHeadApproval;
	}
	public Status getBenCoApproval() {
		return benCoApproval;
	}
	public void setBenCoApproval(Status benCoApproval) {
		this.benCoApproval = benCoApproval;
	}
	public String getReasonForDeclining() {
		return reasonForDeclining;
	}
	public void setReasonForDeclining(String reasonForDeclining) {
		this.reasonForDeclining = reasonForDeclining;
	}
	public boolean isUrgent() {
		return urgent;
	}
	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}
	public boolean isReimbursed() {
		return reimbursed;
	}
	public void setReimbursed(boolean reimbursed) {
		this.reimbursed = reimbursed;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public double getProjectedReimbursement() {
		return projectedReimbursement;
	}
	public void setProjectedReimbursement(double projectedReimbursement) {
		this.projectedReimbursement = projectedReimbursement;
	}
	public String getBenCoReason() {
		return benCoReason;
	}
	public void setBenCoReason(String benCoReason) {
		this.benCoReason = benCoReason;
	}
	public boolean isExceedsFundsAvailable() {
		return exceedsFundsAvailable;
	}
	public void setExceedsFundsAvailable(boolean exceedsFundsAvailable) {
		this.exceedsFundsAvailable = exceedsFundsAvailable;
	}
	public int getDaysTillEvent() {
		return daysTillEvent;
	}
	public void setDaysTillEvent(int daysTillEvent) {
		this.daysTillEvent = daysTillEvent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((benCo == null) ? 0 : benCo.hashCode());
		result = prime * result + ((benCoApproval == null) ? 0 : benCoApproval.hashCode());
		result = prime * result + ((benCoReason == null) ? 0 : benCoReason.hashCode());
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateOfEvent == null) ? 0 : dateOfEvent.hashCode());
		result = prime * result + ((dateSubmitted == null) ? 0 : dateSubmitted.hashCode());
		result = prime * result + daysTillEvent;
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((deptHeadApproval == null) ? 0 : deptHeadApproval.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (exceedsFundsAvailable ? 1231 : 1237);
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(projectedReimbursement);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((reasonForDeclining == null) ? 0 : reasonForDeclining.hashCode());
		result = prime * result + (reimbursed ? 1231 : 1237);
		temp = Double.doubleToLongBits(reimbursement);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((supervisor == null) ? 0 : supervisor.hashCode());
		result = prime * result + ((supervisorApproval == null) ? 0 : supervisorApproval.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (urgent ? 1231 : 1237);
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementForm other = (ReimbursementForm) obj;
		if (attachments == null) {
			if (other.attachments != null)
				return false;
		} else if (!attachments.equals(other.attachments))
			return false;
		if (benCo == null) {
			if (other.benCo != null)
				return false;
		} else if (!benCo.equals(other.benCo))
			return false;
		if (benCoApproval != other.benCoApproval)
			return false;
		if (benCoReason == null) {
			if (other.benCoReason != null)
				return false;
		} else if (!benCoReason.equals(other.benCoReason))
			return false;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (dateOfEvent == null) {
			if (other.dateOfEvent != null)
				return false;
		} else if (!dateOfEvent.equals(other.dateOfEvent))
			return false;
		if (dateSubmitted == null) {
			if (other.dateSubmitted != null)
				return false;
		} else if (!dateSubmitted.equals(other.dateSubmitted))
			return false;
		if (daysTillEvent != other.daysTillEvent)
			return false;
		if (department != other.department)
			return false;
		if (deptHeadApproval != other.deptHeadApproval)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (exceedsFundsAvailable != other.exceedsFundsAvailable)
			return false;
		if (format != other.format)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(projectedReimbursement) != Double.doubleToLongBits(other.projectedReimbursement))
			return false;
		if (reasonForDeclining == null) {
			if (other.reasonForDeclining != null)
				return false;
		} else if (!reasonForDeclining.equals(other.reasonForDeclining))
			return false;
		if (reimbursed != other.reimbursed)
			return false;
		if (Double.doubleToLongBits(reimbursement) != Double.doubleToLongBits(other.reimbursement))
			return false;
		if (supervisor == null) {
			if (other.supervisor != null)
				return false;
		} else if (!supervisor.equals(other.supervisor))
			return false;
		if (supervisorApproval != other.supervisorApproval)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (type != other.type)
			return false;
		if (urgent != other.urgent)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ReimbursementForm [id=" + id + ", name=" + name + ", username=" + username + ", dateSubmitted="
				+ dateSubmitted + ", dateOfEvent=" + dateOfEvent + ", time=" + time + ", location=" + location
				+ ", description=" + description + ", cost=" + cost + ", reimbursement=" + reimbursement + ", format="
				+ format + ", type=" + type + ", justification=" + justification + ", department=" + department
				+ ", supervisor=" + supervisor + ", benCo=" + benCo + ", supervisorApproval=" + supervisorApproval
				+ ", deptHeadApproval=" + deptHeadApproval + ", benCoApproval=" + benCoApproval
				+ ", reasonForDeclining=" + reasonForDeclining + ", urgent=" + urgent + ", reimbursed=" + reimbursed
				+ ", attachments=" + attachments + ", projectedReimbursement=" + projectedReimbursement
				+ ", benCoReason=" + benCoReason + ", exceedsFundsAvailable=" + exceedsFundsAvailable
				+ ", daysTillEvent=" + daysTillEvent + "]";
	}
	
	
}