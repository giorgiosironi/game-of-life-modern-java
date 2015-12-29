package com.giorgiosironi.gameoflife;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giorgiosironi.gameoflife.domain.Cell;
import com.giorgiosironi.gameoflife.domain.Generation;
import com.giorgiosironi.gameoflife.view.GenerationWindow;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GameOfLifeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		Logger logger = LoggerFactory.getLogger(GameOfLifeServlet.class);
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
			
			String requestedGenerationParameter = request.getParameter("generation");
			int requestedGeneration;
			if (requestedGenerationParameter != null) {
				requestedGeneration = Integer.parseInt(requestedGenerationParameter);
				for (int i = 1; i <= requestedGeneration; i++) {
					current = current.evolve();
				}
			} else {
				requestedGeneration = 0;
			}
			
			GenerationWindow generationWindow = new GenerationWindow(current);
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
			cfg.setClassForTemplateLoading(this.getClass(), "/templates");
			Template template = cfg.getTemplate("generation.ftl");
            
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("generation", generationWindow);
            data.put("generation_index", requestedGeneration);
            template.process(data, out);
			
		} catch (IOException e) {
			logger.error("Template not found", e);
		} catch (TemplateException e) {
			logger.error("Template population problem", e);
		}
	}
}
