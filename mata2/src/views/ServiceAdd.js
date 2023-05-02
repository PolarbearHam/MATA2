import React from 'react';
import './ServiceAdd.css'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
const ServiceAdd = () => {
  const navigate=useNavigate()
  const [serviceName, setServiceName] = useState('');
  const [serviceUrl, setServiceUrl] = useState('');
  const [category, SetCategory]=useState('')
  function handleClick(e) {
    e.preventDefault();
    
    console.log('Button clicked!');
    navigate('/signup')
  }
  function serviceAdd (e) {
    e.preventDefault();
    const formData={
      name:serviceName,
      url:serviceUrl,
      category:category,
    }
    const headers = {
      "Authorization": `Bearer ${sessionStorage.getItem('accessToken')}`,
    }
    console.log("로그인 시도",formData)
    
    axios.post('//localhost:8080/api/v1/project/add',formData,{headers})

    .then(response => {
      console.log(response);
      if (response.status==200) {
        navigate('/start') 
        
      }else alert('틀림')
    })
    .catch(error => {
      console.error(error);
    });
  }
  return (
    <div id='ServiceAdd-background' className='vh-100'>
      <form  id='ServiceAddForm' onSubmit={serviceAdd} >
        <div className='text-xl'>서비스 추가</div>
        <input className='inputField'  placeholder='서비스 이름' name='serviceName' type='text' onChange={(e)=>{setServiceName(e.target.value)}}/>
        <input className='inputField' placeholder='서비스 주소 url' name='serviceURL' type='url' onChange={(e)=>{setServiceUrl(e.target.value)}}/>

        <label>카테고리
        <select className='inputField' name='category' onChange={(e)=>{SetCategory(e.target.value)}}>
          <option value='BLOG'>블로그</option>
          <option value='COMMERCE'>쇼핑</option>
          <option value='PORTAL'>포탈</option>    
        </select>
        </label>
        <button id='ServiceAddButton' >추가</button>
        
       
      </form>
      
    </div>
  );
};

export default ServiceAdd;