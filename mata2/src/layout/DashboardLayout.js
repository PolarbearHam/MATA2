import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

const Dashboard = (props) => {
  return (
    <div>
      <Header/>
      <Sidebar/>
      {props.children}
    </div>
  )
}

export default Dashboard;