//package com.security.springSecurity.serviceImpl;
//
//import com.security.springSecurity.Entity.Admin;
//import com.security.springSecurity.Repository.AdminRepository;
//import com.security.springSecurity.Repository.UserRepository;
//import com.security.springSecurity.dto.AdminDto;
//import com.security.springSecurity.security.JwtTokenHelper;
//import com.security.springSecurity.service.AdminService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private AdminRepository adminRepository;
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//    @Override
//    //controller ma euta json auxa jaslai object ma convert garera admindto ko object ma rakhenxa ra  admindto  object ma pass garenca
//    // aba hami admindto ko object lai admin entitiy ko object ma store garxau by converting it
//    public AdminDto registerAdmin(AdminDto adminDto) {
//        Admin admin=this.modelMapper.map(adminDto,Admin.class);
//
//        //admin ko password set garda encodinggarera rakhana ko lagi
//        admin.setPassword(this.passwordEncoder().encode(admin.getPassword()));
//        admin.setCreatedTime(LocalDateTime.now());
//
//       return adminRepository.save(admin);
//    }
//}
