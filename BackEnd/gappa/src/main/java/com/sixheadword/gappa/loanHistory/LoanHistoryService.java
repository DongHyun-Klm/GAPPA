package com.sixheadword.gappa.loanHistory;

import com.sixheadword.gappa.loan.Loan;
import com.sixheadword.gappa.loan.repository.LoanRepository;
import com.sixheadword.gappa.loanHistory.dto.response.GetLoanHistoryResponseDto;

import com.sixheadword.gappa.loanHistory.repository.LoanHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanHistoryService {

    private final LoanHistoryRepository loanHistoryRepository;
    private final LoanRepository loanRepository;

    // 대출 및 대금 이력 상세조회
    public GetLoanHistoryResponseDto getLoanHistory(Long loanSeq, Authentication authentication) {
        Loan loan = loanRepository.findById(loanSeq).orElse(null);
        // type = 0 : 대출
        if((loan != null) && (Long.parseLong(authentication.getName()) == loan.getFromUser().getUserSeq())){
            return GetLoanHistoryResponseDto.builder()
                    .toUserName(loan.getToUser().getName())
                    .fromUserName(loan.getFromUser().getName())
                    .profileImg(loan.getToUser().getProfileImg())
                    .startDate(loan.getStartDate())
                    .redemptionDate(loan.getRedemptionDate())
                    .expiredDate(loan.getExpiredDate())
                    .principal(loan.getPrincipal())
                    .balance(loan.getPrincipal() - loan.getRedemptionMoney() + loan.getInterest())
                    .interest(loan.getInterest())
                    .redemptionMoney(loan.getRedemptionMoney())
                    .isGappa('X')
                    .build();
            // type = 1 : 대금
        }else if((loan != null) && (Long.parseLong(authentication.getName()) == loan.getToUser().getUserSeq())){
            return GetLoanHistoryResponseDto.builder()
                    .toUserName(loan.getToUser().getName())
                    .fromUserName(loan.getFromUser().getName())
                    .profileImg(loan.getFromUser().getProfileImg())
                    .startDate(loan.getStartDate())
                    .redemptionDate(loan.getRedemptionDate())
                    .expiredDate(loan.getExpiredDate())
                    .principal(loan.getPrincipal())
                    .balance(loan.getPrincipal() - loan.getRedemptionMoney() + loan.getInterest())
                    .interest(loan.getInterest())
                    .redemptionMoney(loan.getRedemptionMoney())
                    .isGappa('O')
                    .build();
        } else {
            throw new IllegalArgumentException("상세 이력을 조회할 수 없습니다.");
        }
    }
}
