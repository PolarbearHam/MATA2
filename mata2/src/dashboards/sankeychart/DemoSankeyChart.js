import React,{useEffect, useState} from "react";
import { Sankey, Tooltip } from "recharts";
import DemoSankeyLink from "./DemoSankeyLink";
import DemoSankeyNode from "./DemoSankeyNode";
import axios from "axios";

// const data = {
//   nodes: [
//     // { name: "L0" },
//     // { name: "L1" },
//     // { name: "L2" },
//     // { name: "L3" },
//     // { name: "L4" },
//     // { name: "R5" },
//     // { name: "R6" },
//     // { name: "R7" },
//     // { name: "R8" },
//     // { name: "R9" }
//   ],
//   links: [
//     // { source: 5, target: 1, value: 30 },
//     // { source: 5, target: 2, value: 99 },
//     // { source: 5, target: 3, value: 20 },
//     // { source: 5, target: 4, value: 15 },
//     // { source: 5, target: 0, value: 18 },
//     // { source: 9, target: 5, value: 30 },
//     // { source: 8, target: 5, value: 99 },
//     // { source: 7, target: 5, value: 20 },
//     // { source: 6, target: 5, value: 33 },
//   ]
// };
const colors = ["#F9DB6D", "#40F99B", "#AFC2D5", "#1F8360", "#EC9192", "#919191", "#ABCDEF", "#1A2B3C", "#9F8E7D", "#3912EF"];

// const [data,setData]=useState({})

export default function DemoSankeyChart() {
  const numColors = colors.length;
  const [sankey_data, setSankeyData] = useState(null);
  const [colorGradients,setColorGradients]=useState('null')
  const [current_url, setCureentUrl] = useState('null')

  useEffect(()=>{
    // const url=`${process.env.REACT_APP_HOST}/v1/analytics/journals_all?basetime=${Date.now()}&projectId=15
    const url=`https://mata2.co.kr/api/v1/analytics/journals_all?basetime=${Date.now()}&projectId=15`
    const headers = {
      "Authorization": `Bearer ${sessionStorage.getItem('accessToken')}`,
      'Content-type': 'application/json',
    }
    axios.get(url,{headers})
      .then(async (res) => {
        // 받은 데이터 정리
        console.log(res.data)
        const result = res.data.reduce((acc, item) => {
          const locationFrom = item.locationFrom;
          const locationTo = item.locationTo;
          const totalJournal = item.totalJournal;

          if (!acc[locationFrom]) {
            acc[locationFrom] = {};
          }
          acc[locationFrom][locationTo] = totalJournal;
          return acc;
        }, {});

        // start node 선택
        let short_url = Object.keys(result)[0]
        Object.keys(result).forEach((key) => {
          if (short_url.length > key.length) {
            short_url = key
          }
        })

        setCureentUrl(short_url)

        // 그림 그릴 준비
        console.log(current_url)
        const nodes = []
        const links = []
        nodes.push({name: current_url})
        let i = 0;
        Object.keys(result[current_url]).forEach((key) => {
          nodes.push({name: key})
          links.push({source: 0, target: ++i, value: result[current_url][key]})
        })

        await setSankeyData({"nodes": nodes, "links": links})
      })
      .then(() => {
        // 내일..........
        console.log(sankey_data)
        const newColorgradient= sankey_data.links.map((link) => {
          return {
            source: colors[link.source.length % numColors],
            target: colors[link.target.length % numColors]
          }
        })
        setColorGradients((newColorgradient))
      })
      .catch((err)=>{
        console.log('sankey-chart fail',err)
      })
  },[])
  useEffect(()=>{}, [])

  return colorGradients && sankey_data ? (
    <div className="sankey-charts">
      <div>
        <pre>Sankey with sorted nodes</pre>
        <Sankey
          width={960}
          height={500}
          margin={{ top: 20, bottom: 20 }}
          data={sankey_data}
          nodeWidth={10}
          nodePadding={60}
          linkCurvature={0.61}
          iterations={0}
          link={<DemoSankeyLink colorGradients={colorGradients} />}
          node={<DemoSankeyNode containerWidth={960} colors={colors} />}
        >
          <Tooltip />
        </Sankey>
      </div>
      <br />
    </div>
  ) : (
    <div>Loading...</div>
  );
}

// export default DemoSankeyChart;
