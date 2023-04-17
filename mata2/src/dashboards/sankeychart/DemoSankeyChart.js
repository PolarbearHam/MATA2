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
    { source: 5, target: 1, value: 30 },
    { source: 5, target: 2, value: 99 },
    { source: 5, target: 3, value: 20 },
    { source: 5, target: 4, value: 15 },
    { source: 5, target: 0, value: 18 },
    { source: 9, target: 5, value: 30 },
    { source: 8, target: 5, value: 99 },
    { source: 7, target: 5, value: 20 },
    { source: 6, target: 5, value: 33 },
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
