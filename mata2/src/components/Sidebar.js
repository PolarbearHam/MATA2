import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Nav, NavItem, NavLink, Collapse} from 'reactstrap';

const Sidebar = () => {
  const [isOpen, setIsOpen] = useState(true);

  const toggle = () => setIsOpen(!isOpen);

  return (
    <div>
      <Nav vertical>
        <NavItem onClick={toggle}>
          <NavLink>메뉴</NavLink>
        </NavItem>
        <Collapse isOpen={isOpen}>
          <NavItem>
            <NavLink href="/start">대쉬보드</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="#">서브메뉴3</NavLink>
          </NavItem>
        </Collapse>
      </Nav>
    </div>
  );
};

export default Sidebar;