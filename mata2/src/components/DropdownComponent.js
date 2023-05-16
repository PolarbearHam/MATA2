import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

const DropdownComponent = (props) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };
  const handleItemClick = (item) => {
    
    setSelectedItem(item);
    props.onSelect(item);
  };
  return (
    <Dropdown isOpen={isOpen} toggle={toggleMenu}>
      <DropdownToggle caret>
        {props.menuName}
      </DropdownToggle>

      <DropdownMenu>
      {props.menus.map((item, index) => (
          <DropdownItem key={index} onClick={() => handleItemClick(item)}>{item}</DropdownItem>
        ))}
      </DropdownMenu>
    </Dropdown>
  );
};

export default DropdownComponent;