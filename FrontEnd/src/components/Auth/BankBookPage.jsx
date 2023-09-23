import React, { useEffect, useState } from 'react';
import Headers from './Headers';
import style from './BankBookPage.module.css';
import { useNavigate } from 'react-router-dom';
import { useDispatch,useSelector } from 'react-redux';
import { authActions } from '../../store/authslice';
import { customAxios } from '../api/customAxios';

const BankBookPage = () => {
  const title = "대표 계좌 선택";
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const userInfo = useSelector(state => state.auth);
  function Book({ account_Number, bankname, balance, index, clickedItems,bank_Img, onClick }) {
    // 클릭 상태에 따라 스타일을 동적으로 설정
    const itemStyle = {
      backgroundColor: clickedItems[index] ? 'lightblue' : 'white',
    };
    return (
      <div className={style.all}>
        {/* <div className={style.bankbooksize}> */}
        <div className={style.bankbooklist} style={itemStyle} onClick={() => onClick(index)}>
          <div className={style.imgstyle}>
            <img src={bank_Img} alt="" />
          </div>
          <div>
            <span>{account_Number}</span>
            <br />
            <br />
            <span className={style.bankcolor}>{bankname}</span>
          </div>
          <div>
            <br />
            <br />
            <span>{balance} 원</span>
          </div>
        </div>
        <hr />
        {/* </div> */}
      </div>
    );
  }

  const [account, setAccount] = useState();
  useEffect(()=>{
    customAxios.get("/accounts")
    .then((res)=>{
      console.log(res)
    })
  },[])

  const bankBookData = [
    {
      account_Number: 1,
      bank: "KB국민은행",
      balance: 1000000,
      bank_Img : "images/Ssafy.png"
    },
    {
      account_Number: 1,
      bank: "KEB하나은행",
      balance: 1000000,
      bank_Img : "images/Ssafy.png"
    },
    {
      account_Number: 1,
      bank: "신한은행",
      balance: 1000000,
      bank_Img : "images/Ssafy.png"
    },
    {
      account_Number: 1,
      bank: "우리은행",
      balance: 1000000,
      bank_Img : "images/Ssafy.png"
    },
    {
      account_Number: 1,
      bank: "싸피은행",
      balance: 1000000,
      bank_Img : "images/Ssafy.png"
    },
    {
      account_Number: 1,
      bank: "갚파은행",
      balance: 1000000,
      bank_Img : "images/Ssafy.png"
    },
  ]

  const [pass, setPass] = useState(false);

  // 클릭 상태를 저장할 배열 생성 및 초기값 설정
  const [clickedItems, setClickedItems] = useState(Array(bankBookData.length).fill(false));

  // 클릭 이벤트 핸들러
  const handleItemClick = (index) => {

    // 클릭된 항목의 상태를 변경
    const updatedClickedItems = Array(bankBookData.length).fill(false);
    updatedClickedItems[index] = !clickedItems[index];

    // redux에 저장
    dispatch(authActions.updatedUserBank(bankBookData[index]))
    console.log(userInfo)

    // 클릭된 항목의 개수를 세기
    const clickedCount = updatedClickedItems.filter((item) => item).length;
    setPass(clickedCount === 1);
    setClickedItems(updatedClickedItems);
  };

  const dataRequest = () => {
    // customAxios.post
    navigate("/masterbankbook");
  }

  return (
    <div className={style.bankbook}>
      <Headers title={title} />
      <div className={style.bankbookstyle}>
        {bankBookData.map((data, index) =>
        (<Book
          account_Number={data.account_Number}
          bankname={data.bank}
          balance={data.balance}
          bank_Img={data.bank_Img}
          key={index}
          index={index}
          clickedItems={clickedItems} // 클릭 상태 배열 전달
          onClick={handleItemClick} // 클릭 이벤트 핸들러 전달
        />))}
        <span>다음에 하기</span>
      </div>
      {pass
        ?
        <button className={style.btn} onClick={dataRequest}>확인</button>
        :
        <button className={style.notbtn}>확인</button>
      }
    </div>
  );
}

export default BankBookPage;
