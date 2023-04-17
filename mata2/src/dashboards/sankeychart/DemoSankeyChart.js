import React from "react";
import { Sankey, Tooltip } from "recharts";
import DemoSankeyLink from "./DemoSankeyLink";
import DemoSankeyNode from "./DemoSankeyNode";

const data = {
  nodes: [
    { name: "L0" },
    { name: "L1" },
    { name: "L2" },
    { name: "L3" },
    { name: "L4" },
    { name: "R5" },
    { name: "R6" },
    { name: "R7" },
    { name: "R8" },
    { name: "R9" }
  ],
  links: [
    { source: 0, target: 5, value: 30 },
    { source: 1, target: 8, value: 99 },
    { source: 1, target: 7, value: 20 },
    { source: 1, target: 6, value: 15 },
    { source: 4, target: 5, value: 6 },
    { source: 2, target: 8, value: 30 },
    { source: 0, target: 6, value: 15 },
    { source: 2, target: 9, value: 11 },
    { source: 3, target: 9, value: 8 },
    { source: 3, target: 8, value: 23 },
    { source: 2, target: 5, value: 20 }
  ]
};
const colors = ["#F9DB6D", "#40F99B", "#AFC2D5", "#FF8360", "#EC9192"];

function DemoSankeyChart() {
  const numColors = colors.length;

  const colorGradients = data.links.map((link) => {
    return {
      source: colors[link.source % numColors],
      target: colors[link.target % numColors]
    };
  });

  return (
    <div className="sankey-charts">
      <div>
        <pre>Sankey with sorted nodes</pre>
        <Sankey
          width={960}
          height={500}
          margin={{ top: 20, bottom: 20 }}
          data={data}
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
  );
}

export default DemoSankeyChart;
