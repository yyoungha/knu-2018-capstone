<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: hyunwook
  Date: 2018-11-04
  Time: 오후 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=EUC-KR" language="java" pageEncoding="EUC-KR" %>
<html>
<head>
    <%--Viewport--%>
    <meta name="viewport" content="width=device-width, inital-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
    <link rel="icon" href="img/favicon.ico" type="image/x-icon" />
    <style>
        <%-- FONT --%>
        @import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR');
        <%@include file="../resources/css/webstyle.css"%>
    </style>
    <title>KNUEARTH - THE LITTLE EARTH ON YOUR HAND</title>
        <!-- load JQuery -->
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"
                integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
                crossorigin="anonymous">
        </script>
        <script>
            var agent = navigator.userAgent.toLowerCase();
            if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
                alert("이 홈페이지는 크롬에 최적화 되어있습니다. 크롬을 권장합니다.");
            }
        </script>
        <!-- Count Up -->
        <script>
            jQuery(document).ready(function( $ ) {
                var e = $(".counter");
                var finalNum = e.text();
                e.text("0");

                setTimeout(function() {
                    $({ val : 0 }).animate({ val : finalNum}, {
                        duration: 2000,
                        step: function() {
                            e.text(Math.floor(this.val));
                        },
                        complete: function() {
                            e.text(Math.floor(this.val));
                        }
                    });
                }, 2000);
            });
        </script>
        <!-- fix header-->
        <script>
            var headerFlag = 1;
            $(window).scroll(function(){
                if($(document).scrollTop() > 0) { //스크롤을 내리면
                    if(headerFlag == 1) {
                        headerFlag = 0;
                        $('#header').slideUp(); //하양 슬라이드를 올림
                        $('#fixHeader').slideDown(); //빨강 슬라이드를 내림
                    }
                } else { //최상위 이면
                    if(headerFlag == 0) {
                        headerFlag = 1; // flag 1로 설정
                        $('#header').slideDown();  //하양 슬라이드를 내림
                        $('#fixHeader').slideUp(); //빨강 슬라이드를 올림
                    }
                }
            });
        </script>
        <!-- scroll up -->
        <script>
            $(function() {
                $(window).scroll(function() {
                    if ($(this).scrollTop() > 0) {
                        $('#fixHeader').fadeIn();
                    } else {
                        $('#fixHeader').fadeOut();
                    }
                });

                $("#logoBtn").click(function() {
                    $('html, body').animate({
                        scrollTop : 0
                    }, 400);
                    return false;
                });
            });
        </script>

        <!-- TEMP -->
        <script>
            function temp() {
                alert("준비중")
            }
        </script>
</head>
<body>

<header id="fixHeader" title="Top Button">
    <div class="header-wrap">
        <div class="header-logo">
            <div id="logoBtn"><a href="/"></a></div>
            <%--<img id="logo" src="<spring:url value='../img/header_logo-white.png'/>"/>--%>
        </div>

        <div class="header-nav">
            <ul>
                <li><a href="/?lang=kr" >한국어</a></li>
                <li><a href="/?lang=en">ENGLISH</a></li>
            </ul>
        </div>
    </div>
</header>

<header id="header">
    <div class="header-wrap">
        <div class="header-logo">
            <a href="#"></a>
            <%--<img id="logo" src="<spring:url value='../img/header_logo-white.png'/>"/>--%>
        </div>

        <div class="header-nav">
            <ul>
                <li><a href="/?lang=kr">한국어</a></li>
                <li><a href="/?lang=en">ENGLISH</a></li>
            </ul>
        </div>
    </div>
</header>
<div id="wrap">