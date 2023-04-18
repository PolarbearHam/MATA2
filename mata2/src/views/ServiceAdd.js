import React from 'react';

const ServiceAdd = () => {
  return (
    <div className='vh-100'>
      <form className='container bg-light d-flex flex-column justify-content-center flex-grow' >
        <div >서비스 추가</div>
        <input  placeholder='서비스 이름'/>
        <input  placeholder='서비스 주소 url'/>

        <p>카테고리</p>
        <select>
          <option value='블로그'>블로그</option>
          <option value='쇼핑'>쇼핑</option>
          <option value='SNS'>SNS</option>    
        </select>
        <button  >추가</button>
        
       
      </form>
      
    </div>
  );
};

export default ServiceAdd;