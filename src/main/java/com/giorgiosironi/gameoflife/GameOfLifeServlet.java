package com.giorgiosironi.gameoflife;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.giorgiosironi.gameoflife.view.GenerationTable;

public class GameOfLifeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			PrintWriter out = response.getWriter();
			out.println("<h1>Game Of Life</h1>");
			out.println(new GenerationTable());
		} catch (IOException e) {
			// TODO: logging
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
