package br.com.veck.geolocalizationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.veck.geolocalizationservice.repository.GeolocalizationRepository;
import br.com.veck.model.Geolocalization;

@RestController
@RequestMapping("/geolocalization")
public class GeolocalizationController {
	
	@Autowired
	private GeolocalizationRepository geolocalizationRepository;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Geolocalization> create(@RequestBody Geolocalization geolocatlization) {
		try {
			geolocatlization = geolocalizationRepository.save(geolocatlization);
			if (geolocatlization == null) {
				throw new Exception();
			}
			return new ResponseEntity<Geolocalization>(geolocatlization,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Geolocalization>> consultAll() {
		List<Geolocalization> lstGeolocalizations = geolocalizationRepository.findAll();
		if (lstGeolocalizations == null || lstGeolocalizations.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Geolocalization>>(lstGeolocalizations, HttpStatus.OK); 			
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Geolocalization> delete(@PathVariable String id) {
		try {
			Geolocalization customer = geolocalizationRepository.findOneById(id);
			if (customer == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			geolocalizationRepository.deleteById(id);
			return new ResponseEntity<Geolocalization>(HttpStatus.OK);			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
