import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const PrivateRoute = ({ authenticated, children }) => {
  const location = useLocation();
  return authenticated ? children : <Navigate to="/login" state={{ from: location }} replace />;
};

export default PrivateRoute;
