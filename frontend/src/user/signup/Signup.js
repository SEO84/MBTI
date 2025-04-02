import React, { useState } from 'react';
import { Link, useLocation, useNavigate, Navigate } from 'react-router-dom';
import { NAVER_AUTH_URL, KAKAO_AUTH_URL, GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, GITHUB_AUTH_URL } from '../../constants';
import { signup } from '../../util/APIUtils';
import { toast } from 'react-toastify';
import './Signup.css';
import fbLogo from '../../img/fb-logo.png';
import googleLogo from '../../img/google-logo.png';
import githubLogo from '../../img/github-logo.png';
import kakaoLogo from '../../img/kakao-logo.png';
import naverLogo from '../../img/naver-logo.png';

const SocialSignup = () => (
    <div className="social-signup">
        <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
            <img src={googleLogo} alt="Google" /> Sign up with Google
        </a>
        <a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
            <img src={fbLogo} alt="Facebook" /> Sign up with Facebook
        </a>
        <a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
            <img src={githubLogo} alt="Github" /> Sign up with Github
        </a>
        <a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
            <img src={kakaoLogo} alt="Kakao" /> Sign up with Kakao
        </a>
        <a className="btn btn-block social-btn naver" href={NAVER_AUTH_URL}>
            <img src={naverLogo} alt="Naver" /> Sign up with Naver
        </a>
    </div>
);

const SignupForm = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();
        const signupRequest = { name, email, password };

        signup(signupRequest)
            .then(response => {
                toast.success("회원가입에 성공하셨습니다.");
                navigate("/login", { replace: true });
            })
            .catch(error => {
                toast.error((error && error.message) || '예기치 않은 문제가 발생하였습니다.');
            });
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className="form-item">
                <input
                    type="text"
                    name="name"
                    className="form-control"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required />
            </div>
            <div className="form-item">
                <input
                    type="email"
                    name="email"
                    className="form-control"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required />
            </div>
            <div className="form-item">
                <input
                    type="password"
                    name="password"
                    className="form-control"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required />
            </div>
            <div className="form-item">
                <button type="submit" className="btn btn-block btn-primary">Sign Up</button>
            </div>
        </form>
    );
};

const Signup = ({ authenticated }) => {
    const location = useLocation();
    if (authenticated) {
        return <Navigate to="/" replace state={{ from: location }} />;
    }

    return (
        <div className="signup-container">
            <div className="signup-content">
                <h1 className="signup-title">Sign Up</h1>
                <SocialSignup />
                <div className="or-separator">
                    <span className="or-text">OR</span>
                </div>
                <SignupForm />
                <span className="login-link">Already have an account? <Link to="/login">Login!</Link></span>
            </div>
        </div>
    );
};

export default Signup;
