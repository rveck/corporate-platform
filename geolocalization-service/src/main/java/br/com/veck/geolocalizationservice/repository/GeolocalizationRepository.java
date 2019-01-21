package br.com.veck.geolocalizationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.veck.model.Geolocalization;

public interface GeolocalizationRepository extends MongoRepository<Geolocalization, String>{
	public Geolocalization findOneById(String id) throws Exception;
}
