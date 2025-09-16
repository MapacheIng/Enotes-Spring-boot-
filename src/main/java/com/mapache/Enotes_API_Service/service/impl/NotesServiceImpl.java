package com.mapache.Enotes_API_Service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.dto.NotesResponse;
import com.mapache.Enotes_API_Service.entity.FileDetails;
import com.mapache.Enotes_API_Service.entity.Notes;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.repository.CategoryRepository;
import com.mapache.Enotes_API_Service.repository.FileRepository;
import com.mapache.Enotes_API_Service.repository.NotesRepository;
import com.mapache.Enotes_API_Service.service.NotesService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;
    @Value("${file.upload.path}")
    private String uploadPath;

    public NotesServiceImpl(NotesRepository notesRepository, ModelMapper mapper, CategoryRepository categoryRepository, FileRepository fileRepository) {
        this.notesRepository = notesRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.fileRepository = fileRepository;
    }

//    @Override
//    public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException {
//        // category validation notes
//        checkCategoryExist(notesDto.getCategory());
//        Notes notes = mapper.map(notesDto, Notes.class);
//        Notes saveNotes = notesRepository.save(notes);
//        return !ObjectUtils.isEmpty(saveNotes);
//    }

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws ResourceNotFoundException, IOException {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);
        // category validation notes
        checkCategoryExist(notesDto.getCategory());
        Notes notesMap = mapper.map(notesDto, Notes.class);
        FileDetails fileDtls = saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDtls)) {
            notesMap.setFileDetails(fileDtls);
        } else  {
            notesMap.setFileDetails(null);
        }

        Notes saveNotes = notesRepository.save(notesMap);
        return !ObjectUtils.isEmpty(saveNotes);
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll()
                .stream()
                .map(notes ->  mapper.map(notes, NotesDto.class))
                .toList();
    }

    @Override
    public byte[] downloadFile(FileDetails fileDtls) throws IOException {
        FileInputStream io = new FileInputStream(fileDtls.getPath());
        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws ResourceNotFoundException {
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer id, Integer pageNo, Integer pageSize) {
        int safePage = (pageNo == null || pageNo < 0) ? 0 : pageNo;
        int safeSize = (pageSize == null || pageSize < 1) ? 10 : Math.min(pageSize, 100);

        Pageable pages = PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.ASC, "id"));

        Page<Notes> pageNotes = notesRepository.findByCreatedBy(id, pages);
        List<NotesDto> notesDto = pageNotes.get()
                .map((element) -> mapper.map(element, NotesDto.class))
                .toList();

        return NotesResponse.builder()
                .notes(notesDto)
                .pageNo(pageNotes.getNumber())
                .pageSize(pageNotes.getSize())
                .totalElements(pageNotes.getTotalElements())
                .totalPages(pageNotes.getTotalPages())
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .build();
    }


    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);

            List<String> ExtensionAllow = Arrays.asList("png", "jpg", "jpeg", "pdf", "xlsx", "docx");
            if (!ExtensionAllow.contains(extension)) {
                throw new IllegalArgumentException("Invalid file format. Only PNG, JPG, JPEG, PDF, XLSX and DOCX are allowed.");
            }
            String rndString = UUID.randomUUID().toString();
            String uploadFileName = rndString + "." + extension;

            File saveFile = new File(this.uploadPath);
            if(!saveFile.exists()){
                saveFile.mkdir();
            }

            String storePath = this.uploadPath.concat(uploadFileName);

            long copy = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (copy != 0) {
                FileDetails fileDetails = new FileDetails();
                fileDetails.setOriginalFileName(originalFilename);
                fileDetails.setDisplayFileName(getDisplayName(originalFilename));
                fileDetails.setFileSize(file.getSize());
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setPath(storePath);
                FileDetails saveFileDtls = fileRepository.save(fileDetails);
                return saveFileDtls;
            }

        }

        return null;
    }

    private String getDisplayName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = FilenameUtils.removeExtension(originalFilename);
        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        return fileName + "." + extension;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws ResourceNotFoundException {
        categoryRepository.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + category.getId()));
    }



}
