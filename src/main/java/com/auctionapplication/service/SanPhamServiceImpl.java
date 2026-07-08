package com.auctionapplication.service;

import com.auctionapplication.model.SanPham;
import com.auctionapplication.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public Page<SanPham> search(String name, BigDecimal price, Long idLoaiSp, Pageable pageable) {
        Specification<SanPham> spec = buildSpecification(name, price, idLoaiSp);
        return sanPhamRepository.findAll(spec, pageable);
    }

    private Specification<SanPham> buildSpecification(String name, BigDecimal price, Long idLoaiSp) {
        return (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (name != null && !name.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
            }
            if (price != null) {
                // "Gia bat dau" search: tim san pham co gia khoi diem >= gia nhap
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), price));
            }
            if (idLoaiSp != null) {
                predicates.add(cb.equal(root.get("loaiSanPham").get("id"), idLoaiSp));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }

    @Override
    public void save(SanPham sanPham) {
        sanPhamRepository.save(sanPham);
    }

    @Override
    public SanPham findById(Long id) {
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        sanPhamRepository.deleteAllById(ids);
    }
}
