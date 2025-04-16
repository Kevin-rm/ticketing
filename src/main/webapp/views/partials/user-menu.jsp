<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
       aria-expanded="false">
        <c:set var="user" value="${auth:currentUser()}"/>
        <c:out value="${user.fullName()}"/>
    </a>
    <ul class="dropdown-menu dropdown-menu-end">
        <li>
            <form action="<c:url value="/deconnexion"/>" method="post">
                <button type="submit" class="dropdown-item">Déconnexion</button>
            </form>
        </li>
    </ul>
</li>
