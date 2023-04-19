import React from 'react';
import './Login.css';
import {useNavigate} from 'react-router-dom';
const Login = () => {
  const navigate=useNavigate()
  function handleClick(e) {
    e.preventDefault();
    
    console.log('Button clicked!');
    navigate('/signup')
  }
  return (
    <div id='loginBackground'>
      
      <form id='loginForm'>
        <div id='loginLogo'>MATA</div>
        <input className='inputField' placeholder='email' name='email'/>
        <input className='inputField' placeholder='암호' name='password'/>
        <button id='loginButton' >로그인</button>
        
        <div id='finds'>
          <div className='findsText'> 아이디 찾기</div> <div className='findsText'>비밀번호 찾기</div>
        </div>
      </form>

      <div id='below'> 
        <div id='belowExplanation'>마타를 이용하고 싶으세요?</div>
        <div id='createAccount' onClick={handleClick}>MATA 계정만들기</div>
      </div>
    </div>
  );
};

export default Login;