package br.udesc.librasdic_api.controller.subject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.udesc.librasdic_api.repository.SubjectRepository;
import br.udesc.librasdic_api.entity.Subject;

@RestController
@RequestMapping("/subject")
public class SubjectController {

  @Autowired
  private SubjectRepository repository;

  @GetMapping("/all")
  public ResponseEntity<Iterable<Subject>> getAll() {
    return ResponseEntity.ok(repository.findAll());
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
