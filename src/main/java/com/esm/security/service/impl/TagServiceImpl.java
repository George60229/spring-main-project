package com.esm.security.service.impl;



import com.esm.security.converter.TagConverter;
import com.esm.security.dto.request.TagRequestDTO;
import com.esm.security.dto.response.TagResponseDTO;
import com.esm.security.exception.AppNotFoundException;
import com.esm.security.exception.ErrorCode;
import com.esm.security.model.Tag;
import com.esm.security.repository.TagRepository;
import com.esm.security.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TagServiceImpl implements TagService {


    @Autowired
    private TagRepository tagRepository;



    @Autowired
    TagConverter converter;


    @Override
    public void setConverter(TagConverter tagConverter) {
        converter=tagConverter;
    }

    @Override
    public TagResponseDTO createTag(TagRequestDTO tagDTO) {
        return converter.convertOneToDTO(tagRepository.save(converter.convertDTOtoModel(tagDTO)));
    }

    @Override
    public Page<TagResponseDTO> getAllTags(Pageable pageable) {
        return converter.convert(tagRepository.findAll(pageable));
    }

    @Override
    public void deleteById(int id) {
        tagRepository.deleteById(id);
    }

    @Override
    public TagResponseDTO getById(int id) {
        Optional<Tag> myTag = tagRepository.findById(id);
        if (myTag.isEmpty()) {
            throw new AppNotFoundException("Tag with this id " + id + " is not found", ErrorCode.TAG_NOT_FOUND);
        }
        return converter.convertOneToDTO(myTag.get());
    }

}
