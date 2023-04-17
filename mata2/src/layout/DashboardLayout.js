import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import DashboardMain from "../dashboards/DashboardMain";

const DashboardLayout = (props) => {
  return (
    <div>
      <Header state={props.state}/>
      <Sidebar state={props.state}/>
      <DashboardMain/>
      {props.children}
    </div>
  )
}

export default DashboardLayout;