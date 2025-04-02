import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, Navigate } from 'react-router-dom';
import { NAVER_AUTH_URL, KAKAO_AUTH_URL, GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, GITHUB_AUTH_URL } from '../../constants';
import { login } from '../../util/APIUtils';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../../constants';
import { toast } from 'react-toastify';
import './Login.css';
import fbLogo from '../../img/fb-logo.png';
import googleLogo from '../../img/google-logo.png';
import githubLogo from '../../img/github-logo.png';
import kakaoLogo from '../../img/kakao-logo.png';
import naverLogo from '../../img/naver-logo.png';

const SocialLogin = () => (
    <div className="social-login">
        <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
            <img src={googleLogo} alt="Google" /> Sign in with Google
        </a>
        <a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
            <img src={fbLogo} alt="Facebook" /> Sign in with Facebook
        </a>
        <a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
            <img src={githubLogo} alt="Github" /> Sign in with Github
        </a>
        <a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
            <img src={kakaoLogo} alt="Kakao" /> Sign in with Kakao
        </a>
        <a className="btn btn-block social-btn naver" href={NAVER_AUTH_URL}>
            <img src={naverLogo} alt="Naver" /> Sign in with Naver
        </a>
    </div>
);

const LoginForm = ({ onLoginSuccess }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();
        const loginRequest = { email, password };

        login(loginRequest)
            .then(response => {
                localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                localStorage.setItem(REFRESH_TOKEN, response.refreshToken);
                toast.success("로그인에 성공하였습니다.");
                onLoginSuccess();
                navigate("/", { replace: true });
            })
            .catch(error => {
                toast.error((error && error.message) || '로그인에 실패하였습니다.');
            });
    };

    return (
        <form onSubmit={handleSubmit}>
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
                <button type="submit" className="btn btn-block btn-primary">Login</button>
            </div>
        </form>
    );
};

const Login = ({ authenticated }) => {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if (location.state && location.state.error) {
            toast.error(location.state.error, { autoClose: 5000 });
            navigate(-1);
        }
    }, [location, navigate]);

    if (authenticated) {
        return <Navigate to="/" replace state={{ from: location }} />;
    }

    return (
        <div className="login-container">
            <div className="login-content">
                <h1 className="login-title">Sign In</h1>
                <SocialLogin />
                <div className="or-separator">
                    <span className="or-text">OR</span>
                </div>
                <LoginForm onLoginSuccess={() => { }} />
                <span className="signup-link">New user? <Link to="/signup">Sign up!</Link></span>
            </div>
        </div>
    );
};

export default Login;
