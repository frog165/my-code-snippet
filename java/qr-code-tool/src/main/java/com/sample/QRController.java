package com.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

@Controller
@RequestMapping("/")
public class QRController {

	@GetMapping
	public ModelAndView index() {
		return new ModelAndView("index", "param", new Param());
	}
	
	@GetMapping("index")
	public ModelAndView index2() {
		return new ModelAndView("index", "param", new Param());
	}
	
	@PostMapping("index")
	public ModelAndView create(@Valid Param param, BindingResult result) throws WriterException, IOException {
		if (result.hasErrors()) {
			return new ModelAndView("index", "formErrors", result.getAllErrors());
		}
		
		String fileName = UUID.randomUUID().toString() + ".png";
		String filePath = Paths.get(getBaseJarPath().getPath(), fileName).toString();
        generateQRCodeImage(param.getContent(), Math.toIntExact(param.getWidth()), Math.toIntExact(param.getHeight()), filePath);
		
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("param", param);
		modelAndView.addObject("successMessage", "Successfully created a QR Code");
		modelAndView.addObject("qrpath", "/qrcode/" + fileName);
		return modelAndView;
	}
	
	@GetMapping(value = "read-qr")
	public String readQRCode() {
		return "read-qr";
	}
	
	@PostMapping("read-qr")
	public ModelAndView readQRCode(@RequestParam("fileName") MultipartFile uploadfile) throws WriterException, IOException {
		
        BufferedImage bufferedImage = ImageIO.read(uploadfile.getInputStream());
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		ModelAndView modelAndView = new ModelAndView("read-qr");
        try {
            Result result = new MultiFormatReader().decode(bitmap);
    		modelAndView.addObject("successMessage", "QRCODE content is:" + result.getText());
    		System.out.println("QRCODE content is:" + result.getText());
    		return modelAndView;
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
    		modelAndView.addObject("successMessage", "There is no QR code in the image");
            return modelAndView;
        }
	}

    @GetMapping(value = "/qrcode/{file_name:.+}")
    public void getFile(
    		@PathVariable("file_name") String fileName, 
    		HttpServletResponse response) {
        try {
			String src= Paths.get(getBaseJarPath().getPath(), fileName).toString();
			InputStream is = new FileInputStream(src);          
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
        } catch (IOException ex) {
        	System.out.println("Error writing file to output stream. Filename was " + fileName);
        	ex.printStackTrace();
        	throw new RuntimeException("IOError writing file to output stream");
        }
    }    

    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

//    private byte[] getQRCodeImageByteArray(String text, int width, int height) throws WriterException, IOException {
//		  Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
//    	  hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
//
//        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//        byte[] pngData = pngOutputStream.toByteArray();
//        return pngData;
//    }

    /**
     *获取当前jar包所在系统中的目录
     */
    private File getBaseJarPath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile();
    }
    
}
