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

import _ from "lodash";
import { Responsive, WidthProvider } from "react-grid-layout";
const ResponsiveReactGridLayout = WidthProvider(Responsive);

class ToolBoxItem extends React.Component {
  render() {
    return (
      <div
        className="toolbox__items__item"
        onClick={this.props.onTakeItem.bind(undefined, this.props.item)}
      >
        {this.props.item.i}
      </div>
    );
  }
}
class ToolBox extends React.Component {
  render() {
    return (
      <div className="toolbox">
        <span className="toolbox__title">Toolbox</span>
        <div className="toolbox__items">
          {this.props.items.map(item => (
            <ToolBoxItem
              key={item.i}
              item={item}
              onTakeItem={this.props.onTakeItem}
            />
          ))}
        </div>
      </div>
    );
  }
}
class ToolboxLayout extends React.Component {
  static defaultProps = {
    className: "layout",
    rowHeight: 30,
    onLayoutChange: function() {},
    cols: { lg: 12, md: 10, sm: 6, xs: 4, xxs: 2 },
    initialLayout: (localStorage.getItem("my-grid-layout")? JSON.parse(localStorage.getItem("my-grid-layout")) :[
      {
        "w": 5,
        "h": 7,
        "x": 0,
        "y": 0,
        "i": "a",
        "moved": false,
        "static": false,
        "name": '데모라인차트'
      },
      {
        "w": 5,
        "h": 7,
        "x": 6,
        "y": 0,
        "i": "b",
        "moved": false,
        "static": false,
        "name": '데모라인차트'
      },
      {
        "w": 5,
        "h": 7,
        "x": 0,
        "y": 7,
        "i": "c",
        "moved": false,
        "static": false,
        "name": '데모라인차트'
      },
      {
        "w": 5,
        "h": 7,
        "x": 6,
        "y": 7,
        "i": "d",
        "moved": false,
        "static": false,
        "name": '데모라인차트'
      },
      {
        "w": 11,
        "h": 14,
        "x": 0,
        "y": 14,
        "i": "e",
        "moved": false,
        "static": false,
        "name": '데모라인차트'
      }
    ])
  };

  state = {
    currentBreakpoint: "lg",
    compactType: null,
    mounted: false,
    layouts: { lg: this.props.initialLayout },
    toolbox: { lg: [] }
  };

  componentDidMount() {
    this.setState({ mounted: true });
  }

  generateDOM() {
    let component;
    let chartName
    return _.map(this.state.layouts[this.state.currentBreakpoint], l => {
      
      switch(l.i) {
        case "a":
          component = <DemoLineChart />;
          break;
        case "b":
          component = <DemoAreaChart />;
          break;
        case "c":
          component = <DemoBarChart />;
          break
        case "d":
          component= <DemoPieChart/>
          break
        case "e" :
          component= <DemoSankeyChart/>
          break
        default :
        component= '기타입니다.'
      }
      switch(l.i) {
        case "a":
          chartName = "데모라인차트"
          break;
        case "c":
          chartName = "컴포넌트 별 클릭수"
          break;
        default:
          chartName='다른차트'
      }
      return (
        <div key={l.i} className={l.static ? "static" : ""}>
          <div className="hide-button" onClick={this.onPutItem.bind(this, l)}>
            &times;
          </div>
          {l.static ? (
            <span
              className="text"
              title="This item is static and cannot be removed or resized."
            >
              Static - {l.i} {l.name}
            </span>
          ) : (
            <span className="text">{l.i} {chartName}</span>
          )}
          <div className="h-100 ">
            
            {component}
          </div>

          
        </div>
      );
    });
  }

  onBreakpointChange = breakpoint => {
    this.setState(prevState => ({
      currentBreakpoint: breakpoint,
      toolbox: {
        ...prevState.toolbox,
        [breakpoint]:
          prevState.toolbox[breakpoint] ||
          prevState.toolbox[prevState.currentBreakpoint] ||
          []
      }
    }));
  };

  onCompactTypeChange = () => {
    const { compactType: oldCompactType } = this.state;
    const compactType =
      oldCompactType === "horizontal"
        ? "vertical"
        : oldCompactType === "vertical"
        ? null
        : "horizontal";
    this.setState({ compactType });
  };

  onTakeItem = item => {
    this.setState(prevState => ({
      toolbox: {
        ...prevState.toolbox,
        [prevState.currentBreakpoint]: prevState.toolbox[
          prevState.currentBreakpoint
        ].filter(({ i }) => i !== item.i)
      },
      layouts: {
        ...prevState.layouts,
        [prevState.currentBreakpoint]: [
          ...prevState.layouts[prevState.currentBreakpoint],
          item
        ]
      }
    }));
  };

