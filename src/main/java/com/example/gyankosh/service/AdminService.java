package com.example.gyankosh.service;

import com.example.gyankosh.dto.AdminDto;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
 AdminDto registerAdmin(AdminDto adminDto);
}
