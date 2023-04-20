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
import TagManager from "./assets/tagmanager";
import DashboardMain from './dashboards/DashboardMain';

const mata = new TagManager();

function App() {
  const location = useLocation();
  useEffect(() => {
    mata.attach();
    return () => {
      mata.detach();
    }
  }, [location])

  // GLOBAL: ************** 사용자 정보 **************
  const [user, setUser] = useState(null);
  const userInfo = async (accessToken) => {
    if(!accessToken || accessToken=='') return;
    // fetch나 axios로 유저 정보 가져오기
    // 아래는 로그인 더미 로직
    await setTimeout(()=>{
      sessionStorage.setItem("accessToken", "dummy"); // 더미 데이터
      setUser({
        id: 1,
        email: "dummy@dummy.dum",
        name: "dummy"
      });
    }, 3000);
  }
  // GLOBAL: ************** 사용자 정보 **************

  useEffect(() => {
    let accessToken = sessionStorage.getItem("accessToken");
    console.log('accessToken useeffect전', accessToken);
    accessToken = 'dummy-token'; // 더미 데이터
    console.log('accesToken 후' , accessToken)
    userInfo(accessToken);
    return () => {

    }
  }, []);

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
          <DashboardLayout state={ {user: user} }>
            <ServiceAdd/>
          </DashboardLayout>
        }/>
        <Route path='/start' element={
          <DashboardLayout state={ {user: user} }>
            <div style={{flexBasis:"auto"}}>
              <DashboardMain state={{user: user}}/>
            </div>
          </DashboardLayout>
        }/>
        <Route path='/service/:id/setting' element={
          <DashboardLayout state={ {user: user} }>
            <ServiceCustom/>
          </DashboardLayout>
        }/>
      </Routes>
  )
}

export default App;