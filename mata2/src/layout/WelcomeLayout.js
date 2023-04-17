import {Outlet} from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

const WelcomeLayout = (props) => {
  return (
    <div>
      <Header/>
      {props.children}
    </div>
  )
}

export default WelcomeLayout;