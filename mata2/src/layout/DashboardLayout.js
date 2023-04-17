import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import DashboardMain from "../dashboards/DashboardMain";

const DashboardLayout = (props) => {
  return (
    <div>
      <Header state={props.state}/>
      <div style={{ display: "flex", flexGrow: "1" }}>
        <div style={{width:"15%", minWidth:"150px"}}>
          <Sidebar state={props.state}/>
        </div>
        <div style={{flexBasis:"auto"}}>
          <DashboardMain state={props.state}/>
        </div>
      </div>
      {props.children}
    </div>
  )
}

export default DashboardLayout;