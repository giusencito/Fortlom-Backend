package upc.edu.pe.FortlomBackend.backend.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import upc.edu.pe.FortlomBackend.backend.domain.model.entity.Publication;
import org.springframework.http.ResponseEntity;
import java.util.List;


public interface PublicationService {

    List<Publication> getAll();
    Page<Publication> getAll(Pageable pageable);
    Publication getById(Long publicationId);
    Publication create(Long artistId, Publication publication);
    Publication update(Long publicationId, Publication request);
    List<Publication> getPublicationByArtistId(Long artistId);
    ResponseEntity<?> delete(Long publicationId);
}