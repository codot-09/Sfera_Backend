package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.request.GroupRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.GroupResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface GroupService {
    ApiResponse<String> openGroup(MultipartFile file, GroupRequest request) throws IOException;
    ApiResponse<String> closeGroup(UUID groupId);
    ApiResponse<List<GroupResponse>> getAll();
    ApiResponse<GroupResponse> getById(UUID id);
}
