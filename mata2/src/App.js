import React, {useEffect, useState} from 'react';
import './App.css';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
import Collapse from './views/Collapse';
import Sidebar from './components/Sidebar';
import ServiceCustom from './views/ServiceCustom';
import DashboardLayout from './layout/DashboardLayout';
import WelcomeLayout from "./layout/WelcomeLayout";
import Welcome from './views/Welcome';

function App() {
  const [user, setUser] = useState(null);
  const userInfo = async (accessToken) => {
    if(!accessToken || accessToken=='') return;
    // fetch나 axios로 유저 정보 가져오기
    // 아래는 더미 데이터
    setUser({
      id: 1,
      email: "dummy@dummy.dum",
      name: "dummy"
    });
  }

  useEffect(() => {
    let accessToken = sessionStorage.getItem("accessToken");
    accessToken = 'dummy-token'; // 더미 데이터
    userInfo(accessToken);
    return () => {

    }
  }, []);

  return (
    <BrowserRouter>
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

          </WelcomeLayout>
        }/>
        <Route path='/signup' element={
          <WelcomeLayout state={ {user: user} }>

          </WelcomeLayout>
        }/>
        <Route path='/start' element={
          <DashboardLayout state={ {user: user} }>

          </DashboardLayout>
        }/>
        <Route path='/service/:id/setting' element={
          <DashboardLayout state={ {user: user} }>
            <ServiceCustom/>
          </DashboardLayout>
        }/>
      </Routes>
    </BrowserRouter>
  )
}

export default App;