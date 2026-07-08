package com.auctionapplication.controller;

import com.auctionapplication.model.LoaiSanPham;
import com.auctionapplication.service.LoaiSanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loai-san-pham")
public class LoaiSanPhamController {

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("loaiSanPhamList", loaiSanPhamService.findAll());
        return "loaisanpham/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("loaiSanPham", new LoaiSanPham());
        return "loaisanpham/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("loaiSanPham") LoaiSanPham loaiSanPham,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "loaisanpham/create";
        }

        try {
            loaiSanPhamService.save(loaiSanPham);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("name", "duplicate", ex.getMessage());
            return "loaisanpham/create";
        }

        return "redirect:/loai-san-pham";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        LoaiSanPham loaiSanPham = loaiSanPhamService.findById(id);
        if (loaiSanPham == null) {
            return "redirect:/loai-san-pham";
        }
        model.addAttribute("loaiSanPham", loaiSanPham);
        return "loaisanpham/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("loaiSanPham") LoaiSanPham loaiSanPham,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "loaisanpham/edit";
        }

        loaiSanPham.setId(id);
        try {
            loaiSanPhamService.save(loaiSanPham);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("name", "duplicate", ex.getMessage());
            return "loaisanpham/edit";
        }

        return "redirect:/loai-san-pham";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        try {
            loaiSanPhamService.deleteById(id);
        } catch (IllegalStateException ex) {
            model.addAttribute("loaiSanPhamList", loaiSanPhamService.findAll());
            model.addAttribute("errorMessage", ex.getMessage());
            return "loaisanpham/list";
        }
        return "redirect:/loai-san-pham";
    }
}
