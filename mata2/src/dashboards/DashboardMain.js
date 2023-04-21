import React from 'react';
import './DashboardMain.css';
import DemoLineChart from "./linechart/DemoLineChart";
import DemoAreaChart from "./areachart/DemoAreaChart";
import DemoBarChart from "./barchart/DemoBarChart";
import DemoSankeyChart from "./sankeychart/DemoSankeyChart";
import DemoPieChart from "./piechart/DemoPieChart";


// function DashboardMain() {
const DashboardMain = (props) => {
  return (
    <div className="dashboard">
      <header className="header">
        {!props.state.user
          ? (<h1> 김 아무개의 대시보드 </h1>)
          : (<h1> {props.state.user.name}의 대시보드</h1>)
        }
      </header>
      <main className="main">
        {/* 대시보드 화면 구성 요소 */}
        <div className="charts-container">
          <div className="chart-row">
            <div className="chart-col">
              <DemoLineChart />
            </div>
            <div className="chart-col">
              <DemoAreaChart />
            </div>
          </div>
          <div className="chart-row">
            <div className="chart-col">
              <DemoBarChart />
            </div>
            <div className="chart-col">
              <DemoPieChart />
            </div>
          </div>
          <div className="chart-row">
            <div className="chart-col">
              <DemoSankeyChart />
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default DashboardMain;