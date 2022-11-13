package com.ureport.ureportkeep.controller.image;

import com.amazonaws.util.IOUtils;
import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.core.utils.ReportProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author: summer
 * @Date: 2022/11/13 11:58
 * @Description:
 **/
@RestController
@RequestMapping("/api/image")
public class ImageUploadController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportProperties reportProperties;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/upload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return "";
        } else {

            String basePath = ReportProperties.BASE_STORE_DIR;
            String fileName = file.getOriginalFilename();   //文件名
            String filePath = basePath + reportProperties.getFileStoreDir() + "\\image\\"; //存储路径
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(dest);
                IOUtils.copy(file.getInputStream(), fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "/api/image/loadImg/" + fileName;
        }
    }

    /**
     * 加载图片
     *
     * @param fileName
     * @return
     */
    @GetMapping(value = "/loadImg/{fileName}")
    public void loadImg(HttpServletResponse response,
                          @PathVariable("fileName") String fileName) {

        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            outputStream = response.getOutputStream();
            File file = new File(ReportProperties.BASE_STORE_DIR + reportProperties.getFileStoreDir() + "\\image\\" + fileName);
            fileInputStream = new FileInputStream(file);
            BufferedImage bufferedImage = ImageIO.read(fileInputStream);
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (StringUtils.isEmpty(ext)) {
                ext = "png";
            }

            response.setContentType("image/" + ext);
            response.setHeader("Content-Disposition", "inline;filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\";");
            ImageIO.write(bufferedImage, ext, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/delete")
    public R delete(@RequestParam String fileName) {
        File file = new File(ReportProperties.BASE_STORE_DIR + reportProperties.getFileStoreDir() + "\\image\\" + fileName);
        boolean delete = file.delete();
        if (delete) {
            return R.ok();
        }
        return R.error("图片删除失败！");
    }

}
