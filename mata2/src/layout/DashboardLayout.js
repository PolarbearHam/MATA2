import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import DashboardMain from "../dashboards/DashboardMain";

const Dashboard = (props) => {
  return (
    <div>
      <Header/>
      <Sidebar/>
      <DashboardMain/>
      {props.children}
    </div>
  )
}

export default Dashboard;