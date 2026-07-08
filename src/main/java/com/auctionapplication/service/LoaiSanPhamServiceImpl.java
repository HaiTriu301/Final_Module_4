package com.auctionapplication.service;

import com.auctionapplication.model.LoaiSanPham;
import com.auctionapplication.repository.LoaiSanPhamRepository;
import com.auctionapplication.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {

    @Autowired
    private LoaiSanPhamRepository loaiSanPhamRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<LoaiSanPham> findAll() {
        return loaiSanPhamRepository.findAll();
    }

    @Override
    public LoaiSanPham findById(Long id) {
        return loaiSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public LoaiSanPham save(LoaiSanPham loaiSanPham) {
        if (isNameDuplicate(loaiSanPham.getName(), loaiSanPham.getId())) {
            throw new IllegalArgumentException("Loại sản phẩm \"" + loaiSanPham.getName() + "\" đã tồn tại");
        }
        return loaiSanPhamRepository.save(loaiSanPham);
    }

    @Override
    public void deleteById(Long id) {
        long soSanPhamDangDung = sanPhamRepository.countByLoaiSanPhamId(id);
        if (soSanPhamDangDung > 0) {
            throw new IllegalStateException(
                    "Không thể xoá vì đang có " + soSanPhamDangDung + " sản phẩm thuộc loại này");
        }
        loaiSanPhamRepository.deleteById(id);
    }

    @Override
    public boolean isNameDuplicate(String name, Long excludeId) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (excludeId == null) {
            return loaiSanPhamRepository.existsByNameIgnoreCase(name.trim());
        }
        return loaiSanPhamRepository.existsByNameIgnoreCaseAndIdNot(name.trim(), excludeId);
    }
}
