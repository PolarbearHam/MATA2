import React from 'react';
import backgroundImage from '../assets/Welcome.jpg'
import './Welcome.css'
const Welcome = () => {

    return (
      <div id='background'>
        <div id='heroTitle'>
          <div id='MATA'>MATA</div>
          <div id='main'>메인입니다.</div>
        </div>

        <div id='heroContent'>
          <div id='firstExplanation'>첫 설명입니다.</div>
          <div id='secondExplanation'>두 번째 설명입니다.</div>
        </div>
      </div>
    );
};

export default Welcome;