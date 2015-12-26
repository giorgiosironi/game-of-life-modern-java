package com.giorgiosironi.gameoflife;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.giorgiosironi.gameoflife.domain.Cell;
import com.giorgiosironi.gameoflife.domain.Generation;
import com.giorgiosironi.gameoflife.view.GenerationWindow;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GameOfLifeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			PrintWriter out = response.getWriter();

			Generation current = Generation.withAliveCells(
				Cell.onXAndY(1, 1),
				Cell.onXAndY(1, 2),
				Cell.onXAndY(2, 1),
				Cell.onXAndY(2, 2),
				Cell.onXAndY(7, 1),
				Cell.onXAndY(7, 2),
				Cell.onXAndY(7, 3),
				Cell.onXAndY(7, 8)
			);
			
			String requestedGeneration = request.getParameter("generation");
			if (requestedGeneration != null) {
				for (int i = 1; i <= Integer.parseInt(requestedGeneration); i++) {
					current = current.evolve();
				}
			}
			GenerationWindow generationTable = new GenerationWindow(current);
			Configuration cfg = new Configuration();
			// TODO: template path, where it should be and how to refer to it?
            Template template = cfg.getTemplate("src/main/java/generation.ftl");
            // TODO: build a good data model out of Generation and GenerationTable class
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("generation", generationTable);
            template.process(data, out);
			
		} catch (IOException e) {
			// TODO: logging
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
