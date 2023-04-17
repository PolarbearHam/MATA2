import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

const DashboardLayout = (props) => {
  return (
    <div>
      <Header state={props.state}/>
      <Sidebar state={props.state}/>
      {props.children}
    </div>
  )
}

export default DashboardLayout;