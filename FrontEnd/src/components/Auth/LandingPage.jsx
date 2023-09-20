import React from 'react';
import style from './LandingPage.module.css';
import { useNavigate } from 'react-router-dom';
const LandingPage = () => {

  const navigate = useNavigate();

  return (
    <div className={style.main}>
      <img src="./images/gappalogo.png" alt="" className={style.logo}/>
      <img src="./images/gappa.png" alt="" className={style.gappa}/>
      <div className={style.btn}>
      <img src="./images/login.png" alt="" onClick={()=>{navigate('/login')}}/>
      <img src="./images/signup.png" alt="" onClick={()=>{navigate('/agreement')}}/>
      </div>
    </div>
  );
};

export default LandingPage;