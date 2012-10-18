package org.mifos.application.servicefacade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.mifos.clientportfolio.newloan.applicationservice.CreateGroupLoanAccount;
import org.mifos.clientportfolio.newloan.applicationservice.LoanAccountCashFlow;
import org.mifos.dto.domain.GroupIndividualLoanDto;
import org.mifos.dto.domain.OriginalScheduleInfoDto;
import org.mifos.dto.screen.LoanCreationResultDto;
import org.mifos.platform.questionnaire.service.QuestionGroupDetail;

public interface GroupLoanAccountServiceFacade {

    LoanCreationResultDto createGroupLoan(CreateGroupLoanAccount createGroupLoanAccount,List<QuestionGroupDetail> questionGroups, LoanAccountCashFlow loanAccountCashFlow);
    
    Integer getMemberClientId(String globalCustNum);
    
    int getNumberOfMemberAccounts(Integer accountId);
    
    List<Integer> getListOfMemberAccountIds(Integer parentAccountId);
    
    List<String> getListOfMemberGlobalAccountNumbers(Integer parentAccountId);
    
    List<GroupIndividualLoanDto> getMemberLoansAndDefaultPayments(Integer parentAccountId, BigDecimal amount);
    
}
