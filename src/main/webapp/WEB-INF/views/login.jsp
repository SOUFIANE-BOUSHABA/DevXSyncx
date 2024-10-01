<%@ include file="shared/_head.jsp" %>

<body>
<div class="container">
    <div class="row">
        <div class=" col-md-6 left-side">
            <h2 class="form-title">Login</h2>
            <form action="login" method="post">
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
            <a href="register" class="login-link">Register here</a>
        </div>

        <div class=" col-md-6  right-side">
        </div>
    </div>
</div>

<link rel="stylesheet" href="assets/css/login.css">
</body>
