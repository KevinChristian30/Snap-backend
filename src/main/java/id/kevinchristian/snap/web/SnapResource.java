package id.kevinchristian.snap.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.kevinchristian.snap.dto.request.SnapCreateRequestDTO;
import id.kevinchristian.snap.dto.request.SnapUpdateRequestDTO;
import id.kevinchristian.snap.service.SnapService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SnapResource {
    private final SnapService snapService;

    @PostMapping("/v1/snaps")
    public ResponseEntity<Void> create(@Valid @RequestBody SnapCreateRequestDTO dto) {
        snapService.create(dto);
        return ResponseEntity.created(URI.create("/v1/snaps")).build();
    }

    @PatchMapping("/v1/snaps/{snapId}")
    public ResponseEntity<Void> update(@PathVariable("snapId") String snapId,
            @Valid @RequestBody SnapUpdateRequestDTO dto) {
        snapService.update(snapId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/snaps/{snapId}")
    public ResponseEntity<Void> delete(@PathVariable("snapId") String snapId) {
        snapService.delete(snapId);
        return ResponseEntity.ok().build();
    }
}
