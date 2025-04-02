import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';

import AppHeader from '../common/AppHeader';
import Home from '../home/Home';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import OAuth2RedirectHandler from '../user/oauth2/OAuth2RedirectHandler';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../constants';
import PrivateRoute from '../common/PrivateRoute';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './App.css';

const App = () => {
  const [authenticated, setAuthenticated] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadCurrentlyLoggedInUser();
  }, []);

  const loadCurrentlyLoggedInUser = () => {
    getCurrentUser()
      .then(response => {
        setCurrentUser(response);
        setAuthenticated(true);
        setLoading(false);
      })
      .catch(error => {
        console.error("Error loading user:", error);
        setLoading(false);
      });
  };

  const handleLogout = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
    setAuthenticated(false);
    setCurrentUser(null);
    toast.success("로그아웃 했습니다.");
  };

  if (loading) {
    return <LoadingIndicator />;
  }

  return (
    <div className="app">
      <div className="app-top-box">
        <AppHeader authenticated={authenticated} onLogout={handleLogout} />
      </div>
      <div className="app-body">
        <h1>Welcome to the App</h1>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route
            path="/profile"
            element={
              <PrivateRoute authenticated={authenticated}>
                <Profile currentUser={currentUser} />
              </PrivateRoute>
            }
          />
          <Route path="/login" element={<Login authenticated={authenticated} />} />
          <Route path="/signup" element={<Signup authenticated={authenticated} />} />
          <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
};

export default App;
