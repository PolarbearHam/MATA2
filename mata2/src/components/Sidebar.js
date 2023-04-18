import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Nav, NavItem, NavLink, Collapse} from 'reactstrap';
import './Sidebar.css';
import itemIcon from '../assets/item-project-icon.svg';
import serviceAddIcon from '../assets/Service_add_icon.svg';
const Sidebar = () => {
  const services=['services1','services2','services3']
  return (
    <div id='SidebarBackground'>
      <Nav vertical id='Sidebar'>
        {services.map((service) => (
          <NavItem className='NavItemButton'>
            <img src={itemIcon} className='itemIcon '/>  <NavLink href="/start" className='nav-link'>{service}</NavLink>
          </NavItem>))}

          <NavItem className='NavItemButton'>
            <NavLink href="/start">TestService</NavLink>
          </NavItem>
          <NavItem className='NavItemButton'>
            <img src={serviceAddIcon} className='itemIcon'/>  <NavLink href="#" className='nav-link'>서비스추가</NavLink>
          </NavItem>
      </Nav>
    </div>
  );
};

export default Sidebar;