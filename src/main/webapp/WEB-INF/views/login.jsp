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

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function getQueryParams() {
        const params = {};
        window.location.search.substring(1).split("&").forEach(pair => {
            const [key, value] = pair.split("=");
            params[key] = decodeURIComponent(value);
        });
        return params;
    }

    const params = getQueryParams();
    if (params.registered === "true") {
        const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
            }
        });
        Toast.fire({
            icon: "success",
            title: "Your account has been created successfully. Now you can login."
        });
    }
</script>
</body>