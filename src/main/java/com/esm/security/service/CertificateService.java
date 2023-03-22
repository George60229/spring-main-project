package com.esm.security.service;



import com.esm.security.converter.CertificateConverter;
import com.esm.security.converter.TagConverter;
import com.esm.security.dto.request.CertificateEditRequestDto;
import com.esm.security.dto.request.CertificateFindByRequestDTO;
import com.esm.security.dto.request.CertificateRequestDTO;
import com.esm.security.dto.response.ResponseCertificateDTO;
import com.esm.security.dto.response.TagResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CertificateService {




    /**
     * get all entity
     *
     * @return list of certificates.
     */


    Page<ResponseCertificateDTO> listCertificates(CertificateFindByRequestDTO certificateFindByRequestDTO, Pageable pageable);

    /**
     * delete entity by id
     *
     * @param id just id
     */

    void deleteCertificateById(Integer id);


    ResponseCertificateDTO getCertificateById(@RequestParam int id);


    /**
     * get all entity by description
     *
     * @return list of certificates.
     */


    /**
     * get all entity ordered by date asc
     *
     * @return list of certificates.
     */


    /**
     * get all entity ordered by date desc
     *
     * @return list of certificates.
     */


    ResponseCertificateDTO createCertificate(@RequestBody CertificateRequestDTO certificateDTO);


    ResponseCertificateDTO editCertificate(CertificateRequestDTO certificateEditDto, int id);

    Page<ResponseCertificateDTO> findByTagName(String tagName, Pageable pageable);

    ResponseCertificateDTO editOneField(CertificateEditRequestDto certificateEditRequestDto);

    Page<ResponseCertificateDTO> findByTagsNameList(List<String> tagNames,Pageable pageable);

    TagResponseDTO popularTag();

    int amount();
}


