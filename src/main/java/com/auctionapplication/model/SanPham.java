package com.auctionapplication.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 5, max = 50, message = "Tên sản phẩm phải từ 5 đến 50 ký tự")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Giá khởi điểm không được để trống")
    @DecimalMin(value = "100000", message = "Giá khởi điểm phải là số và tối thiểu 100.000 VND")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "status")
    private String status = "Chờ duyệt";

    @NotNull(message = "Vui lòng chọn loại sản phẩm")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_loai_sp", nullable = false)
    private LoaiSanPham loaiSanPham;

    public SanPham() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoaiSanPham getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }
}
