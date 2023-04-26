import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Nav, NavItem, NavLink, Collapse} from 'reactstrap';
import './Sidebar.css';
import itemIcon from '../assets/item-project-icon.svg';
import serviceAddIcon from '../assets/Service_add_icon.svg';
import {Link} from "react-router-dom";

const Sidebar = (props) => {
  const services=[...props.state.serviceList]
  console.log('사이드바 목록:',services)
  return (
    <div id='sidebar-container'>
      <Nav vertical id='sidebar'>
        {services.map((service) => (
          <NavItem className='nav-item-button'>
            <div className='nav-item-icon'>
              <svg version="1.1" id="Layer_1" x="0px" y="0px" width="18px" viewBox="0 0 980 912">
                  <path fill="#010101" opacity="1.000000" stroke="none" d=" M38.562500,1.000000   C41.113857,1.609572 43.222790,2.236887 45.342365,2.825874   C60.479969,7.032299 70.695702,20.141779 71.028854,35.825821   C71.078384,38.157932 71.057236,40.491692 71.057251,42.824688   C71.058357,296.630554 71.057426,550.436401 71.060440,804.242249   C71.060654,822.137573 79.477257,835.410461 94.695290,840.270447   C99.646957,841.851807 105.153931,842.247559 110.407448,842.250427   C286.221710,842.347229 462.035980,842.330566 637.850281,842.330627   C739.672607,842.330688 841.494934,842.319641 943.317261,842.340698   C962.695435,842.344727 976.407715,852.941833 980.641479,870.870300   C981.000000,875.356873 981.000000,879.713806 980.652100,884.640747   C976.359253,898.614563 968.121033,907.762146 954.340698,911.433044   C952.548523,911.910522 950.779663,912.475586 949.000000,913.000000   C948.555542,913.000000 948.111084,913.000000 947.150269,912.620483   C944.644592,912.161621 942.655273,912.013000 940.665955,912.012817   C650.418762,911.985840 360.171570,911.968140 69.924400,911.995911   C68.616234,911.996033 67.308128,912.650940 66.000000,913.000000   C65.555557,913.000000 65.111107,913.000000 64.193741,912.630737   C30.746351,906.797058 10.072900,888.041626 2.224248,855.394287   C2.020566,854.547058 1.417053,853.795959 0.999999,853.000000   C1.000000,851.285645 1.000000,849.571289 1.322374,847.162292   C1.763296,844.988342 1.985619,843.509033 1.985710,842.029724   C2.002213,573.555847 2.004566,305.081970 1.962684,36.608109   C1.962444,35.072021 1.334709,33.536034 1.000000,32.000000   C1.000000,31.555555 1.000000,31.111111 1.336086,30.181259   C5.696224,14.364648 15.187705,4.812292 30.994963,1.898407   C31.372355,1.828839 31.666843,1.309536 32.000000,1.000000   C34.041668,1.000000 36.083332,1.000000 38.562500,1.000000  z"/>
                  <path fill="#FDFDFD" opacity="1.000000" stroke="none" d=" M0.999999,853.468628   C1.417053,853.795959 2.020566,854.547058 2.224248,855.394287   C10.072900,888.041626 30.746351,906.797058 63.860409,912.630737   C43.074966,913.000000 22.149931,913.000000 1.000000,913.000000   C1.000000,893.312988 1.000000,873.625183 0.999999,853.468628  z"/>
                  <path fill="#777777" opacity="1.000000" stroke="none" d=" M66.462639,913.000000   C67.308128,912.650940 68.616234,911.996033 69.924400,911.995911   C360.171570,911.968140 650.418762,911.985840 940.665955,912.012817   C942.655273,912.013000 944.644592,912.161621 946.816956,912.620483   C653.641785,913.000000 360.283508,913.000000 66.462639,913.000000  z"/>
                  <path fill="#1A1A1A" opacity="1.000000" stroke="none" d=" M1.000000,32.466984   C1.334709,33.536034 1.962444,35.072021 1.962684,36.608109   C2.004566,305.081970 2.002213,573.555847 1.985710,842.029724   C1.985619,843.509033 1.763296,844.988342 1.322374,846.733826   C1.000000,575.644653 1.000000,304.289307 1.000000,32.466984  z"/>
                  <path fill="#FAFAFA" opacity="1.000000" stroke="none" d=" M31.531342,1.000000   C31.666843,1.309536 31.372355,1.828839 30.994963,1.898407   C15.187705,4.812292 5.696224,14.364648 1.336086,29.847927   C1.000000,20.404383 1.000000,10.808764 1.000000,1.000000   C11.019889,1.000000 21.041286,1.000000 31.531342,1.000000  z"/>
                  <path fill="#FAFAFA" opacity="1.000000" stroke="none" d=" M949.468628,913.000000   C950.779663,912.475586 952.548523,911.910522 954.340698,911.433044   C968.121033,907.762146 976.359253,898.614563 980.652100,885.105469   C981.000000,894.264709 981.000000,903.529480 981.000000,913.000000   C970.646667,913.000000 960.291992,913.000000 949.468628,913.000000  z"/>
                  <path fill="#010101" opacity="1.000000" stroke="none" d=" M801.388184,465.388153   C770.385803,496.390381 739.640381,527.149536 708.878418,557.892212   C691.530334,575.229431 670.521484,575.185730 653.122375,557.781921   C613.067444,517.716125 573.020569,477.642151 532.987000,437.555023   C531.705261,436.271545 530.604187,434.807648 529.196228,433.168579   C527.258179,435.014740 525.825623,436.322540 524.455383,437.692657   C440.322876,521.814209 356.199524,605.944824 272.063904,690.063110   C257.980072,704.144043 239.439087,706.234497 224.655518,695.480408   C208.392914,683.650330 205.398956,660.693420 218.120407,645.113647   C219.486420,643.440735 221.013382,641.890747 222.542923,640.360901   C315.145386,547.742615 407.789337,455.165771 500.322021,362.477875   C511.514008,351.267090 524.446655,346.301147 539.654846,351.232788   C545.693787,353.191040 551.588989,357.282867 556.162292,361.806000   C596.561218,401.761108 636.649658,442.030243 676.819031,482.217377   C678.104980,483.503845 679.274780,484.906525 680.752136,486.534241   C682.400696,484.999817 683.732483,483.835571 684.980713,482.587646   C739.658142,427.923645 794.319702,373.243713 849.005920,318.588470   C860.601013,306.999939 874.970276,303.699341 888.698914,309.326538   C910.931824,318.439514 917.553955,347.033905 901.587036,365.039795   C900.262085,366.534027 898.832397,367.937531 897.419189,369.351044   C865.494934,401.282837 833.566101,433.210083 801.388184,465.388153  z"/>
              </svg>
            </div>
            <NavLink href="/start" className='nav-link'>{service.name}</NavLink>
            <NavLink href={`/service/${service.id}/setting`} className='nav-item-settings'>
              <svg fill="#000000" width="18px" version="1.1" id="Capa_1" viewBox="0 0 478.703 478.703">
                <g>
                  <g>
                    <path d="M454.2,189.101l-33.6-5.7c-3.5-11.3-8-22.2-13.5-32.6l19.8-27.7c8.4-11.8,7.1-27.9-3.2-38.1l-29.8-29.8    c-5.6-5.6-13-8.7-20.9-8.7c-6.2,0-12.1,1.9-17.1,5.5l-27.8,19.8c-10.8-5.7-22.1-10.4-33.8-13.9l-5.6-33.2    c-2.4-14.3-14.7-24.7-29.2-24.7h-42.1c-14.5,0-26.8,10.4-29.2,24.7l-5.8,34c-11.2,3.5-22.1,8.1-32.5,13.7l-27.5-19.8    c-5-3.6-11-5.5-17.2-5.5c-7.9,0-15.4,3.1-20.9,8.7l-29.9,29.8c-10.2,10.2-11.6,26.3-3.2,38.1l20,28.1    c-5.5,10.5-9.9,21.4-13.3,32.7l-33.2,5.6c-14.3,2.4-24.7,14.7-24.7,29.2v42.1c0,14.5,10.4,26.8,24.7,29.2l34,5.8    c3.5,11.2,8.1,22.1,13.7,32.5l-19.7,27.4c-8.4,11.8-7.1,27.9,3.2,38.1l29.8,29.8c5.6,5.6,13,8.7,20.9,8.7c6.2,0,12.1-1.9,17.1-5.5    l28.1-20c10.1,5.3,20.7,9.6,31.6,13l5.6,33.6c2.4,14.3,14.7,24.7,29.2,24.7h42.2c14.5,0,26.8-10.4,29.2-24.7l5.7-33.6    c11.3-3.5,22.2-8,32.6-13.5l27.7,19.8c5,3.6,11,5.5,17.2,5.5l0,0c7.9,0,15.3-3.1,20.9-8.7l29.8-29.8c10.2-10.2,11.6-26.3,3.2-38.1    l-19.8-27.8c5.5-10.5,10.1-21.4,13.5-32.6l33.6-5.6c14.3-2.4,24.7-14.7,24.7-29.2v-42.1    C478.9,203.801,468.5,191.501,454.2,189.101z M451.9,260.401c0,1.3-0.9,2.4-2.2,2.6l-42,7c-5.3,0.9-9.5,4.8-10.8,9.9    c-3.8,14.7-9.6,28.8-17.4,41.9c-2.7,4.6-2.5,10.3,0.6,14.7l24.7,34.8c0.7,1,0.6,2.5-0.3,3.4l-29.8,29.8c-0.7,0.7-1.4,0.8-1.9,0.8    c-0.6,0-1.1-0.2-1.5-0.5l-34.7-24.7c-4.3-3.1-10.1-3.3-14.7-0.6c-13.1,7.8-27.2,13.6-41.9,17.4c-5.2,1.3-9.1,5.6-9.9,10.8l-7.1,42    c-0.2,1.3-1.3,2.2-2.6,2.2h-42.1c-1.3,0-2.4-0.9-2.6-2.2l-7-42c-0.9-5.3-4.8-9.5-9.9-10.8c-14.3-3.7-28.1-9.4-41-16.8    c-2.1-1.2-4.5-1.8-6.8-1.8c-2.7,0-5.5,0.8-7.8,2.5l-35,24.9c-0.5,0.3-1,0.5-1.5,0.5c-0.4,0-1.2-0.1-1.9-0.8l-29.8-29.8    c-0.9-0.9-1-2.3-0.3-3.4l24.6-34.5c3.1-4.4,3.3-10.2,0.6-14.8c-7.8-13-13.8-27.1-17.6-41.8c-1.4-5.1-5.6-9-10.8-9.9l-42.3-7.2    c-1.3-0.2-2.2-1.3-2.2-2.6v-42.1c0-1.3,0.9-2.4,2.2-2.6l41.7-7c5.3-0.9,9.6-4.8,10.9-10c3.7-14.7,9.4-28.9,17.1-42    c2.7-4.6,2.4-10.3-0.7-14.6l-24.9-35c-0.7-1-0.6-2.5,0.3-3.4l29.8-29.8c0.7-0.7,1.4-0.8,1.9-0.8c0.6,0,1.1,0.2,1.5,0.5l34.5,24.6    c4.4,3.1,10.2,3.3,14.8,0.6c13-7.8,27.1-13.8,41.8-17.6c5.1-1.4,9-5.6,9.9-10.8l7.2-42.3c0.2-1.3,1.3-2.2,2.6-2.2h42.1    c1.3,0,2.4,0.9,2.6,2.2l7,41.7c0.9,5.3,4.8,9.6,10,10.9c15.1,3.8,29.5,9.7,42.9,17.6c4.6,2.7,10.3,2.5,14.7-0.6l34.5-24.8    c0.5-0.3,1-0.5,1.5-0.5c0.4,0,1.2,0.1,1.9,0.8l29.8,29.8c0.9,0.9,1,2.3,0.3,3.4l-24.7,34.7c-3.1,4.3-3.3,10.1-0.6,14.7    c7.8,13.1,13.6,27.2,17.4,41.9c1.3,5.2,5.6,9.1,10.8,9.9l42,7.1c1.3,0.2,2.2,1.3,2.2,2.6v42.1H451.9z"/>
                    <path d="M239.4,136.001c-57,0-103.3,46.3-103.3,103.3s46.3,103.3,103.3,103.3s103.3-46.3,103.3-103.3S296.4,136.001,239.4,136.001    z M239.4,315.601c-42.1,0-76.3-34.2-76.3-76.3s34.2-76.3,76.3-76.3s76.3,34.2,76.3,76.3S281.5,315.601,239.4,315.601z"/>
                  </g>
                </g>
              </svg>
            </NavLink>
          </NavItem>))}
          <NavItem className='nav-item-button'>
            <div className='nav-item-icon'>
              <svg width="18px" viewBox="0 -0.5 21 21">
                <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                  <g id="Dribbble-Light-Preview" transform="translate(-179.000000, -600.000000)" fill="#000000">
                    <g id="icons" transform="translate(56.000000, 160.000000)">
                      <path d="M137.7,450 C137.7,450.552 137.2296,451 136.65,451 L134.55,451 L134.55,453 C134.55,453.552 134.0796,454 133.5,454 C132.9204,454 132.45,453.552 132.45,453 L132.45,451 L130.35,451 C129.7704,451 129.3,450.552 129.3,450 C129.3,449.448 129.7704,449 130.35,449 L132.45,449 L132.45,447 C132.45,446.448 132.9204,446 133.5,446 C134.0796,446 134.55,446.448 134.55,447 L134.55,449 L136.65,449 C137.2296,449 137.7,449.448 137.7,450 M133.5,458 C128.86845,458 125.1,454.411 125.1,450 C125.1,445.589 128.86845,442 133.5,442 C138.13155,442 141.9,445.589 141.9,450 C141.9,454.411 138.13155,458 133.5,458 M133.5,440 C127.70085,440 123,444.477 123,450 C123,455.523 127.70085,460 133.5,460 C139.29915,460 144,455.523 144,450 C144,444.477 139.29915,440 133.5,440" id="plus_circle-[#1427]">
                      </path>
                    </g>
                  </g>
                </g>
              </svg>
            </div>
            <NavLink href="/service-add" className='nav-link'>서비스 추가</NavLink>
          </NavItem>
      </Nav>
    </div>
  );
};

export default Sidebar;