// package com.example.demo.servlet;

// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;

// @WebServlet("/status")
// public class SimpleStatusServlet extends HttpServlet {
    
//     @Override
//     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//         resp.setStatus(200);
//         resp.setContentType("text/plain");
//         resp.getWriter().write("OK");
//     }
// }


// // package com.example.demo.servlet;

// // import jakarta.servlet.annotation.WebServlet;
// // import jakarta.servlet.http.HttpServlet;
// // import jakarta.servlet.http.HttpServletRequest;
// // import jakarta.servlet.http.HttpServletResponse;
// // import java.io.IOException;
// // import java.io.PrintWriter;

// // @WebServlet("/status")
// // public class SimpleStatusServlet extends HttpServlet {
    
// //     @Override
// //     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// //         response.setContentType("text/plain");
// //         response.setCharacterEncoding("UTF-8");
        
// //         PrintWriter out = response.getWriter();
// //         out.write("Application is running");
// //         out.flush();
// //     }
// // }