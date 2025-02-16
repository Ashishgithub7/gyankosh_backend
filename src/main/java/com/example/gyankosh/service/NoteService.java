package com.example.gyankosh.service;

import com.example.gyankosh.Entity.Note;
import com.example.gyankosh.Repository.NoteRepository;
import com.example.gyankosh.dto.NoteDto;
import com.example.gyankosh.dto.NoteResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NoteRepository noteRepository;
    @Value("${pdfNotes.base-url}")
    private String baseUrl;
    private NoteResponseDto response;

    public NoteDto setPdfFile(NoteDto noteDto) throws IOException {
        Note note=this.modelMapper.map(noteDto,Note.class);  //map garyo but note ma string auda multipart axa
        String pdfNotes=saveFile(noteDto.getPdfNotes());   //so string ma convert garyo savefile le file lai directory ma save garera string return gardenxa
        if(pdfNotes==null)
        {
            System.out.println("File upload failed, pdfNotes is null");

        }
        else {
            note.setPdfNotes(pdfNotes);
            System.out.println("pdf Notes saved:"+ pdfNotes);
        }
       Note newnote = noteRepository.save(note); //ball note ko Pdfnotes ma string ayesi repo ma gayera database ma save vo
         NoteDto response = this.modelMapper.map(newnote, NoteDto.class); //new note lai feri map gareama teha arko string var xa tesai sanga map hunxa mulipart sanga hudaina

          if(newnote.getPdfNotes()!=null)
          {
              response.setPdfNotesUrl(baseUrl+ "note/file/"+ newnote.getPdfNotes()); //save garesi repsonsse ma auxa
          }
         return  response;
    }
    public NoteResponseDto getNotesById(Long id) {
        Optional<Note> note=this.noteRepository.findById(id);
        if(note.isPresent())
        {
            Note note1=note.get();
            System.out.println(note1.getPdfNotes());
          response = this.modelMapper.map(note1, NoteResponseDto.class);
           if(note1.getPdfNotes()!=null)
           {
               response.setPdfNotesUrl(baseUrl+ "note/file/"+ note1.getPdfNotes()); //save garesi repsonsse ma auxa
               //note1.getPdfNotes le file ko name auxa

           }
           else {
                System.out.println("null occcccccccure");
            }
        }

        return response;

    }


    // Step 1: This method returns the directory path where the files will be uploaded
    public String uploaddir() throws IOException
    {
        //kuna dir or folder ma image upload garna ho
        // Step 2: Define the directory (relative path) where the images will be stored.
        String uploadDir="static/pdfNotes/";
        // Step 3: Create a FileSystemResource object for the specified upload directory.
        // This helps point to the file system location.
        Resource resource=new FileSystemResource(uploadDir);
        // Step 4: Decode the absolute path of the directory to handle any special characters
        // that may be present (like spaces or non-ASCII characters).
        String resourcePath= URLDecoder.decode(resource.getFile().getAbsolutePath(),"UTF-8");
        // Step 5: Replace system-specific file separator (e.g., backslashes on Windows)
        // with a standard forward slash "/" for consistency across different platforms.
        resourcePath=resourcePath.replace(File.separator,"/");
        // Step 6: Create a File object pointing to the decoded directory path.
        File dir=new File(resourcePath);
        // Step 7: If the directory does not exist, create it.
        if(!dir.exists())
        {
            dir.mkdirs(); // Creates the directory and any missing parent directories.
        }
        // Step 8: Return the absolute path to the directory where files will be uploaded.
        return resourcePath;

    }

    // Step 9: Method to save a file in the upload directory and return its name.
    public String saveFile(MultipartFile file) throws IOException
    {
        // Step 10: Get the upload directory where the file should be saved.
        String uploadDir=uploaddir();
        // Step 11: Generate a unique name for the file using the original file's name.
        String fileName = file.getOriginalFilename();
        // Step 12: Combine the directory path and the generated file name to create the full path.
        Path filePath = Paths.get(uploadDir, fileName);
        // Step 13: Copy the file's input stream to the target location in the file system.
        // This ensures that any existing file with the same name is replaced.
        try (InputStream inputStream = file.getInputStream()) {
            //The method copies the file's input stream to the target path, replacing any existing file with the same name (StandardCopyOption.REPLACE_EXISTING).
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        // Step 14: Return the file's  name, which can be stored in a database or used later.
        return fileName;

    }


    // Step 19: Method to determine the media typeof a file based on its extension.
    public MediaType determineMediaType(String fileName) {
        // Step 20: Split the file name to get the file extension.
        String[] parts = fileName.split("\\.");
        String fileExtension = parts[parts.length - 1].toLowerCase();
        // Step 21: Based on the file extension, return the appropriate media type.
        switch (fileExtension) {
            case "pdf":
                return MediaType.APPLICATION_PDF; // For PDF files.
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG; // For JPEG image files.
            case "png":
                return MediaType.IMAGE_PNG; // For PNG image files.
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // For unknown or other file types.
        }
    }


}
