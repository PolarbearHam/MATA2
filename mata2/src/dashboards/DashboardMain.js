import React,{useState,useEffect} from 'react';
import './DashboardMain.css';
import DemoLineChart from "./linechart/DemoLineChart";
import DemoAreaChart from "./areachart/DemoAreaChart";
import DemoBarChart from "./barchart/DemoBarChart";
import DemoSankeyChart from "./sankeychart/DemoSankeyChart";
import DemoPieChart from "./piechart/DemoPieChart";
import Draggable, {DraggableCore} from 'react-draggable';
import { Resizable,ResizableBox } from 'react-resizable';
import GridLayout from "react-grid-layout";
import "react-grid-layout/css/styles.css";
import "react-resizable/css/styles.css";
// function DashboardMain() {
const DashboardMain = (props) => {
  const [layout, setLayout] = useState([
    {
      "w": 5,
      "h": 7,
      "x": 0,
      "y": 0,
      "i": "a",
      "moved": false,
      "static": false
    },
    {
      "w": 5,
      "h": 7,
      "x": 6,
      "y": 0,
      "i": "b",
      "moved": false,
      "static": false
    },
    {
      "w": 5,
      "h": 7,
      "x": 0,
      "y": 7,
      "i": "c",
      "moved": false,
      "static": false
    },
    {
      "w": 5,
      "h": 7,
      "x": 6,
      "y": 7,
      "i": "d",
      "moved": false,
      "static": false
    },
    {
      "w": 11,
      "h": 14,
      "x": 0,
      "y": 14,
      "i": "e",
      "moved": false,
      "static": false
    }
  ]);

  useEffect(() => {
    console.log('그리드 레이아웃은',layout)
    const storedLayout = JSON.parse(localStorage.getItem("my-grid-layout")) || [];
    setLayout(storedLayout);
    console.log('그리드 레이아웃은',layout)
  }, []);

  const onLayoutChange = (newLayout) => {
    localStorage.setItem("my-grid-layout", JSON.stringify(newLayout));
    setLayout(newLayout);
  };
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
        <GridLayout
          className="layout"
          cols={12}
          rowHeight={30}
          width={1200}
          layout={layout}
          onLayoutChange={onLayoutChange}
        >
          {/* {layout.map((item) => (
            <div key={item.i} data-grid={item}>
              <DemoPieChart />
            </div>
          ))} */}
          <div key='a' data-grid={layout[0]}>
            방문자수 라인차트
            <DemoLineChart />
          </div>
          <div key='b' data-grid={layout[1]}>
            방문자수 라인차트2
            <DemoAreaChart />
          </div>
          <div key='c' data-grid={layout[2]}>
            페이지별 이탈
            <DemoBarChart />
          </div>
          <div key='d' data-grid={layout[3]}>
            유입경로 별 점유율
            <DemoPieChart />
          </div>
          <div key='e' data-grid={layout[4]}>
            유입경로 별 점유율
            <DemoSankeyChart />
          </div>
          
          
        </GridLayout>
      </main>
    </div>
  );
}

export default DashboardMain;