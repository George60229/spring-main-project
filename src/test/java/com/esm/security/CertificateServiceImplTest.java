package com.esm.security;

import com.esm.security.converter.CertificateConverter;
import com.esm.security.converter.TagConverter;
import com.esm.security.dto.request.CertificateEditRequestDto;
import com.esm.security.dto.request.CertificateFindByRequestDTO;
import com.esm.security.dto.request.CertificateRequestDTO;
import com.esm.security.exception.AppNotFoundException;
import com.esm.security.exception.BadRequestException;
import com.esm.security.model.GiftCertificate;
import com.esm.security.model.Tag;
import com.esm.security.repository.CertificateRepository;
import com.esm.security.repository.TagRepository;
import com.esm.security.service.CertificateService;
import com.esm.security.service.impl.CertificateServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


public class CertificateServiceImplTest {

    @Test
    public void first() {

        List<GiftCertificate> certificates = new ArrayList<>();
        List<CertificateRequestDTO> expectedTags = new ArrayList<>();

        CertificateRequestDTO requestDTO = new CertificateRequestDTO();
        expectedTags.add(requestDTO);
        requestDTO.setName("Hello");

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("WOW");
        giftCertificate.setCertificateId(5);

        CertificateFindByRequestDTO certificateFindByRequestDTO = new CertificateFindByRequestDTO();

        Tag tag = new Tag();
        tag.setName("AMAZING");
        tag.setId(10);
        giftCertificate.setTag(tag);

        List<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Tag resTag = new Tag();
        resTag.setId(5);
        resTag.setName("Hello");

        certificates.add(giftCertificate);


        Pageable pageable = PageRequest.of(0, 5);

        CertificateRepository mock = Mockito.mock(CertificateRepository.class);
        when(mock.findById(5)).thenReturn(Optional.of(giftCertificate));
        // when(mock.findAll()).thenReturn(certificates);
        //when(mock.findByNameLike("He")).thenReturn(certificates);
        //when(mock.findByDescriptionLike("WOW")).thenReturn(certificates);

        when(mock.findByTagsName("AMAZING")).thenReturn(certificates);
        when(mock.countByTagsName()).thenReturn(tags);

        when(mock.findAll()).thenReturn(certificates);
        when(mock.save(giftCertificate)).thenReturn(giftCertificate);


        TagRepository tagMock = Mockito.mock(TagRepository.class);
        //when(tagMock.findByName("AMAZING")).thenReturn(tags);
        //when(tagMock.findAll()).thenReturn(tags);



        CertificateConverter converter = new CertificateConverter();
        converter.setTagConverter(new TagConverter());



    }
}

