package br.udesc.librasdic_api.controller.hand;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.udesc.librasdic_api.controller.hand.dto.HandResponse;
import br.udesc.librasdic_api.repository.HandRepository;

@RestController
@RequestMapping("/api/hand")
public class HandController {

  private final Path imagesDir = Paths.get("hands/images");

  @Autowired
  private HandRepository repository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<HandResponse>> getAll() {
    return ResponseEntity.ok(repository.findAll().stream().map(HandResponse::new).toList());
  }

  @GetMapping("/image/{filename}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) throws Exception {
      Path file = imagesDir.resolve(filename).normalize();
      Resource resource = new UrlResource(file.toUri());

      String extension = filename.substring(filename.lastIndexOf('.') + 1);

      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType("image/" + extension))
              .body(resource);
  }
}
