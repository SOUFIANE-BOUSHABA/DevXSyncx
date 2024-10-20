<%@ include file="_head.jsp" %>
<style>
  .navbar {
    z-index: 100;
    background-color: white;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    position: sticky;
    top: 0;
    height: 70px;
  }

  .navbar-nav {
    flex-direction: row;
    justify-content: center;
    width: 100%;
  }

  .navbar-brand {
    margin-right: auto;
  }

  .profile-link {
    margin-left: auto;
  }

  .nav-link {
    color: #000;
  }

  .nav-item form {
    display: inline;
  }

  .nav-item form button {
    background: none;
    border: none;
    color: #000;
    padding: 0;
    font: inherit;
    cursor: pointer;
  }

  .nav-item form button:hover {
    text-decoration: underline;
  }

</style>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container">
    <a class="navbar-brand" href="/">DevSync</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav mx-auto">
        <li class="nav-item active">
          <a class="nav-link" href="projects">Projects</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="users">Users</a>
        </li>
        <li class="nav-item">
          <form action="tasks" method="get">
            <button type="submit" class="nav-link mt-2">Tasks</button>
          </form>
        </li>

      </ul>
      <a class="nav-link profile-link" href="profile">Profile</a>
      <form action="logout" method="post">
        <button type="submit" class="btn btn-dark mt-2">Logout</button>
      </form>
    </div>
  </div>
</nav>