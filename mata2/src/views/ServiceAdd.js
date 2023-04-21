import React from 'react';
import './ServiceAdd.css'
const ServiceAdd = () => {
  return (
    <div id='ServiceAdd-background' className='vh-100'>
      <form  id='ServiceAddForm' >
        <div className='text-xl'>서비스 추가</div>
        <input className='inputField'  placeholder='서비스 이름' name='serviceName'/>
        <input className='inputField' placeholder='서비스 주소 url' name='serviceURL'/>

        <label>카테고리
        <select className='inputField' name='category'>
          <option value='블로그'>블로그</option>
          <option value='쇼핑'>쇼핑</option>
          <option value='SNS'>SNS</option>    
        </select>
        </label>
        <button id='ServiceAddButton' >추가</button>
        
       
      </form>
      
    </div>
  );
};

export default ServiceAdd;