  onPutItem = item => {
    this.setState(prevState => {
      return {
        toolbox: {
          ...prevState.toolbox,
          [prevState.currentBreakpoint]: [
            ...(prevState.toolbox[prevState.currentBreakpoint] || []),
            item
          ]
        },
        layouts: {
          ...prevState.layouts,
          [prevState.currentBreakpoint]: prevState.layouts[
            prevState.currentBreakpoint
          ].filter(({ i }) => i !== item.i)
        }
      };
    });
  };

  onLayoutChange = (layout, layouts) => {
    console.log('레이아웃 바뀜 layout,layouts',layout,layouts)
    this.props.onLayoutChange(layout, layouts);
    this.setState({ layouts });
    localStorage.setItem("my-grid-layout", JSON.stringify(layout));
  };

  // onNewLayout = () => {
  //   this.setState({
  //     layouts: { lg: generateLayout() }
  //   });
  // };

  render() {
    return (
      <div>
        <div>
          Current Breakpoint: {this.state.currentBreakpoint} (
          {this.props.cols[this.state.currentBreakpoint]} columns)
        </div>
        <div>
          Compaction type:{" "}
          {_.capitalize(this.state.compactType) || "No Compaction"}
        </div>
        {/* <button onClick={this.onNewLayout}>Generate New Layout</button> */}
        <button onClick={this.onCompactTypeChange}>
          Change Compaction Type
        </button>

        <ToolBox
          items={this.state.toolbox[this.state.currentBreakpoint] || []}
          onTakeItem={this.onTakeItem}
        />

        <ResponsiveReactGridLayout
          {...this.props}
          layouts={this.state.layouts}
          onBreakpointChange={this.onBreakpointChange}
          onLayoutChange={this.onLayoutChange}
          // WidthProvider option
          measureBeforeMount={false}
          // I like to have it animate on mount. If you don't, delete `useCSSTransforms` (it's default `true`)
          // and set `measureBeforeMount={true}`.
          useCSSTransforms={this.state.mounted}
          compactType={this.state.compactType}
          preventCollision={!this.state.compactType}
        >
          {this.generateDOM()}
        </ResponsiveReactGridLayout>
      </div>
    );
  }
}

// function generateLayout() {
//   return _.map(_.range(0, 4), function(item, i) {
//     var y = Math.ceil(Math.random() * 4) + 1;
//     return {
//       x: (_.random(0, 5) * 2) % 12,
//       y: Math.floor(i / 6) * y,
//       w: 2,
//       h: y,
//       i: i.toString(),
//       static: Math.random() < 0.05
//     };
//   });
// }
const DashboardMain = (props) => {
  const [layout, setLayout] = useState ((localStorage.getItem("my-grid-layout")? JSON.parse(localStorage.getItem("my-grid-layout")) :[
    {
      "w": 5,
      "h": 7,
      "x": 0,
      "y": 0,
      "i": "a",
      "moved": false,
      "static": false,
      "name": '데모라인차트'
    },
    {
      "w": 5,
      "h": 7,
      "x": 6,
      "y": 0,
      "i": "b",
      "moved": false,
      "static": false,
      "name": '데모라인차트'
    },
    {
      "w": 5,
      "h": 7,
      "x": 0,
      "y": 7,
      "i": "c",
      "moved": false,
      "static": false,
      "name": '데모라인차트'
    },
    {
      "w": 5,
      "h": 7,
      "x": 6,
      "y": 7,
      "i": "d",
      "moved": false,
      "static": false,
      "name": '데모라인차트'
    },
    {
      "w": 11,
      "h": 14,
      "x": 0,
      "y": 14,
      "i": "e",
      "moved": false,
      "static": false,
      "name": '데모라인차트'
    }
  ]));

  useEffect(() => {
    console.log('그리드 레이아웃은',layout)
    const storedLayout = JSON.parse(localStorage.getItem("my-grid-layout")) || [];
    setLayout(storedLayout);
    console.log('그리드 레이아웃은',layout)
  },[]);

  const onLayoutChange = (newLayout) => {
    console.log('레이아웃 바뀜',newLayout)
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
        
        </div>
        {/* <GridLayout
          className="layout"
          cols={12}
          rowHeight={30}
          width={1200}
          layout={layout}
          onLayoutChange={onLayoutChange}
        >
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
          
          
        </GridLayout> */}
        <ToolboxLayout/>


        
      </main>
    </div>
  );
}

export default DashboardMain;