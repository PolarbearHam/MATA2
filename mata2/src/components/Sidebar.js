import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Nav, NavItem, NavLink, Collapse} from 'reactstrap';

const Sidebar = () => {
  return (
    <div>
      <Nav vertical>
          <NavItem>
            <NavLink href="/start">대쉬보드</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="#">서브메뉴3</NavLink>
          </NavItem>
      </Nav>
    </div>
  );
};

export default Sidebar;