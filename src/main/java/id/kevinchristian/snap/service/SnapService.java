package id.kevinchristian.snap.service;

import id.kevinchristian.snap.dto.request.SnapCreateRequestDTO;
import id.kevinchristian.snap.dto.request.SnapUpdateRequestDTO;

public interface SnapService {
    void create(SnapCreateRequestDTO dto);

    void update(String snapId, SnapUpdateRequestDTO dto);
}
