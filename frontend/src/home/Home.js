import React from 'react';
import './Home.css';
import springReactImage from '../img/spring-boot-and-react-js.png';

const Home = () => {
    return (
        <div className="home-container">
            <div className="container">
                <img className="home-image" src={springReactImage} alt="Spring Boot and React" />
                <h1 className="home-title">Sample OAuth2 & JWT</h1>
                <span className="home-footer">Copyright © 2022 Mizzle Inc.</span>
            </div>
        </div>
    );
};

export default Home;
