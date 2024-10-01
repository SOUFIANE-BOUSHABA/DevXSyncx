<%@ include file="shared/_head.jsp" %>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 left-side">
            <h2 class="form-title">Register</h2>
            <form action="register" method="post">
                <div class="mb-3">
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="First Name" required>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Last Name" required>
                </div>
                <div class="mb-3">
                    <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
            </form>
            <a href="login" class="login-link">Login here</a>
        </div>

        <div class="col-md-6 right-side">
        </div>
    </div>
</div>

<link rel="stylesheet" href="assets/css/register.css">
</body>
