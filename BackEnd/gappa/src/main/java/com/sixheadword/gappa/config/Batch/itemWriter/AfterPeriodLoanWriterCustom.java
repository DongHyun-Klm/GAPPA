package com.sixheadword.gappa.config.Batch.itemWriter;

import com.sixheadword.gappa.account.Account;
import com.sixheadword.gappa.account.repository.AccountRepository;
import com.sixheadword.gappa.accountHistory.AccountHistory;
import com.sixheadword.gappa.accountHistory.repository.AccountHistoryRepository;
import com.sixheadword.gappa.config.Batch.dto.AfterPeriodLoanDto;
import com.sixheadword.gappa.loan.Loan;
import com.sixheadword.gappa.loan.repository.LoanRepository;
import com.sixheadword.gappa.loanHistory.entity.LoanHistory;
import com.sixheadword.gappa.loanHistory.repository.LoanHistoryRepository;
import com.sixheadword.gappa.webAlarm.WebAlarm;
import com.sixheadword.gappa.webAlarm.WebAlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AfterPeriodLoanWriterCustom implements ItemWriter<AfterPeriodLoanDto> {

    private final LoanRepository loanRepository;
    private final LoanHistoryRepository loanHistoryRepository;
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final WebAlarmRepository webAlarmRepository;

    @Override
    public void write(List<? extends AfterPeriodLoanDto> items) throws Exception {
        log.info(">>>>> Spring Batch With AfterPeriodLoanWriter was Executed");

        items.forEach(afterPeriodLoanDto -> {
            Loan loan = afterPeriodLoanDto.getLoan();
            LoanHistory loanHistory = afterPeriodLoanDto.getLoanHistory();
            Account fromUserAccount = afterPeriodLoanDto.getFromUserAccount();
            Account toUserAccount = afterPeriodLoanDto.getToUserAccount();
            AccountHistory fromUserAccountHistory = afterPeriodLoanDto.getFromUserAccountHistory();
            AccountHistory toUserAccountHistory = afterPeriodLoanDto.getToUserAccountHistory();
            WebAlarm fromUserWebAlarm = afterPeriodLoanDto.getFromUserWebAlarm();
            WebAlarm toUserWebAlarm = afterPeriodLoanDto.getToUserWebAlarm();

            loanRepository.save(loan);
            loanHistoryRepository.save(loanHistory);
            accountRepository.save(fromUserAccount);
            accountRepository.save(toUserAccount);
            accountHistoryRepository.save(fromUserAccountHistory);
            accountHistoryRepository.save(toUserAccountHistory);
            webAlarmRepository.save(fromUserWebAlarm);
            webAlarmRepository.save(toUserWebAlarm);
        });
    }
}
