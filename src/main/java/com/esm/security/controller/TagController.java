package com.esm.security.controller;



import com.esm.security.dto.request.TagRequestDTO;
import com.esm.security.dto.response.TagResponseDTO;
import com.esm.security.exception.BadRequestException;
import com.esm.security.exception.ErrorCode;
import com.esm.security.service.TagService;
import com.esm.security.urlCreator.TagUrlCreator;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tag")
@CrossOrigin(origins = "http://localhost:3000")
public class TagController {
    @Autowired
    TagUrlCreator tagUrlCreator;


@Autowired
    private TagService tagServiceImpl;




    @GetMapping("/getTagById/{id}")
    public CollectionModel<TagResponseDTO> getTagByID(@PathVariable(value = "id") int id) {
        List<TagResponseDTO> list = new ArrayList<>();

        list.add(tagServiceImpl.getById(id));

        List<Link> links = new ArrayList<>();
        links.add(tagUrlCreator.getAllTags());


        return CollectionModel.of(list, links);
    }


    @RolesAllowed("ADMIN")
    @PostMapping("/addTag")
    public CollectionModel<TagResponseDTO> addTag(@RequestBody TagRequestDTO tag) {
        List<TagResponseDTO> list = new ArrayList<>();

        list.add(tagServiceImpl.createTag(tag));

        List<Link> links = new ArrayList<>();
        links.add(tagUrlCreator.getAllTags());
        links.add(tagUrlCreator.getTag(list.get(0).getId()));


        return CollectionModel.of(list, links);
    }

    @GetMapping("/getAllTags")
    public CollectionModel<TagResponseDTO> getAllTags(@PageableDefault Pageable pageable) {


        Page<TagResponseDTO> tags = tagServiceImpl.getAllTags(pageable);

        List<Link> links = new ArrayList<>();
        links.add(tagUrlCreator.getAllTags());


        return CollectionModel.of(tags, links);
    }

    @GetMapping("/getAllTags/{page}")
    public CollectionModel<TagResponseDTO> getAllTagsWithPage(@PathVariable(value = "page") int page) {
        if (page <= 0) {
            throw new BadRequestException("Page must be positive", ErrorCode.BAD_REQUEST_ERROR);
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<TagResponseDTO> list = tagServiceImpl.getAllTags(pageable);
        List<Link> links = new ArrayList<>();


        return CollectionModel.of(list, links);
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTagById(@PathVariable(value = "id") int id) {
        tagServiceImpl.deleteById(id);
    }


}
