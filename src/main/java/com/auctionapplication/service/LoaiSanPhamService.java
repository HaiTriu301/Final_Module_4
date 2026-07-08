package com.auctionapplication.service;


import com.auctionapplication.model.LoaiSanPham;

import java.util.List;

public interface LoaiSanPhamService {
    List<LoaiSanPham> findAll();

    LoaiSanPham findById(Long id);

    LoaiSanPham save(LoaiSanPham loaiSanPham);

    void deleteById(Long id);

    boolean isNameDuplicate(String name, Long excludeId);
}
