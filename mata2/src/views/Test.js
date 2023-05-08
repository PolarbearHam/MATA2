import React from "react";
import _ from "lodash";
import { Responsive, WidthProvider } from "react-grid-layout";
import DemoLineChart from "../dashboards/linechart/DemoLineChart";
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

const test = () => {
  const defaultProps = {
    className: "layout",
    rowHeight: 30,
    onLayoutChange: function() {},
    cols: { lg: 12, md: 10, sm: 6, xs: 4, xxs: 2 },
    initialLayout: generateLayout()
  };
  return (
    <div>
      <ToolboxLayout/>
    </div>
  );
};

export default test;

class ToolboxLayout extends React.Component {
  static defaultProps = {
    className: "layout",
    rowHeight: 30,
    onLayoutChange: function() {},
    cols: { lg: 12, md: 10, sm: 6, xs: 4, xxs: 2 },
    initialLayout: [
      {
        "w": 5,
        "h": 7,
        "x": 0,
        "y": 0,
        "i": "a",
        "moved": false,
        "static": false,
        "component": DemoLineChart,
        "name":"데모라인차트",
      },
      {
        "w": 5,
        "h": 7,
        "x": 6,
        "y": 0,
        "i": "b",
        "moved": false,
        "static": false,
        "component": <DemoLineChart/>,
        "name":"데모라인차트",
      },
      {
        "w": 5,
        "h": 7,
        "x": 0,
        "y": 7,
        "i": "c",
        "moved": false,
        "static": false,
        "component": <DemoLineChart/>,
        "name":"데모라인차트",
      },
      {
        "w": 5,
        "h": 7,
        "x": 6,
        "y": 7,
        "i": "d",
        "moved": false,
        "static": false,
        "component": <DemoLineChart/>,
        "name":"데모라인차트",
      },
      {
        "w": 11,
        "h": 14,
        "x": 0,
        "y": 14,
        "i": "e",
        "moved": false,
        "static": false,
        "component": <DemoLineChart/>,
        "name":"데모라인차트",
      }
    ]
  };

  state = {
    currentBreakpoint: "lg",
    compactType: "vertical",
    mounted: false,
    layouts: { lg: this.props.initialLayout },
    toolbox: { lg: [] },
    idxs: {a: ['라인차트',  <DemoLineChart/>],b: ['라인차트',  <DemoLineChart/>],c: ['라인차트',  <DemoLineChart/>],d: ['라인차트',  <DemoLineChart/>],e: ['라인차트',  <DemoLineChart/>],}
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
        case "2":
          component = <DemoLineChart />;
          break;
        default:
          component = <DemoLineChart />;
      }
      chartName=l.name
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
          <div class="h-100 ">
            {chartName}
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
    this.props.onLayoutChange(layout, layouts);
    this.setState({ layouts });
  };

  onNewLayout = () => {
    this.setState({
      layouts: { lg: generateLayout() }
    });
  };

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
        <button onClick={this.onNewLayout}>Generate New Layout</button>
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

function generateLayout() {
  return _.map(_.range(0, 25), function(item, i) {
    var y = Math.ceil(Math.random() * 4) + 1;
    return {
      x: (_.random(0, 5) * 2) % 12,
      y: Math.floor(i / 6) * y,
      w: 2,
      h: y,
      i: i.toString(),
      static: Math.random() < 0.05
    };
  });
}

