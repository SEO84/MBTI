import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import './AppHeader.css';

const AppHeader = ({ authenticated, onLogout }) => {
    return (
        <header className="app-header">
            <div className="container">
                <div className="app-branding">
                    <Link to="/" className="app-title">Sample OAuth2 & JWT</Link>
                </div>
                <div className="app-options">
                    <nav className="app-nav">
                        {authenticated ? (
                            <ul>
                                <li>
                                    <NavLink to="/profile" className={({ isActive }) => (isActive ? 'active' : '')}>
                                        Profile
                                    </NavLink>
                                </li>
                                <li>
                                    <button onClick={onLogout} className="logout-btn" style={{ cursor: 'pointer' }}>
                                        Logout
                                    </button>
                                </li>
                            </ul>
                        ) : (
                            <ul>
                                <li>
                                    <NavLink to="/login" className={({ isActive }) => (isActive ? 'active' : '')}>
                                        Sign In
                                    </NavLink>
                                </li>
                                <li>
                                    <NavLink to="/signup" className={({ isActive }) => (isActive ? 'active' : '')}>
                                        Sign Up
                                    </NavLink>
                                </li>
                            </ul>
                        )}
                    </nav>
                </div>
            </div>
        </header>
    );
};

export default AppHeader;
