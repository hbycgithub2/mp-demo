package com.itheima.mp.controller;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.mp.domain.dto.ShopDTO;
import com.itheima.mp.service.IShopService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-12-26
 */
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final IShopService shopService;

    /**
     * 查找所有商店信息
     *
     * @return 所有商店信息的列表
     */
    @GetMapping
    public List<ShopDTO> findAll(){
        return shopService.list()
                .stream()
                .map(ShopDTO::new)
                .peek(this::downloadImage)
                .collect(Collectors.toList());
    }


    /**
     * 扣减用户余额接口
     *
     * @param id 用户id
     * @param money 扣减的金额
     */
    @PutMapping("/{id}/deduction/{money}")
    public void deductBalance(
            @ApiParam("用户id") @PathVariable("id") Long id,
            @ApiParam("扣减的金额") @PathVariable("money") Integer money){
        shopService.list()
                // 将商店信息转换为ShopDTO对象
                .stream()
                .map(ShopDTO::new)
                // 调用downloadImage方法，下载商店的图片
                .peek(this::downloadImage)
                // 将转换后的ShopDTO对象收集到一个List中
                .collect(Collectors.toList());
    }

    private void downloadImage(ShopDTO shopDTO) {
        for (String image : shopDTO.getImages()) {
            try {
                // 获取图片地址
                URLConnection connection = new URL(image).openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                String name = RandomUtil.randomString(20);

                String rawName = StrUtil.subAfter(image, '/', true);
                System.out.println("rawName = " + rawName + "， newName = " + name);

                Files.copy(inputStream, new File("C:\\Users\\amy\\Desktop\\" + name + ".jpg").toPath());

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
