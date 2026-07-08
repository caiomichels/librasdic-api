package br.udesc.librasdic_api.controller.sign;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.udesc.librasdic_api.repository.HandRepository;
import br.udesc.librasdic_api.repository.OriginRepository;
import br.udesc.librasdic_api.repository.SignRepository;
import br.udesc.librasdic_api.repository.VariationRepository;
import br.udesc.librasdic_api.repository.WordRepository;
import tools.jackson.databind.ObjectMapper;
import br.udesc.librasdic_api.controller.sign.dto.SignRequest;
import br.udesc.librasdic_api.controller.sign.dto.SignResponse;
import br.udesc.librasdic_api.entity.Hand;
import br.udesc.librasdic_api.entity.Origin;
import br.udesc.librasdic_api.entity.Sign;
import br.udesc.librasdic_api.entity.Variation;
import br.udesc.librasdic_api.entity.Word;

@RestController
@RequestMapping("/api/sign")
public class SignController {

  private final Path videosDir = Paths.get("signs/videos");
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private SignRepository repository;
  @Autowired
  private WordRepository wordRepository;
  @Autowired
  private HandRepository handRepository;
  @Autowired
  private OriginRepository originRepository;
  @Autowired
  private VariationRepository variationRepository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<SignResponse>> getAll() {
    return ResponseEntity.ok(repository.findAll().stream().map(SignResponse::new).toList());
  }

  @GetMapping("/word/{id}")
  public ResponseEntity<Iterable<SignResponse>> getByWord(@PathVariable Long id) {
    Optional<Word> word = wordRepository.findById(id);

    if (word.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    List<SignResponse> body = repository.findByWord(word.get()).stream().map(SignResponse::new).toList();

    return ResponseEntity.ok(body);
  }

  @GetMapping("/word/main/{id}")
  public ResponseEntity<SignResponse> getMainByWord(@PathVariable Long id) {
    Optional<Word> word = wordRepository.findById(id);

    if (word.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Sign sign = word.get().getMainSign();

    if (sign == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(new SignResponse(sign));
  }

  @GetMapping("/video/{filename}")
  public ResponseEntity<Resource> getVideo(@PathVariable String filename) throws Exception {
      Path file = videosDir.resolve(filename).normalize();
      Resource resource = new UrlResource(file.toUri());

      String extension = filename.substring(filename.lastIndexOf('.') + 1);

      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType("vide/" + extension))
              .body(resource);
  }

  @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<SignResponse> add(@RequestParam("sign") String signJson, @RequestParam("video") MultipartFile video) {
    SignRequest request = objectMapper.readValue(signJson, SignRequest.class);

    Optional<Word> word = wordRepository.findById(request.wordId());
    Optional<Origin> origin = originRepository.findById(request.originId());
    Optional<Hand> hand = handRepository.findById(request.handId());

    if (word.isEmpty() || origin.isEmpty() || hand.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Variation variation = null;

    if (request.variation() != null) {
      variation = variationRepository.save(new Variation(request.variation()));
    }

    if (video == null || video.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    try {
      Files.createDirectories(videosDir);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

    String original = video.getOriginalFilename();
    String ext = "";
    if (original != null && original.contains(".")) {
      ext = original.substring(original.lastIndexOf('.'));
    }

    String storedName = UUID.randomUUID() + ext;
    Path target = videosDir.resolve(storedName);

    try {
      Files.copy(video.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

    Sign sign = this.repository.save(new Sign(storedName, word.get(), variation, origin.get(), hand.get()));

    return ResponseEntity.ok(new SignResponse(sign));
  }
}
