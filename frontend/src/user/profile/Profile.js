import React from 'react';
import './Profile.css';

const Profile = ({ currentUser }) => {
    if (!currentUser) {
        return <div>Loading profile...</div>;
    }
    return (
        <div className="profile-container">
            <div className="container">
                <div className="profile-info">
                    <div className="profile-avatar">
                        {currentUser.information && currentUser.information.imageUrl ? (
                            <img src={currentUser.information.imageUrl} alt={currentUser.information.name} />
                        ) : (
                            <div className="text-avatar">
                                <span>{currentUser.information && currentUser.information.name ? currentUser.information.name[0] : ''}</span>
                            </div>
                        )}
                    </div>
                    <div className="profile-name">
                        <h2>{currentUser.information ? currentUser.information.name : ''}</h2>
                        <p className="profile-email">{currentUser.information ? currentUser.information.email : ''}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Profile;
