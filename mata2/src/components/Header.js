import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const Header = () => {
  return (
    <nav>
      <ul class="nav flex-column">
        <li class="nav-item">
          <a class="nav-link active" href="#">홈</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">프로필</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">메시지</a>
        </li>
      </ul>
    </nav>
  );
};

export default Header;