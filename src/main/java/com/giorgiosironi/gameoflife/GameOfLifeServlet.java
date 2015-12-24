package com.giorgiosironi.gameoflife;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.giorgiosironi.gameoflife.domain.Cell;
import com.giorgiosironi.gameoflife.domain.Generation;
import com.giorgiosironi.gameoflife.view.GenerationTable;

public class GameOfLifeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			PrintWriter out = response.getWriter();
			out.println("<h1>Game Of Life</h1>");
			out.println("<style>td { border: 1px solid black; width: 30px; height: 30px; } </style>");

			out.println(new GenerationTable(Generation.withAliveCells(Cell.onXAndY(1, 1), Cell.onXAndY(1, 2))));
		} catch (IOException e) {
			// TODO: logging
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
