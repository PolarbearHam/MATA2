import React from 'react';
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
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={
          <WelcomeLayout>
            <Welcome/>
          </WelcomeLayout>
        }/>
        <Route path='/start' element={
          <DashboardLayout>

          </DashboardLayout>
        }/>
        <Route path='/service/:id/setting' element={
          <DashboardLayout>
            <ServiceCustom/>
          </DashboardLayout>
        }/>
      </Routes>
    </BrowserRouter>
  )
}

export default App;