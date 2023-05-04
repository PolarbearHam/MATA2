import React, { useState, useEffect } from "react";
import GridLayout from "react-grid-layout";
import "react-grid-layout/css/styles.css";
import "react-resizable/css/styles.css";

const Test =()=>{ 
  const [layout, setLayout] = useState([]);

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
    <GridLayout
      className="layout"
      cols={12}
      rowHeight={30}
      width={1200}
      layout={layout}
      onLayoutChange={onLayoutChange}
    >
      {layout.map((item) => (
        <div key={item.i} data-grid={item}>
          <span className="text">{item.i}</span>
        </div>
      ))}
    </GridLayout>
  );
};

export default Test;