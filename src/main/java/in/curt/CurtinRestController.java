package in.curt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
public class CurtinRestController {

    private final URLEntityRepository urlEntityRepository;

    @Autowired
    public CurtinRestController(URLEntityRepository urlEntityRepository) {
        this.urlEntityRepository = urlEntityRepository;
    }

    @PostMapping()
    public ResponseEntity<Object> shorten(@RequestBody ShortenURLRequest request) {
        URLEntity url = urlEntityRepository.save(new URLEntity(request.getUrl()));

        return ResponseEntity
                .created(URI.create(fromCurrentRequestUri().toUriString() + url.getId()))
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOriginalURL(@PathVariable Integer id) {
        Optional<URLEntity> url = urlEntityRepository.findById(id);

        return url.map(urlEntity -> ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .header(HttpHeaders.LOCATION, urlEntity.getUrl().toString())
                .build())
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
