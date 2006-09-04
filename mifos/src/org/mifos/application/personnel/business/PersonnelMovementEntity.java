package org.mifos.application.personnel.business;

import java.util.Date;

import org.mifos.application.customer.business.CustomerBO;
import org.mifos.application.office.business.OfficeBO;
import org.mifos.application.util.helpers.Status;
import org.mifos.framework.business.PersistentObject;

public class PersonnelMovementEntity extends PersistentObject {

	private final Short personnelMovementId;

	private final PersonnelBO personnel;

	private OfficeBO office;

	private final Date startDate;

	private Date endDate;
	
	private Short status;

	
	/*
	 * Adding a default constructor is hibernate's requirement and should not be
	 * used to create a valid Object.
	 */
	protected PersonnelMovementEntity(){
		this.personnelMovementId = null;
		this.personnel = null;
		this.office = null;
		this.startDate = null;
	}
	
	public PersonnelMovementEntity(PersonnelBO personnel, Date startDate){
		this.personnel = personnel;
		this.office = personnel.getOffice();
		this.startDate = startDate;
		this.status = Status.ACTIVE.getValue();
		this.personnelMovementId = null;
	}
	
	public void updateStatus(Status status){
		this.status = status.getValue();
	}

	public boolean isActive(){
		return status.equals(Status.ACTIVE.getValue());
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Short getPersonnelMovementId() {
		return personnelMovementId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void makeInActive(Short updatedBy){
		updateStatus(Status.INACTIVE);
		setUpdatedBy(updatedBy);
		setUpdatedDate(new Date());
		setEndDate(new Date());
	}
}
