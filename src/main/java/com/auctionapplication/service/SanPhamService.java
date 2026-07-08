package com.auctionapplication.service;

import com.auctionapplication.model.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface SanPhamService {

    Page<SanPham> search(String name, BigDecimal price, Long idLoaiSp, Pageable pageable);

    void save(SanPham sanPham);

    SanPham findById(Long id);

    void deleteByIds(List<Long> ids);
}
