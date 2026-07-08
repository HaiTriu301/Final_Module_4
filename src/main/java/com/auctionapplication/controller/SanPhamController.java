package com.auctionapplication.controller;


import com.auctionapplication.model.SanPham;
import com.auctionapplication.service.LoaiSanPhamService;
import com.auctionapplication.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/san-pham")
public class SanPhamController {

    private static final int PAGE_SIZE = 5;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @GetMapping
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) BigDecimal price,
                       @RequestParam(required = false) Long idLoaiSp,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<SanPham> sanPhamPage = sanPhamService.search(name, price, idLoaiSp, pageable);

        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("loaiSanPhamList", loaiSanPhamService.findAll());

        model.addAttribute("name", name);
        model.addAttribute("price", price);
        model.addAttribute("idLoaiSp", idLoaiSp);

        return "sanpham/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("sanPham", new SanPham());
        model.addAttribute("loaiSanPhamList", loaiSanPhamService.findAll());
        return "sanpham/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("sanPham") SanPham sanPham,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loaiSanPhamList", loaiSanPhamService.findAll());
            return "sanpham/create";
        }

        sanPhamService.save(sanPham);
        return "redirect:/san-pham";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "ids", required = false) List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            sanPhamService.deleteByIds(ids);
        }
        return "redirect:/san-pham";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        SanPham sanPham = sanPhamService.findById(id);
        if (sanPham == null) {
            return "redirect:/san-pham";
        }
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("loaiSanPhamList", loaiSanPhamService.findAll());
        return "sanpham/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("sanPham") SanPham sanPham,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loaiSanPhamList", loaiSanPhamService.findAll());
            return "sanpham/edit";
        }

        sanPham.setId(id);
        sanPhamService.save(sanPham);
        return "redirect:/san-pham";
    }
}
