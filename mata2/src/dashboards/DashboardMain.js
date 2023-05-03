import React from 'react';
import './DashboardMain.css';
import DemoLineChart from "./linechart/DemoLineChart";
import DemoAreaChart from "./areachart/DemoAreaChart";
import DemoBarChart from "./barchart/DemoBarChart";
import DemoSankeyChart from "./sankeychart/DemoSankeyChart";
import DemoPieChart from "./piechart/DemoPieChart";
import Draggable, {DraggableCore} from 'react-draggable';
import { Resizable,ResizableBox } from 'react-resizable';

// function DashboardMain() {
const DashboardMain = (props) => {
  return (
    <div className="dashboard flex-grow-1">
      <header className="header" >
        {!props.state.user
          ? (<h1> 김 아무개의 대시보드 </h1>)
          : (<h1> {props.state.user.name}의 대시보드</h1>)
        }
      </header>
      <main className="main"  >
        {/* 대시보드 화면 구성 요소 */}
        <div className=" charts-container gap-3">
        <Draggable> 
          <Resizable>
          <div className='card'>

            <p> 방문자수 </p>

              <div className=" chart-row">
                <div className="chart-col col-6 col-lg-6 col-md-6 col-sm-6">
                  <DemoLineChart />
                </div>
                <div className="chart-col col-6 col-lg-6 col-md-6 col-sm-6">
                  <DemoAreaChart />
                </div>
              </div>
            </div>
            </Resizable>
          </Draggable>
          <Draggable>
            <div className='card'>
              <p>기타 컴포넌트 등 </p>
              <div className="chart-row">
                <div className="chart-col">
                  <DemoBarChart />
                </div>
                <div className="chart-col">
                  <DemoPieChart />
                </div>
              </div>
            </div>
          </Draggable>
          <Draggable>
            <div className="chart-row">
              <div className="chart-col">
                <DemoSankeyChart />
              </div>
            </div>
          </Draggable>
        </div>
      </main>
    </div>
  );
}

export default DashboardMain;