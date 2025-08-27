<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg fixed-top" 
     style="background: linear-gradient(90deg, #6a11cb, #2575fc);">
    <div class="container-fluid">
        <!-- Brand -->
        <a class="navbar-brand text-white fw-bold" href="#">Leave Management</a>

        <div class="d-flex">
            <!-- Show only after login -->
            <c:if test="${not empty user}">
                <span class="navbar-text text-white me-3">
                    Welcome, ${user.fullname}
                </span>
                <a href="LogoutController" class="btn btn-light ">Logout</a>
            </c:if>
        </div>
    </div>
</nav>

<!-- Add spacing so content is not hidden under fixed navbar -->
<div style="margin-top: 50px;"></div>
