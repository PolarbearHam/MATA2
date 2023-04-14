import React from 'react';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
import Collapse from './views/Collapse';
import Sidebar from './components/Sidebar';
import ServiceCustom from './views/ServiceCustom';


function App() {
	return(
		<BrowserRouter>
      <Sidebar/>
			<Routes>
        <Route path='/service/:id/setting' element={ServiceCustom()}/>
        <Route path='*' element={Collapse}/>

      </Routes>
		</BrowserRouter>
	)
}

export default App;