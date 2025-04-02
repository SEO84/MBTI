import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../../constants';

const OAuth2RedirectHandler = () => {
    const location = useLocation();

    const getUrlParameter = (name) => {
        name = name.replace(/[\\[]/, '\\[').replace(/[\]]/, '\\]');
        const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        const results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };

    const token = getUrlParameter('token');
    const error = getUrlParameter('error');

    if (token) {
        localStorage.setItem(ACCESS_TOKEN, token);
        localStorage.setItem(REFRESH_TOKEN, null);
        return <Navigate to="/profile" replace state={{ from: location }} />;
    } else {
        return <Navigate to="/login" replace state={{ from: location, error }} />;
    }
};

export default OAuth2RedirectHandler;
