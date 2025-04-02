module.exports = {
    devServer: {
        setupMiddlewares: (middlewares, devServer) => { // 원하는 미들웨어 추가 (예: 요청시 로그 출력) if (devServer?.app) { devServer.app.use((req, res, next) => { console.log("Custom middleware - 요청 URL:", req.url); next(); }); }

            // 추가로 onAfterSetupMiddleware에 해당하는 작업이 있다면 여기서 실행
            console.log("Custom: setupMiddlewares 적용 완료");

            return middlewares;
        }
    }
};