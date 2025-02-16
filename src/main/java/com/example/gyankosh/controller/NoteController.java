package com.example.gyankosh.controller;

import com.example.gyankosh.dto.NoteDto;
import com.example.gyankosh.dto.NoteResponseDto;
import com.example.gyankosh.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController {
//    private final NoteService noteService;
//
//    @Autowired
//    public NoteController(NoteService noteService) {
//        this.noteService = noteService;
//    }
    @Autowired
    private NoteService noteService;

    @PostMapping("/add")
    public NoteDto setPdfFiles(@ModelAttribute NoteDto noteDto) throws IOException {
        return noteService.setPdfFile(noteDto);
    }
    @GetMapping("/get")
    public NoteResponseDto getNotes(@RequestParam Long id)
    {
      NoteResponseDto noteResponseDto= noteService.getNotesById(id);
      return noteResponseDto;
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getProfileDocument(@PathVariable String fileName) throws IOException {
        // Step 1: Get the directory path where files are stored (e.g., static/image).
        String uploadDir = noteService.uploaddir();
        // Step 2: Combine the directory path with the file name to get the full file path.
        Path documentPath = Paths.get(uploadDir, fileName);  // Locate the file in the static/image directory.
        // Step 3: Create a resource object pointing to the file’s URL. The file’s path is converted to a URI.
        Resource resource = new UrlResource(documentPath.toUri());
        // Step 4: Check if the file exists and is readable.
        if (resource.exists() && resource.isReadable()) {
            // Step 5: Determine the file's media type (e.g., image/jpeg, application/pdf) based on the file extension.

            MediaType mediaType = noteService.determineMediaType(fileName);
            // Step 6: Return the file as a response. Set the content type as the file’s media type.

            return ResponseEntity.ok()
                    .contentType(mediaType)// Set the correct content type.
                    .body(resource);  // Attach the file (resource) to the response.
        } else {
            // Step 7: If the file is not found or not readable, return a 404 Not Found response.

            return ResponseEntity.notFound().build();
        }

    }
        @GetMapping("/try")
    public String tryyourself()
    {
        return "welcome to controller";
    }
}
