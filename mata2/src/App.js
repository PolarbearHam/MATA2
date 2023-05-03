import React, {useEffect, useState} from 'react';
import './App.css';
import {BrowserRouter, Routes, Route, useLocation} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
import ServiceCustom from './views/ServiceCustom';
import DashboardLayout from './layout/DashboardLayout';
import WelcomeLayout from "./layout/WelcomeLayout";
import Welcome from './views/Welcome';
import Login from "./views/Login";
import Logout from "./views/Logout";
import SignUp from './views/SignUp';
import ServiceAdd from './views/ServiceAdd';
import DashboardMain from './dashboards/DashboardMain';
import axios from "axios";


// SPA 제공 코드
import TagManager from "http://localhost:8080/api/v1/js/2";
const mata = new TagManager();

function App() {
  const location = useLocation();
  const [serviceList,setServiceList]=useState([])

  useEffect(() => {
    mata.attach();
    return () => {
      mata.detach();
    }
  }, [location])

  // GLOBAL: ************** 사용자 정보 **************
  const [user, setUser] = useState({

  });
  const userInfo = async (accessToken) => {
    if(!accessToken || accessToken=='') return;
    // fetch나 axios로 유저 정보 가져오기
    // 아래는 로그인 더미 로직
    // const formData= {
    //   "grantType": "Bearer",
    //   "accessToken": accessToken
    // }

    // const headers = {
    //   'Content-type': 'application/json'
    // }
    // console.log('axios요청 보냄',formData)
    // axios.get("localhost:8081/api/v1/member/info",formData)
    
    // .then(response => {
    //     console.log(response);
        
    // })
    // .catch(error => {
    //     console.error(error);
    // });
    // await setTimeout(()=>{
    //   setUser({
    //     id: 1,
    //     email: "dummy@dummy.dum",
    //     name: "dummy"
    //   });
    // }, 3000);
    accessToken = sessionStorage.getItem("accessToken");
    const headers = {
      "Authorization": `Bearer ${accessToken}`,
    }
    const formData= {
      "grantType": "Bearer",
      "accessToken": accessToken
    }
    axios({
      //request
      method: "get",
      url: "//localhost:8080/api/v1/member/info",
      responseType: "type",
      headers: headers
  }).then(function (response) {
      console.log(response)
      const userResponse=JSON.parse(response.data)
      console.log(userResponse)
      setUser({
        id: userResponse.id,
        email: userResponse.email,
        name: userResponse.name
      });
  })
    .catch(error => {
        console.error(error);
    });
  }
  // GLOBAL: ************** 사용자 정보 **************

  useEffect(() => {
    let accessToken = sessionStorage.getItem("accessToken");
    
    const headers = {
      "Authorization": `Bearer ${accessToken}`,
    }
    if (accessToken) {
      axios({method:"get",url:"//localhost:8080/api/v1/project/",headers:headers})
      .then(res=>{
        setServiceList(res.data)
      })
      .catch(err=>{
      })
    }
    
    const formData= {
      "grantType": "Bearer",
      "accessToken": accessToken
    }
    axios({
      //request
      method: "get",
      url: "//localhost:8080/api/v1/member/info",
      responseType: "type",
      headers: headers
  }).then(function (response) {
      console.log(response)
      const userResponse=JSON.parse(response.data)
      console.log(userResponse)
      setUser({
        id: userResponse.id,
        email: userResponse.email,
        name: userResponse.name
      });
  })
    .catch(error => {
        console.error(error);
    });
    
    // accessToken = 'dummy-token'; // 더미 데이터
    // userInfo(accessToken);
    return () => {
    }
  },[]);
  useEffect(() => {
    let accessToken = sessionStorage.getItem("accessToken");
    
    const headers = {
      "Authorization": `Bearer ${accessToken}`,
    }
    if (accessToken) {
      axios({method:"get",url:"//localhost:8080/api/v1/project/",headers:headers})
      .then(res=>{
        setServiceList(res.data)
      })
      .catch(err=>{
      })
    }
    
    const formData= {
      "grantType": "Bearer",
      "accessToken": accessToken
    }
    axios({
      //request
      method: "get",
      url: "//localhost:8080/api/v1/member/info",
      responseType: "type",
      headers: headers
  }).then(function (response) {
      console.log(response)
      const userResponse=JSON.parse(response.data)
      console.log(userResponse)
      setUser({
        id: userResponse.id,
        email: userResponse.email,
        name: userResponse.name
      });

  })
    .catch(error => {
        console.error(error);
    });
    

 
  },[sessionStorage.getItem('accessToken')]);



  return (
      <Routes>
        <Route path='/' element={
          <WelcomeLayout state={ {user: user} }>
            <Welcome/>
          </WelcomeLayout>
        }/>
        <Route path='/login' element={
          <WelcomeLayout state={ {user: user} }>
          <Login/>
          </WelcomeLayout>
        }/>
        <Route path='/logout' element={
          <WelcomeLayout state={ {user: user} }>
            <Logout state={ {user: user, setUser: setUser} }/>
          </WelcomeLayout>
        }/>
        <Route path='/signup' element={
          <WelcomeLayout state={ {user: user} }>
            <SignUp></SignUp>
          </WelcomeLayout>
        }/>
        <Route path='/service-add' element={
          <DashboardLayout state={ {user: user,serviceList:serviceList} } >
            <ServiceAdd/>
          </DashboardLayout>
        }/>
        <Route path='/start' element={
          <DashboardLayout state={ {user: user,serviceList:serviceList} }>
              <DashboardMain state={{user: user}}/>
          </DashboardLayout>
        }/>
        <Route path='/service/:id/setting' element={
          <DashboardLayout state={ {user: user,serviceList:serviceList} }>
            <ServiceCustom state={ {user: user,serviceList:serviceList} }/>
          </DashboardLayout>
        }/>
      </Routes>
  )
}
export default App;