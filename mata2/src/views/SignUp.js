import React from 'react';
import './SignUp.css'
const SignUp = () => {
  return (
    <div id='signUpBackground'>
      <form id='signUpForm'>
        <div id='signUpTitle'>회원가입</div>
        <input className='inputField' placeholder='이메일'/>
        <input className='inputField'placeholder='비밀번호'/>
        <input className='inputField'placeholder='비밀번호 확인'/>
        <div id='agreement'>이용약관</div>
        <label id='checkBox'>
          <input type='checkbox'/>
          위 약관에 동의합니다.
        </label> 
        <div id='buttons'>
          <button className='button'>취소</button>
          <button>가입합니다</button>
        </div>
      </form>
      
    </div>
  );
};

export default SignUp;