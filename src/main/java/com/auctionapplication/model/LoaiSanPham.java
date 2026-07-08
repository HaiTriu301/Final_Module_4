package com.auctionapplication.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "loai_san_pham")
public class LoaiSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    @Size(min = 2, max = 50, message = "Tên loại sản phẩm phải từ 2 đến 50 ký tự")
    @Column(name = "name", nullable = false)
    private String name;

    public LoaiSanPham() {
    }

    public LoaiSanPham(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
