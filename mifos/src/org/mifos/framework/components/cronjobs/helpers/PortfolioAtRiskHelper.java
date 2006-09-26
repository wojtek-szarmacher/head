package org.mifos.framework.components.cronjobs.helpers;

import java.util.List;

import org.mifos.application.customer.group.business.GroupBO;
import org.mifos.application.customer.persistence.CustomerPersistence;
import org.mifos.application.customer.util.helpers.CustomerConstants;
import org.mifos.framework.components.cronjobs.TaskHelper;
import org.mifos.framework.hibernate.helper.HibernateUtil;

public class PortfolioAtRiskHelper extends TaskHelper {
	
	@Override
	public void execute(long timeInMillis) {
		try{
			CustomerPersistence customerPersistence= new CustomerPersistence();
			List<Integer> customerIds = customerPersistence.getCustomers(CustomerConstants.GROUP_LEVEL_ID);
			if(customerIds!=null && !customerIds.isEmpty())
				for(Integer customerId :  customerIds){
					try{
						GroupBO group = (GroupBO)new CustomerPersistence().getCustomer(customerId);
						group.getPerformanceHistory().generatePortfolioAtRisk();
						group.update();
						HibernateUtil.commitTransaction();
					}			
					catch(Exception e){
						HibernateUtil.rollbackTransaction();
						e.printStackTrace();
					}finally{
						HibernateUtil.closeSession();
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	@Override
	public boolean isTaskAllowedToRun() {
		return true;
	}

}
