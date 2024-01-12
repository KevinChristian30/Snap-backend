package id.kevinchristian.snap.service;

import id.kevinchristian.snap.dto.request.SnapCreateRequestDTO;

public interface SnapService {
    void create(SnapCreateRequestDTO dto);
}
