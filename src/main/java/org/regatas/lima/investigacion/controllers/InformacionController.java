package org.regatas.lima.investigacion.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.regatas.lima.investigacion.entities.Informacion;
import org.regatas.lima.investigacion.services.InformacionService;
import org.springframework.core.io.UrlResource;

@RestController
public class InformacionController {
	
	private static final Logger logger = LoggerFactory.getLogger(InformacionController.class);
	
	@Value("${app.storage.path}")
	private String STORAGEPATH;
	
	@Autowired
	private InformacionService informacionService;
	
	@GetMapping("/info")
	public List<Informacion> informacion() {
		logger.info("call informacion");
		
		List<Informacion> informacion = informacionService.findAll();
		logger.info("informacion: " + informacion);
		
		return informacion;
	}

	
	@GetMapping("/info/images/{filename:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) throws Exception{
		logger.info("call images: " + filename);
		
		Path path = Paths.get(STORAGEPATH).resolve(filename);
		logger.info("Path: " + path);
		
		if(!Files.exists(path)) {
			return ResponseEntity.notFound().build();
		}
		
		Resource resource = new UrlResource(path.toUri());
		logger.info("Resource: " + resource);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+resource.getFilename()+"\"")
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(STORAGEPATH).resolve(filename)))
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
				.body(resource);
	}

	
	@PostMapping("/info")
	public Informacion crear(@RequestParam(name="imagen", required=false) MultipartFile imagen, @RequestParam("dia_if") String dia_if, @RequestParam("area_if") 
							String area_if, @RequestParam("trabajador_if") String trabajador_if,@RequestParam("descripcion_if") String descripcion_if,
							@RequestParam("fecha_ini") String fecha_ini,@RequestParam("fecha_fin") String fecha_fin) throws Exception {
		logger.info("call crear(" + imagen + ", " + area_if + ", " + trabajador_if + ", " + descripcion_if + "," + fecha_ini + "," + fecha_fin + "," + dia_if + ")");
		
		Informacion info = new Informacion();
		info.setDia_if(dia_if);
		info.setArea_if(area_if);
		info.setDescripcion_if(descripcion_if);
		info.setTrabajador_if(trabajador_if);
		info.setFecha_ini(fecha_ini);
		info.setFecha_fin(fecha_fin);
		
		if (imagen != null && !imagen.isEmpty()) {
			String filename = System.currentTimeMillis() + imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
			info.setImagen(filename);
			if(Files.notExists(Paths.get(STORAGEPATH))){
		        Files.createDirectories(Paths.get(STORAGEPATH));
		    }
			Files.copy(imagen.getInputStream(), Paths.get(STORAGEPATH).resolve(filename));
		}
		
		informacionService.save(info);
		
		return info;
	}




}
