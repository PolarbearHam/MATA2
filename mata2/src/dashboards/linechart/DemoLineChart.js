import React, { PureComponent } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import axios from 'axios';
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
    };
  }
  static demoUrl = 'https://codesandbox.io/s/simple-line-chart-kec3v';
  componentDidMount(){
    const url=`http://70.12.246.60:8080/api/v1/analytics/components?basetime=${Date.now()}&interval=1m&projectId=1`
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
      const timestampByscreenDeviceArray = Object.values(Object.entries(timestampByscreenDevice).map(([timestamp, values]) => {
        return {timestamp,...values};
      }));
      this.setState(timestampByscreenDeviceArray)
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
          data={data}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="pv" stroke="#8884d8" activeDot={{ r: 8 }} />
          <Line type="monotone" dataKey="uv" stroke="#82ca9d" />
        </LineChart>
      </ResponsiveContainer>
    );
  };
}
