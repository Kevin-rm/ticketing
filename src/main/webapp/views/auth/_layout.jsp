<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/base">
    <layout:put block="body">
        <div class="container-fluid min-vh-100 d-flex align-items-center justify-content-center bg-light py-5">
            <div class="card shadow-sm" style="max-width: 400px; width: 100%;">
                <div class="card-body p-4">
                    <layout:block name="form-title"/>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success d-flex align-items-center" role="alert">
                            <i class="bi bi-check-circle-fill me-2"></i>
                            <div>${success}</div>
                        </div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>
                            <div>${error}</div>
                        </div>
                    </c:if>

                    <layout:block name="form-content"/>
                </div>
            </div>
        </div>
    </layout:put>

    <layout:put block="scripts">
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const togglePasswordButton = document.getElementById("toggle-password");
                const passwordInput        = document.getElementById("password");
                const icon                 = togglePasswordButton.querySelector("i");

                togglePasswordButton.addEventListener("click", () => {
                    const showPassword = passwordInput.type === "password";
                    passwordInput.type = showPassword ? "text" : "password";
                    icon.className = `bi bi-eye\${showPassword ? "" : "-slash"}`;
                });
            });
        </script>
    </layout:put>
</layout:extends>
