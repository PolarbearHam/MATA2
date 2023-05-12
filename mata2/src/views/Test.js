import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import DropdownComponent from "../components/DropdownComponent"
const Test = () => {
  const [isOpen, setIsOpen] = React.useState(false);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <>
    <Dropdown isOpen={isOpen} toggle={toggleMenu}>
      <DropdownToggle caret>
        메뉴
      </DropdownToggle>

      <DropdownMenu>
        <DropdownItem>항목 1</DropdownItem>
        <DropdownItem>항목 2</DropdownItem>
        <DropdownItem>항목 3</DropdownItem>
      </DropdownMenu>
    </Dropdown>
    <DropdownComponent menus={['m1','m2']}/>
    </>
  );
};

export default Test;
