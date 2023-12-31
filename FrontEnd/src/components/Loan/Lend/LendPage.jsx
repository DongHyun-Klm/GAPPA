import React, { useEffect, useState } from 'react';
import HeaderSub from '../../Common/HeaderSub';
import { useNavigate, useLocation  } from 'react-router-dom';
import style from './LendPage.module.css';
import { customAxios } from '../../api/customAxios';
import toast, { Toaster } from 'react-hot-toast';

const LendPage = () => {
  const navigate = useNavigate();

  const location = useLocation();
  const loanSeq = location.state.loanSeq;

  const [toUser, setToUser] = useState("");
  const [fromUser, setFromUser] = useState("");
  const [principal, setPrincipal] = useState(0);
  const [startDate, setStartDate] = useState("");
  const [redemptionDate, setRedemptionDate] = useState("");
  const [isRefuseOpen, setIsRefuseOpen] = useState(false);

  useEffect(() => {
    getApply();
  }, []);

  const getApply = () => {
    customAxios.get(`/loan/apply/${loanSeq}`)
    .then((res)=>{
      setToUser(res.data.toUser);
      setFromUser(res.data.fromUser);
      setPrincipal(res.data.principal);
      setStartDate(res.data.startDate);
      setRedemptionDate(res.data.redemptionDate);
    })
    .catch((res)=>{
    })
  }

  const formattingDate = (date) => {
    // Date 객체로 파싱
    var inputDate = new Date(date);
    // 출력 형식 정의
    var options = { year: 'numeric', month: 'long', day: 'numeric' };
    // 형식에 맞게 출력
    var formattedDate = inputDate.toLocaleDateString('ko-KR', options);
    return formattedDate;
  }

  const refuseClick = () => {
    setIsRefuseOpen(true);
  }

  const toggleSearch = () => {
    setIsRefuseOpen(!isRefuseOpen);
  };

  const checkCert = (loanSeq) => {
    const isCPK = localStorage.getItem("CPK");
    const isExpire = localStorage.getItem("expire");
    if(isCPK === null || isExpire === null){
      toast.error("인증서 발급이 필요합니다", {
        duration: 1000,
      });
      setTimeout(() => {
        navigate("/mycertificate")
      }, 1000);
    } else {
      navigate('/lend/check', { state: { loanSeq: loanSeq}})
    }
  }

  const refuse = () => {
    const body = {
      loanSeq : loanSeq
    };

    customAxios.post('/loan/money/fail', body)
    .then((res)=>{
      navigate('/lend/list');
    })
    .catch((res)=>{
    });
  }

  return (
    <div className={style.body}>
      <HeaderSub title={"대금 신청"} />
      <Toaster />
      <div className={style.container}>
        <div className={style.messageBox}>
          <div className={style.message}><span className={style.bold}>{fromUser}</span> 님이 <span className={style.bold}>{toUser}</span> 님에게</div>
          <div className={style.message}><span className={style.bold}>{principal.toLocaleString()}원</span> 을 요청했어요</div>
        </div>
        <div className={style.detailBox}>
          <div className={style.title}>요청 대출 내용</div>
          <div className={style.detail}>
            <div className={style.detailKey}>대출금</div>
            <div className={style.detailValue}>{principal.toLocaleString()}원</div>
          </div>
          <div className={style.detail}>
            <div className={style.detailKey}>신청일자</div>
            <div className={style.detailValue}>{formattingDate(startDate)}</div>
          </div>
          <div className={style.title}>요청 상환일</div>
          <div className={style.detail}>
            <div className={style.detailKey}>상환일</div>
            <div className={style.detailValue}>{formattingDate(redemptionDate)}</div>
          </div>
          
        </div>
        <div className={style.btnBox}>
          <div onClick={refuseClick}>거절</div>
          <div onClick={() => checkCert(loanSeq) }>승인</div>
        </div>
      </div>
      {isRefuseOpen && (
        <div className={style.overlay} onClick={toggleSearch}></div>
      )}
      {isRefuseOpen && (
        <div className={style.resultBox}>
          <div className={style.infoBox}>
            해당 대출 요청을 거절하시겠습니까?
          </div>
          <div className={style.resultBtnBox}>
              <div className={style.resultBtn}>
                <button onClick={toggleSearch}>취소</button>
                <button onClick={refuse}>확인</button>
              </div>
            </div>
        </div>
      )}
    </div>
  );
};

export default LendPage;