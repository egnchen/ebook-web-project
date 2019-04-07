package com.eyek.ebook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

@WebServlet(
        name = "bookList",
        urlPatterns = { "/bookList" })
public class BookListServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            FileReader fr = new FileReader(new File(getClass().getResource("bookData.json").toURI()), Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(fr);
            String buf;
            StringBuilder builder = new StringBuilder();
            while((buf = reader.readLine()) != null)
                builder.append(buf);
            out.print(builder.toString());
        } catch(Exception e) {
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
