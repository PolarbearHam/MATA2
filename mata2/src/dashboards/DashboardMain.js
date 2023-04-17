import React from 'react';
import './DashboardMain.css';
import DemoLineChart from "./linechart/DemoLineChart";
import DemoAreaChart from "./areachart/DemoAreaChart";
import DemoBarChart from "./barchart/DemoBarChart";
import DemoSankeyChart from "./sankeychart/DemoSankeyChart";
import DemoPieChart from "./piechart/DemoPieChart";


function DashboardMain() {
  return (
    <div className="dashboard">
      <header className="header">
        <h1>각종 기본 정보들...</h1>
      </header>
      <main className="main">
        {/* 대시보드 화면 구성 요소 */}
        <div className="charts-container">
          <div className="chart-row">
            <DemoLineChart />
            <DemoAreaChart />
          </div>
          <div className="chart-row">
            <DemoBarChart />
            <DemoSankeyChart />
          </div>
          <div className="chart-row">
            <DemoPieChart />
          </div>
        </div>
      </main>
    </div>
  );
}

export default DashboardMain;