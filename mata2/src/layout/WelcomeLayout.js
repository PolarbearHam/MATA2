import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

const Welcome = (props) => {
  return (
    <div>
      <Header/>
      {props.children}
    </div>
  )
}

export default Welcome;