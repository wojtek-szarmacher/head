package org.mifos.application.customer.group.business;

import java.util.List;

import org.mifos.application.customer.business.CustomerBO;
import org.mifos.application.customer.business.CustomerPerformanceHistory;
import org.mifos.application.customer.exceptions.CustomerException;
import org.mifos.application.customer.util.helpers.ChildrenStateType;
import org.mifos.application.customer.util.helpers.CustomerLevel;
import org.mifos.framework.util.helpers.Money;

public class GroupPerformanceHistoryEntity extends CustomerPerformanceHistory {

	private Integer id;

	private Integer clientCount;

	private Money lastGroupLoanAmount;

	private Money avgLoanForMember;

	private Money totalOutstandingPortfolio;

	private Money totalSavings;

	private Money portfolioAtRisk;

	private GroupBO group;

	public GroupPerformanceHistoryEntity(GroupBO group) {
		super();
		this.id = null;
		this.group = group;
		this.portfolioAtRisk = new Money();
		this.totalOutstandingPortfolio = new Money();
		this.totalSavings = new Money();
		this.avgLoanForMember = new Money();
		this.lastGroupLoanAmount = new Money();
		this.clientCount = 0;
	}

	public GroupPerformanceHistoryEntity(Integer clientCount,
			Money lastGroupLoanAmount, Money avgLoanForMember,
			Money totalOutstandingPortfolio, Money totalSavings,
			Money portfolioAtRisk) {
		this.portfolioAtRisk = portfolioAtRisk;
		this.totalOutstandingPortfolio = totalOutstandingPortfolio;
		this.totalSavings = totalSavings;
		this.avgLoanForMember = avgLoanForMember;
		this.lastGroupLoanAmount = lastGroupLoanAmount;
		this.clientCount = clientCount;
	}

	protected GroupPerformanceHistoryEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Money getAvgLoanForMember() {
		return avgLoanForMember;
	}

	private void setAvgLoanForMember(Money avgLoanForMember) {
		this.avgLoanForMember = avgLoanForMember;
	}

	public Integer getClientCount() {
		return clientCount;
	}

	public void setClientCount(Integer clientCount) {
		this.clientCount = clientCount;
	}

	public Money getLastGroupLoanAmount() {
		return lastGroupLoanAmount;
	}

	public void setLastGroupLoanAmount(Money lastGroupLoanAmount) {
		this.lastGroupLoanAmount = lastGroupLoanAmount;
	}

	private Money getTotalOutstandingPortfolio() {
		return totalOutstandingPortfolio;
	}

	private void setTotalOutstandingPortfolio(Money totalOutstandingPortfolio) {
		this.totalOutstandingPortfolio = totalOutstandingPortfolio;
	}

	public Money getPortfolioAtRisk() {
		return portfolioAtRisk;
	}

	private void setPortfolioAtRisk(Money portfolioAtRisk) {
		this.portfolioAtRisk = portfolioAtRisk;
	}

	private Money getTotalSavings() {
		return totalSavings;
	}

	private void setTotalSavings(Money totalSavings) {
		this.totalSavings = totalSavings;
	}

	public GroupBO getGroup() {
		return group;
	}

	public void setGroup(GroupBO group) {
		this.group = group;
	}

	public Money getAvgLoanAmountForMember() throws CustomerException {
		Money amountForActiveAccount = new Money();
		Integer countOfActiveLoans = 0;
		List<CustomerBO> clients = getChildren();
		if (clients != null) {
			for (CustomerBO client : clients) {
				amountForActiveAccount = amountForActiveAccount.add(client
						.getOutstandingLoanAmount());
				countOfActiveLoans += client.getActiveLoanCounts();
			}
		}
		if (countOfActiveLoans.intValue() > 0)
			return new Money(String.valueOf(amountForActiveAccount
					.getAmountDoubleValue()
					/ countOfActiveLoans.intValue()));
		return new Money();
	}

	public Integer getActiveClientCount() throws CustomerException {
		List<CustomerBO> clients = getChildren();
		if (clients != null) {
			return Integer.valueOf(clients.size());
		}
		return Integer.valueOf(0);
	}

	public Money getTotalOutStandingLoanAmount() throws CustomerException {
		Money amount = group.getOutstandingLoanAmount();
		List<CustomerBO> clients = getChildren();
		if (clients != null) {
			for (CustomerBO client : clients) {
				amount = amount.add(client.getOutstandingLoanAmount());
			}
		}
		return amount;
	}

	public Money getTotalSavingsAmount() throws CustomerException {
		Money amount = group.getSavingsBalance();
		List<CustomerBO> clients = getChildren();
		if (clients != null) {
			for (CustomerBO client : clients) {
				amount = amount.add(client.getSavingsBalance());
			}
		}
		return amount;
	}
	
	public void generatePortfolioAtRisk() throws CustomerException {
		Money amount = group.getBalanceForAccountsAtRisk();
		List<CustomerBO> clients = getChildren();
		if (clients != null) {
			for (CustomerBO client : clients) {
				amount = amount.add(client.getBalanceForAccountsAtRisk());
			}
		}
		if (getTotalOutStandingLoanAmount().getAmountDoubleValue() != 0.0)
			setPortfolioAtRisk(new Money(String.valueOf(amount.getAmountDoubleValue()/getTotalOutStandingLoanAmount().getAmountDoubleValue())));
	}
	
	private List<CustomerBO> getChildren() throws CustomerException {
		return group.getChildren(CustomerLevel.CLIENT,ChildrenStateType.ACTIVE_AND_ONHOLD);
	}
}
