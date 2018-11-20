<%@ page contentType="text/html; charset=EUC-KR" language="java" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="contents">
    <div class="intro">
        <div class="introBG"></div>
        <div class="introExplain">
            <div class="introExplainWrap">
                <div class="title">
                    <h1><spring:message code="title.name"/></h1>
                    <h2>;<spring:message code="title.subtitle"/></h2>
                </div>
                <div class="explain">
                    <%--WEB VIEW--%>
                    <p class="explain_web-p"><spring:message code="explain.p1"/></p>
                    <p class="explain_web-p"><spring:message code="explain.p2"/></p>
                    <p class="explain_web-p"><spring:message code="explain.p3"/><br/><br/></p>
                    <%--MOBILE VIEW--%>
                    <p class="explain_mobile-p"><spring:message code="explain.mobile.p1"/></p>
                    <p class="explain_mobile-p"><spring:message code="explain.mobile.p2"/><br/><br/></p>

                </div>
                <div class="download">
                    <a href="" onclick="temp()"><img src="img/div1_down_android.png" alt="android">&nbsp&nbsp&nbsp</a>
                    <a href="" onclick="temp()"> <img src="img/div1_down_ios.svg" alt="ios"></a>
                </div>
            </div>
        </div>
        <div class="introImg">
            <div></div>
        </div>
    </div>

    <div class="platformIntro">
        <div class="platformIntroBG"></div>
        <div class="platformExplain">
            <div class="platformExplainWrap">
                <p class="platformExplain_web-p"><spring:message code="platform.title1"/></p>

                <p class="counter">125000</p> <!--애니메이션 효과주기-->

                <p class="platformExplain_web-p"><spring:message code="platform.title2"/></p>

                <p class="platformExplain_web-p">
                    <h2><spring:message code="platform.subtitle"/><br/></h2>
                </p>
                <div class="platformExplain_grid">
                    <div class="platformExplain_web-statistics"><p class="statistics">32%</p><p><spring:message code="platform.problem.language"/></p></div>
                    <div class="platformExplain_web-statistics"><p class="statistics">21%</p><p><spring:message code="platform.problem.hospital"/></p></div>
                    <div class="platformExplain_web-statistics"><p class="statistics">17%</p><p><spring:message code="platform.problem.daily"/></p></div>
                    <div class="platformExplain_web-statistics"><p class="statistics">12%</p><p><spring:message code="platform.problem.culture"/></p></div>
                    <div class="platformExplain_web-statistics"><p class="statistics">10%</p><p><spring:message code="platform.problem.accomodation"/></p></div>
                    <div class="platformExplain_web-statistics"><p class="statistics">05%</p><p><spring:message code="platform.problem.job"/></p></div>
                </div>
                <h2><spring:message code="platform.slogan"/></h2>
                <%--<h2>site.title : <spring:message code="site.title"/></h2>--%>
            </div>
        </div>
    </div>
    <div class="keyword">
        <div class="keywordBG"></div>
        <!--Web -->
        <div class="keywordWrap">
            <div class="keywordWeb">
                <h1>KEYWORDS</h1>
                <div class="funcgrid">
                    <div><p><img src="img/div3_trade.png"></p><p class="keyword-p"><spring:message code="keyword.trade"/></p></div>
                    <div><p><img src="img/div3_language.png"></p><p class="keyword-p"><spring:message code="keyword.language"/></p></div>
                    <div><p><img src="img/div3_hospital.png"></p><p class="keyword-p"><spring:message code="keyword.hospital"/></p></div>
                    <div><p><img src="img/div3_culture.png"></p><p class="keyword-p"><spring:message code="keyword.culture"/></p></div>
                    <div><p><img src="img/div3_tip.png"></p><p class="keyword-p"><spring:message code="keyword.livingtip"/></p></div>
                </div>
                <h2><spring:message code="keyword.subtitle"/></h2>
            </div>
            <!--Mobile-->
            <div class="keywordMobile">
                <div class="funcgrid">
                    <div><h1>KEYWORDS</h1></div>
                    <div><p><img src="img/div3_trade.png"></p><p class="keyword-p"><spring:message code="keyword.trade"/></p></div>
                    <div><p><img src="img/div3_language.png"></p><p class="keyword-p"><spring:message code="keyword.language"/></p></div>
                    <div><p><img src="img/div3_hospital.png"></p><p class="keyword-p"><spring:message code="keyword.hospital"/></p></div>
                    <div><p><img src="img/div3_culture.png"></p><p class="keyword-p"><spring:message code="keyword.culture"/></p></div>
                    <div><p><img src="img/div3_tip.png"></p><p class="keyword-p"><spring:message code="keyword.livingtip"/></p></div>
                </div>
                <h2><spring:message code="keyword.subtitle"/></h2>
            </div>
        </div>
    </div>
    <%--<div class="cooperation">--%>
        <%--<div class="cooperationBG"></div>--%>
        <%--<div class="cooperationWrap">--%>
            <%--TEST--%>
        <%--</div>--%>
    <%--</div>--%>
</div>
