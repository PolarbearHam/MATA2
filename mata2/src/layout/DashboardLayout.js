import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import DashboardMain from "../dashboards/DashboardMain";

const DashboardLayout = (props) => {
  return (
    <div>
      <Header state={props.state}/>
      
      
      <div style={{ display: "flex", flexGrow: "1" }}>
        <div>
        <Sidebar state={props.state}/>
        </div>
        {props.children}
     
      </div>
      
    </div>
  )
}

export default DashboardLayout;