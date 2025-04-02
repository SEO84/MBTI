import React from 'react';
import './LoadingIndicator.css';

const LoadingIndicator = () => {
    return (
        <div className="loading-indicator" style={{ display: 'block', textAlign: 'center', marginTop: '30px' }}>
            Loading...
        </div>
    );
};

export default LoadingIndicator;
