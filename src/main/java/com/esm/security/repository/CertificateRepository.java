package com.esm.security.repository;


import com.esm.security.model.GiftCertificate;
import com.esm.security.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<GiftCertificate, Integer> {
    @Query("select count(g) from GiftCertificate g where g.certificateId >= ?1")
    long countByCertificateIdGreaterThanEqual(Integer certificateId);
    @Transactional
    @Modifying
    @Query("delete from GiftCertificate g where g.certificateId = ?1")
    int deleteByCertificateId(Integer certificateId);
    @Query("select t from GiftCertificate g right join Tag t on t.tag_id = g.certificateId")
    List<Tag> countByTagsName();
    List<GiftCertificate> findByName(String name);
    List<GiftCertificate> findByTagsName(String name);
    List<GiftCertificate> findByDescriptionLike(String description);
    List<GiftCertificate> findByNameLike(String name);


}
