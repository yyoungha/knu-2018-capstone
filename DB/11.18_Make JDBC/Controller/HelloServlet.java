package Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/calc.do")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HelloServlet(){
        // doGet() : get 방식으로 호출시 호출되는 메서드
        /*
         * HttpServletRequest : request 객체 -> 사용자의 요청 정보를 알아낼 수 있다.
         * HttpServletResponse : response 객체 -> 응답에 관련된 객체이다.
         */
        System.out.println("Hello servlet 객체 생성");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=EUC-KR");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        int a=10;
        int b=5;
        String view = "../../TEST.jsp";
        request.setAttribute("su1", a);
        request.setAttribute("su2", b);
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=EUC-KR");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        int a=0;
        int b=0;
        a=  Integer.parseInt(request.getParameter("su1"))  ;
        b=  Integer.parseInt(request.getParameter("su2"))  ;
        out.print("곱하기 : " +a*b+"<br>");
        out.print("나누기 : " +a/b+"<br>");
        out.print("더하기 : " + (a+b) +"<br>");
        out.print("빼기 : " + (a-b) +"<br>");
    }
}

