package com.example.sfera_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String userName;
    private String description;
    private String phoneNumber;
    private String company;
    private String job;
    private String fileUrl;
}
