package br.udesc.librasdic_api.controller.word;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.udesc.librasdic_api.repository.GramaticalClassRepository;
import br.udesc.librasdic_api.repository.SignRepository;
import br.udesc.librasdic_api.repository.SubjectRepository;
import br.udesc.librasdic_api.repository.WordRepository;
import tools.jackson.databind.ObjectMapper;
import br.udesc.librasdic_api.controller.sign.dto.SignResponse;
import br.udesc.librasdic_api.controller.word.dto.WordRequest;
import br.udesc.librasdic_api.controller.word.dto.WordResponse;
import br.udesc.librasdic_api.controller.word.dto.WordSignRequest;
import br.udesc.librasdic_api.controller.word.dto.WordSimpleResponse;
import br.udesc.librasdic_api.entity.GramaticalClass;
import br.udesc.librasdic_api.entity.Sign;
import br.udesc.librasdic_api.entity.Subject;
import br.udesc.librasdic_api.entity.Word;

@RestController
@RequestMapping("/api/word")
public class WordController {

  private final Path imagesDir = Paths.get("words/images");
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private WordRepository repository;
  @Autowired
  private SignRepository signRepository;
  @Autowired
  private SubjectRepository subjectRepository;
  @Autowired
  private GramaticalClassRepository classRepository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<WordResponse>> getAll() {
    return ResponseEntity.ok(repository.findAll().stream().map(WordResponse::new).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<WordResponse> get(@PathVariable Long id) {
    Optional<Word> word = repository.findById(id);

    if (word.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(new WordResponse(word.get()));
  }

  @GetMapping("/alphabetic/{letter}")
  public ResponseEntity<Iterable<WordSimpleResponse>> getSimpleAlphabetic(@PathVariable Character letter) {
    return ResponseEntity.ok(repository.findByWordStartingWithIgnoreCase(letter.toString()));
  }

  @GetMapping("/subject/{text}")
  public ResponseEntity<Iterable<WordSimpleResponse>> searchSubject(@PathVariable String text) {
    return ResponseEntity.ok(repository.findBySubject_NameContainingIgnoreCase(text));
  }

  @GetMapping("/search/{text}")
  public ResponseEntity<Iterable<WordSimpleResponse>> search(@PathVariable String text) {
    return ResponseEntity.ok(repository.findByWordContainingIgnoreCase(text));
  }

  @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<WordResponse> add(@RequestParam("word") String wordJson, @RequestParam("image") MultipartFile image) {
    WordRequest request = objectMapper.readValue(wordJson, WordRequest.class);

    Optional<Subject> subject = subjectRepository.findById(request.subjectId());
    Optional<GramaticalClass> gramaticalClass = classRepository.findById(request.gramClassId());

    if (subject.isEmpty() || gramaticalClass.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if (image == null || image.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    try {
      Files.createDirectories(imagesDir);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

    String original = image.getOriginalFilename();
    String ext = "";
    if (original != null && original.contains(".")) {
      ext = original.substring(original.lastIndexOf('.'));
    }

    String storedName = UUID.randomUUID() + ext;
    Path target = imagesDir.resolve(storedName);

    try {
      Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

    Word word = repository.save(new Word(request.word(), request.description(), request.example(), request.librasExample(), storedName, subject.get(), gramaticalClass.get()));

    return ResponseEntity.ok(new WordResponse(word));
  }

  @PostMapping("/set/main")
  public ResponseEntity<WordResponse> setMainSign(@RequestBody WordSignRequest request) {

    Optional<Word> word = repository.findById(request.wordId());
    Optional<Sign> sign = signRepository.findById(request.signId());

    if (word.isEmpty() || sign.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if (sign.get().getWord().getId() != word.get().getId()) {
      return ResponseEntity.badRequest().build();
    }

    Word wordS = word.get();

    wordS.setMainSign(sign.get());

    repository.save(wordS);

    return ResponseEntity.ok().build();
  }
/*

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TokenService tokenService;
  
  @GetMapping("/all")
  public ResponseEntity<Iterable<NoteResponse>> getAll() {
    List<Note> notes = noteRepository.findAll();
    List<NoteResponse> notesResponse = notes.stream().map(NoteResponse::new).toList();

    return ResponseEntity.ok(notesResponse);
  }

  @GetMapping("/mine")
  public ResponseEntity<Iterable<NoteResponse>> getMine(@RequestHeader("Authorization") String header) {
    String token = tokenService.recoverToken(header);
    String email = tokenService.validateToken(token);

    if (userRepository.findByEmail(email).get().getRole() == UserRole.ADMIN)
      return getAll();

    Optional<User> user = userRepository.findByEmail(email);
    if (user.isEmpty())
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    List<NoteResponse> notesResponse = noteRepository.findByUser(user.get()).stream().map(NoteResponse::new).toList();

    return ResponseEntity.ok(notesResponse);
  }

  @GetMapping("/one")
  public ResponseEntity<NoteResponse> getOne(@RequestParam Long id, @RequestHeader("Authorization") String header) {
    Optional<Note> note = noteRepository.findById(id);
    if (note.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    String token = tokenService.recoverToken(header);
    String email = tokenService.validateToken(token);

    if (note.get().getUser().getEmail().equals(email) || userRepository.findByEmail(email).get().getRole() == UserRole.ADMIN)
      return ResponseEntity.ok(new NoteResponse(note.get()));

    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping("/new")
  public ResponseEntity<String> addNew(@RequestBody NoteRequest body, @RequestHeader("Authorization") String header) {
    String token = tokenService.recoverToken(header);
    String email = tokenService.validateToken(token);

    Optional<User> user = userRepository.findByEmail(email);
    if (user.isEmpty())
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Note note = new Note();

    note.setTitle(body.title());
    note.setContent(body.content());
    note.setUser(user.get());

    noteRepository.save(note);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> delete(@RequestParam Long id, @RequestHeader("Authorization") String header) {
    Optional<Note> note = noteRepository.findById(id);
    if (note.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    String token = tokenService.recoverToken(header);
    String email = tokenService.validateToken(token);

    if (note.get().getUser().getEmail().equals(email) || userRepository.findByEmail(email).get().getRole() == UserRole.ADMIN) {
      noteRepository.delete(note.get());
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PutMapping("/update")
  public ResponseEntity<String> update(@RequestParam Long id, @RequestBody UpdateNoteRequest body, @RequestHeader("Authorization") String header) {
    Optional<Note> note = noteRepository.findById(id);
    if (note.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    String token = tokenService.recoverToken(header);
    String email = tokenService.validateToken(token);

    if (note.get().getUser().getEmail().equals(email) || userRepository.findByEmail(email).get().getRole() == UserRole.ADMIN) {
      Note updatedNote = note.get();
      updatedNote.setTitle(body.title());
      updatedNote.setContent(body.content());
      noteRepository.save(updatedNote);
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
*/
}
