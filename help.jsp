<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help Center</title>
    <link rel="stylesheet" href="helpStyle.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
        <div class="header-content">
            <h1>We Are Here To Help</h1>
              </div>
             <section class="search-section">
        <input type="text" placeholder="Search Here" class="search-bar">
    </section>
    <div class="phone-box">
        <div class="phone-icon"><i class="fa fa-phone" id="icon-phone"></i></div>
        <h4 class="phone">PHONE NUMBER</h4>
    <p class="ph">888-0123-4567(Toll Free)<br>
           222-5697-9087<br>
    </p>
    </div>
    <div class="email-box">
        <div class="email-icon"><i class="fa fa-envelope" id="icon-email"></i></div>
        <h4 class="email">EMAIL ID</h4>
    <p class="em">homefornow@gmail.com
    </p>
    </div>
    <div class="instagram-box">
        <div class="instagram-icon"><i class="fa-brands fa-instagram" id="icon-instagram"></i></div>
        <h4 class="insta">INSTAGRAM</h4>
    <p class="in">@homefornow__
    </p>
    </div>
    <div class="address-box">
        <div class="address-icon"><i class="fa fa-map-marker" id="icon-address"></i></div>
        <h4 class="address">ADDRESS</h4>
    <p class="add">EM 413 SALTLAKE, KOLKATA-95
    </p>
    </div>
</body>
</html>
<jsp:include page="footer.jsp"/>