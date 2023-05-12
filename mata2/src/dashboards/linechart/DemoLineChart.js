import React, { PureComponent } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, Brush } from 'recharts';
import axios from 'axios';
import { Dropdown } from 'reactstrap';
const data = [
  {
    name: 'Page A',
    uv: 4000,
    pv: 2400,
    amt: 2400,
  },
  {
    name: 'Page B',
    uv: 3000,
    pv: 1398,
    amt: 2210,
  },
  {
    name: 'Page C',
    uv: 2000,
    pv: 9800,
    amt: 2290,
  },
  {
    name: 'Page D',
    uv: 2780,
    pv: 3908,
    amt: 2000,
  },
  {
    name: 'Page E',
    uv: 1890,
    pv: 4800,
    amt: 2181,
  },
  {
    name: 'Page F',
    uv: 2390,
    pv: 3800,
    amt: 2500,
  },
  {
    name: 'Page G',
    uv: 3490,
    pv: 4300,
    amt: 2100,
  },
];

export default class DemoLineChart extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data:[]
    };
    this.handleBrushMouseDown = this.handleBrushMouseDown.bind(this);
    this.handleBrushMouseMove = this.handleBrushMouseMove.bind(this);
    this.handleBrushMouseUp = this.handleBrushMouseUp.bind(this);
    
  }
  
  handleBrushMouseDown(e) {
    e.stopPropagation(); // React Grid Layout의 드래그 이벤트 중지
  }

  handleBrushMouseMove(e) {
    e.stopPropagation(); // React Grid Layout의 드래그 이벤트 중지
  }

  handleBrushMouseUp(e) {
    e.stopPropagation(); // React Grid Layout의 드래그 이벤트 중지
  }
  static demoUrl = 'https://codesandbox.io/s/simple-line-chart-kec3v';
  componentDidMount(){
    const url=`http://70.12.246.60:8080/api/v1/analytics/components?basetime=${Date.now()}&interval=1m&projectId=15`
    const headers = {
      "Authorization": `Bearer ${sessionStorage.getItem('accessToken')}`,
      'Content-type': 'application/json',
    }
    axios.get(url,{headers})
    .then((res)=>{
      
      
      console.log('꺾은선 스테이트',this.state)
      const timestampByscreenDevice = {};
      for (let i = 0; i < res.data.length; i++) {
        const el = res.data[i];
        if (!timestampByscreenDevice[el.updateTimestamp]) {
          timestampByscreenDevice[el.updateTimestamp] = {};
        }
        if (!timestampByscreenDevice[el.updateTimestamp][el.screenDevice]) {
          timestampByscreenDevice[el.updateTimestamp][el.screenDevice] = 0;
        } timestampByscreenDevice[el.updateTimestamp][el.screenDevice] += el.totalClick;
      }
      
      const timestampByscreenDeviceObject = Object.entries(timestampByscreenDevice).map(([timestamp, values]) => {
        return {timestamp:new Date( parseInt(timestamp)),...values};
      });
      const timestampByscreenDeviceArray=Object.values(timestampByscreenDeviceObject)
      const sortedData = timestampByscreenDeviceArray.sort((a, b) => {
        const timestampA = Date.parse(a.timestamp);
        const timestampB = Date.parse(b.timestamp);
        return timestampA - timestampB;
      });
      this.setState({
        data:sortedData
      })
      console.log('꺾은선 넣을 데이터',this.state)
  })
    .catch((err)=>{
      console.log('꺾은선 데이터 실패',err)
    })
    
  }
  render() {
    return (
      <ResponsiveContainer width="100%" height="100%">
   
        <LineChart
          width={500}
          height={300}
          data={this.state.data? this.state.data :data }
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="timestamp" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="tablet" stroke="#8884d8" activeDot={{ r: 8 }} />
          <Line type="monotone" dataKey="phone" stroke="#82ca9d" />
          <Line type="monotone" dataKey="Desktop" stroke="#82ca9d" />
          <Brush dataKey="timestamp" height={30} stroke="#8884d8" 
            onMouseDown={this.handleBrushMouseDown}
            onMouseMove={this.handleBrushMouseMove}
            onMouseUp={this.handleBrushMouseUp}/>
        </LineChart>
      </ResponsiveContainer>
      
    );
  };
}
