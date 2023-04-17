import React from 'react';
import backgroundImage from '../assets/Welcome.jpg'

const Welcome = () => {
  const styles = {
    backgroundImage: `url(${backgroundImage})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    width: '100%',
    height: '500px',
  };

    return (
      <div style={styles}>
        <div className="center-text">
          <div  >MATA</div>
          <div>메인입니다.</div>
        </div>

        <div>
          <div>첫 설명입니다.</div>
          <div>두 번째 설명입니다.</div>
        </div>
      </div>
    );
};

export default Welcome;