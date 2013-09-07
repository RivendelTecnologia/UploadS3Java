package br.com.rivendel.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class UploadDisco
 */
public class UploadDisco extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadDisco() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// nada a fazer, só temos o upload de arquivo no formulário
				} else {
					// Process form file field (input type="file").
					InputStream conteudoArquivo = item.getInputStream();
					File arquivo = new File(item.getName() + System.currentTimeMillis());
					FileOutputStream fos = new FileOutputStream(arquivo);
					int read = 0;
					byte[] bytes = new byte[1024];
					int contador = 0;

					while ((read = conteudoArquivo.read(bytes)) != -1) {
						fos.write(bytes, 0, read);
						contador++;
						System.out.println("escrevendo " + contador);
					}
					System.out.println("Pronto! Arquivo: " + arquivo.getAbsolutePath());
					conteudoArquivo.close();
					fos.flush();
					fos.close();
					response.getWriter().println("Arquivo escrito: " + arquivo.getAbsolutePath());
				}
			}
		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		} finally{
			
		}

	}

}
