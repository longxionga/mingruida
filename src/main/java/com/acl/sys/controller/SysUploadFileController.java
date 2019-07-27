package com.acl.sys.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acl.pojo.UserInfo;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.excel.ParsingExcelUtil;
import com.acl.utils.util.CollectionUtil;
import com.acl.utils.util.MD5Utils;
import com.acl.utils.util.UUIDGenerator;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.pojo.SysFile;
import com.acl.sys.service.impl.SysFileServiceImpl;
import com.anchol.common.util.file.FileUtil;
import com.anchol.common.util.image.ImageUtils;

/**
 * 编辑器文件上传 
 **/
@Controller
@RequestMapping("/upload")
public class SysUploadFileController extends CoreBaseController {
  private static final Logger LOGGER = LoggerFactory.getLogger(SysUploadFileController.class);



  @Autowired
  private SysFileServiceImpl sysFileService;

  @Autowired
  private SysUserInfoService sysUserInfoService;



  @ResponseBody
  @RequiresAuthentication
  @RequestMapping(value = "/uploadfile")
  public Object uploadfile(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> result = new HashMap<String, Object>();
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    SystemConfig systemConfig = (SystemConfig) applicationContext.getBean("systemConfig");
    try {
      //获得临时文件
      String path = systemConfig.getTempPath();
      //创建临时目录
      File fileDir = new File(path);
      if(!fileDir.exists() && !fileDir.isDirectory()) {
        fileDir.mkdir();
      }

      for (int i = 0; i < file.length; i++) {
        MultipartFile multipartFiles = file[i];
        //SysFile sysFile = sysFileService.fileUpload(file[i], "image");

        String fileName = UUIDGenerator.generate() + multipartFiles.getOriginalFilename();
        String filePath = path + System.getProperty("file.separator") + fileName;
        File destTemp = new File(filePath);
        InputStream is = null;
        FileUtil.writeFile(file[i].getInputStream(), destTemp);
        is = new FileInputStream(destTemp);

        ParsingExcelUtil parsingExcelUtil = new ParsingExcelUtil();
        List<ParsingExcelUtil.BasicInfo> basicInfoList = parsingExcelUtil.getParsingExcelResult(destTemp);
        List<UserInfo> userInfoList = sysUserInfoService.getUserInfoList();
        Map<String,UserInfo> map = new HashMap<>();
        if(CollectionUtil.isNotEmpty(userInfoList)){
          userInfoList.parallelStream().forEach(userInfo -> {
            map.put(userInfo.getMobile(),userInfo);
          });
        }
        if(CollectionUtil.isNotEmpty(basicInfoList)){
          for (int j=0;j<basicInfoList.size();j++){
            ParsingExcelUtil.BasicInfo basicInfo =  basicInfoList.get(j);
            if(!(StringUtils.isNotEmpty(basicInfo.getId())
                    && StringUtils.isNotEmpty(basicInfo.getMobile())
                    && basicInfo.getMobile().length() == 11
                    && StringUtils.isNotEmpty(basicInfo.getIdCard())
                    && basicInfo.getIdCard().length() ==18)){
              LOGGER.info("数据不完整:"+basicInfo.toString());
              continue;
            }
            if(StringUtils.isNotEmpty(basicInfo.getId())
                    && StringUtils.isNotEmpty(basicInfo.getMobile())
                    && basicInfo.getMobile().length() == 11
                    && StringUtils.isNotEmpty(basicInfo.getIdCard())
                    && basicInfo.getIdCard().length() ==18 ){
              if(!map.containsKey(basicInfo.getMobile())){
                UserInfo userInfo = new UserInfo();
                userInfo.setId(UUIDGenerator.generate());
                userInfo.setAccountLevel(1);
                userInfo.setAccountType("01");
                userInfo.setCreateTime(new Date());
                userInfo.setMobile(basicInfo.getMobile());
                userInfo.setIdCard(basicInfo.getIdCard());
                userInfo.setName(basicInfo.getName());
                userInfo.setStatus(1);
                userInfo.setUpPassword("02");
                userInfo.setPassword(MD5Utils.sign(StringUtils.substring(basicInfo.getIdCard(),basicInfo.getIdCard().length()-6,basicInfo.getIdCard().length()), MD5Utils.PWD_KEY, MD5Utils.DEFAULT_UTF_8_INPUT_CHARSET));
                userInfo.setJob(StringUtils.defaultIfEmpty(basicInfo.getJob(),null));
                userInfo.setJobDay(StringUtils.defaultIfEmpty(basicInfo.getJobDay(),null));
                userInfo.setWordDays(StringUtils.defaultIfEmpty(basicInfo.getWordDays(),null));
                userInfo.setLeaveDays(StringUtils.defaultIfEmpty(basicInfo.getLeaveDays(),null));
                userInfo.setTotalAmount(StringUtils.defaultIfEmpty(basicInfo.getTotalAmount(),null));
                userInfo.setRealAmount(StringUtils.defaultIfEmpty(basicInfo.getRealAmount(),null));
                userInfo.setDeductAmount(StringUtils.defaultIfEmpty(basicInfo.getDeductAmount(),null));
                userInfo.setNotes(StringUtils.defaultIfEmpty(basicInfo.getNotes(),null));
                userInfo.setMonthCheck(StringUtils.defaultIfEmpty(basicInfo.getMonthCheck(),null));
                userInfo.setMonthUncheck(StringUtils.defaultIfEmpty(basicInfo.getMonthUnCheck(),null));
                userInfo.setDeductAmount(StringUtils.defaultIfEmpty(basicInfo.getDeductAmount(),null));


                Map<String,ParsingExcelUtil.MergedRowTitle> totalMap = basicInfo.getTotalMap();
                if(totalMap!=null){
                  List<ParsingExcelUtil.MergedRowTitle> totalMergedRowTitles = new ArrayList<>();
                  for(String s:totalMap.keySet()){
                    ParsingExcelUtil.MergedRowTitle mergedRowTitle = totalMap.get(s);
                    //LOGGER.info("合并的 key : "+s+" value : "+mergedRowTitle);
                    totalMergedRowTitles.add(mergedRowTitle);
                  }
                  userInfo.setTotalMap(JSONArray.toJSONString(totalMergedRowTitles));
                }
                Map<String,ParsingExcelUtil.MergedRowTitle> deductMap = basicInfo.getDeductMap();
                if(deductMap!=null){
                  List<ParsingExcelUtil.MergedRowTitle> deductMergedRowTitles = new ArrayList<>();
                  for(String s:deductMap.keySet()){
                    ParsingExcelUtil.MergedRowTitle mergedRowTitle = deductMap.get(s);
                    //LOGGER.info("合并的 key : "+s+" value : "+mergedRowTitle);
                    deductMergedRowTitles.add(mergedRowTitle);
                  }
                  userInfo.setDeductMap(JSONArray.toJSONString(deductMergedRowTitles));
                }
                sysUserInfoService.saveUserInfo(userInfo);
                map.put(userInfo.getMobile(),userInfo);
              }else{
                UserInfo userInfo = map.get(basicInfo.getMobile());
                UserInfo updateUserInfo = new UserInfo();
                updateUserInfo.setId(userInfo.getId());
                updateUserInfo.setIdCard(basicInfo.getIdCard());
                updateUserInfo.setUpdateTime(new Date());
                updateUserInfo.setName(basicInfo.getName());
                updateUserInfo.setJob(StringUtils.defaultIfEmpty(basicInfo.getJob(),""));
                updateUserInfo.setJobDay(StringUtils.defaultIfEmpty(basicInfo.getJobDay(),""));
                updateUserInfo.setWordDays(StringUtils.defaultIfEmpty(basicInfo.getWordDays(),""));
                updateUserInfo.setLeaveDays(StringUtils.defaultIfEmpty(basicInfo.getLeaveDays(),""));
                updateUserInfo.setTotalAmount(StringUtils.defaultIfEmpty(basicInfo.getTotalAmount(),""));
                updateUserInfo.setRealAmount(StringUtils.defaultIfEmpty(basicInfo.getRealAmount(),""));
                updateUserInfo.setDeductAmount(StringUtils.defaultIfEmpty(basicInfo.getDeductAmount(),""));
                updateUserInfo.setNotes(StringUtils.defaultIfEmpty(basicInfo.getNotes(),""));
                updateUserInfo.setMonthCheck(StringUtils.defaultIfEmpty(basicInfo.getMonthCheck(),""));
                updateUserInfo.setMonthUncheck(StringUtils.defaultIfEmpty(basicInfo.getMonthUnCheck(),""));
                updateUserInfo.setDeductAmount(StringUtils.defaultIfEmpty(basicInfo.getDeductAmount(),""));
                Map<String,ParsingExcelUtil.MergedRowTitle> totalMap = basicInfo.getTotalMap();
                if(totalMap!=null){
                  List<ParsingExcelUtil.MergedRowTitle> totalMergedRowTitles = new ArrayList<>();
                  for(String s:totalMap.keySet()){
                    ParsingExcelUtil.MergedRowTitle mergedRowTitle = totalMap.get(s);
                    //LOGGER.info("合并的 key : "+s+" value : "+mergedRowTitle.toString());
                    totalMergedRowTitles.add(mergedRowTitle);
                  }
                  updateUserInfo.setTotalMap(JSONArray.toJSONString(totalMergedRowTitles));
                }
                Map<String,ParsingExcelUtil.MergedRowTitle> deductMap = basicInfo.getDeductMap();
                if(deductMap!=null){
                  List<ParsingExcelUtil.MergedRowTitle> deductMergedRowTitles = new ArrayList<>();
                  for(String s:deductMap.keySet()){
                    ParsingExcelUtil.MergedRowTitle mergedRowTitle = deductMap.get(s);
                    //LOGGER.info("合并的 key : "+s+" value : "+mergedRowTitle);
                    deductMergedRowTitles.add(mergedRowTitle);
                  }
                  updateUserInfo.setDeductMap(JSONArray.toJSONString(deductMergedRowTitles));
                }
                updateUserInfo.setStatus(1);
                sysUserInfoService.updateUserInfo(updateUserInfo);
              }
            }
          }
        }

        //SysFile sysFilePrefix = sysFileService.fileUpload(file[i].getOriginalFilename(), "image", is, filePath);
        SysFile sysFilePrefix = new SysFile();
        sysFilePrefix.setFileName(fileName);
        sysFilePrefix.setFileType("xlsx");
        sysFilePrefix.setFilePath(path);
        sysFilePrefix.setFileSize(multipartFiles.getSize());
        String url = systemConfig.getFileServBaseUrl() + sysFilePrefix.getFilePath();
        String u = Base64.encodeBase64String(url.getBytes("UTF-8"));
        result.put("url", SystemConfig.localUrl + "/upload/getImage/" + u);
        result.put("original", sysFilePrefix.getFileName());
        result.put("size", sysFilePrefix.getFileSize());
        result.put("type", sysFilePrefix.getFileType());
        result.put("state", "SUCCESS");
        is.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }


  @ResponseBody
  @RequiresAuthentication
  @RequestMapping(value = "/uploadImage")
  public Object uploadImage(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> result = new HashMap<String, Object>(); 
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    SystemConfig systemConfig = (SystemConfig) applicationContext.getBean("systemConfig");
    try {
    //获得临时文件
      String path = systemConfig.getTempPath();
    //创建临时目录
      File fileDir = new File(path);
      if(!fileDir.exists() && !fileDir.isDirectory()) {
        fileDir.mkdir();
      }
      
      for (int i = 0; i < file.length; i++) {
        SysFile sysFile = sysFileService.fileUpload(file[i], "image");
        
        String fileName = "uploadImage-"+UUID.randomUUID()+".jpg";
        String filePath = path + System.getProperty("file.separator") + fileName;
        File destTemp = new File(filePath);
        InputStream is = null;
        FileUtil.writeFile(file[i].getInputStream(), destTemp);
       
        BufferedImage sourceImg =ImageIO.read(new FileInputStream(destTemp)); 
        int imgWidth  = sourceImg.getWidth();
        int imgHeight = sourceImg.getHeight();
        
        if (imgWidth > 640) {//图片宽度大于640进行压缩
          DecimalFormat df = new DecimalFormat("0.00"); 
          double proportion = Double.parseDouble(df.format((float)640/imgWidth));
//          System.out.println(imgHeight);
//          System.out.println(proportion);
//          System.out.println(df.format((float)proportion * imgHeight));
//          System.out.println(Float.parseFloat(df.format((float)proportion * imgHeight)));
          BufferedImage img = ImageIO.read(file[i].getInputStream());
          ImageUtils.compressPic(img, destTemp, 640, (int)Float.parseFloat(df.format((float)proportion * imgHeight)));
        }
        is = new FileInputStream(destTemp);
        SysFile sysFilePrefix = sysFileService.fileUpload(file[i].getOriginalFilename(), "image", is, sysFile.getFilePath());
        String url = systemConfig.getFileServBaseUrl() + sysFilePrefix.getFilePath();
        String u = Base64.encodeBase64String(url.getBytes("UTF-8"));
        result.put("url", SystemConfig.localUrl + "/upload/getImage/" + u);
        result.put("original", sysFilePrefix.getFileName());
        result.put("size", sysFilePrefix.getFileSize());
        result.put("type", sysFilePrefix.getFileType());
        result.put("state", "SUCCESS");
      }
    } catch (Exception e) {
      e.printStackTrace();
	}
    return result;
  }



  @ResponseBody
  @RequiresAuthentication
  @RequestMapping(value = "/getImage/{path}")
  public void getImage(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, MalformedURLException {
    FileInputStream fis = null;
    OutputStream os = null;
    InputStream is = null;
    try {
      String a = request.getScheme() + ":" + new String(Base64.decodeBase64(path), "UTF-8");
//      SSLContext sc = SSLContext.getInstance("SSL");
//      sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
      URL url = new URL(a);
      System.out.println(url);
//      HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//      httpsConn.setSSLSocketFactory(sc.getSocketFactory());
//      httpConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
      httpConn.connect();
      is = httpConn.getInputStream();
      os = response.getOutputStream();
      int c;
      byte[] b = new byte[4096];
      while ((c = is.read(b)) != -1) {
        os.write(b, 0, c);
      }
      os.flush();
    } catch (Exception e) {
	      e.printStackTrace();
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (Exception e) {

        }
      }
      if (os != null) {
        try {
          os.close();
        } catch (Exception e) {

        }
      }
      if (is != null) {
        try {
          is.close();
        } catch (Exception e) {
        }
      }
//	return is;
    }
  }

//  private static class TrustAnyTrustManager implements X509TrustManager {
//    public void checkClientTrusted(X509Certificate[] chain, String authType)
//        throws CertificateException {
//    }
//
//    public void checkServerTrusted(X509Certificate[] chain, String authType)
//        throws CertificateException {
//    }
//
//    public X509Certificate[] getAcceptedIssuers() {
//      return new X509Certificate[] {};
//    }
//  }

//  private static class TrustAnyHostnameVerifier implements HostnameVerifier {
//    public boolean verify(String hostname, SSLSession session) {
//      return true;
//    }
//  }
  
  @ResponseBody
  @RequiresAuthentication
  @RequestMapping(value = "/uploadGoodsImage")
  public Object uploadGoodsImage(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> result = new HashMap<String, Object>(); 
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    SystemConfig systemConfig = (SystemConfig) applicationContext.getBean("systemConfig");
    String floder=request.getParameter("floder");
    try {
    //获得临时文件
      String path = systemConfig.getTempPath();     
    //创建临时目录
      File fileDir = new File(path);
      if(!fileDir.exists() && !fileDir.isDirectory()) {
        fileDir.mkdir();
      }
      
      for (int i = 0; i < file.length; i++) {
        SysFile sysFile = sysFileService.fileUploadImg(file[i], "image",floder);
        
        String fileName = "uploadImage-"+UUID.randomUUID()+".jpg";
        String filePath = path + System.getProperty("file.separator") + fileName;
        File destTemp = new File(filePath);
        InputStream is = null;
        FileUtil.writeFile(file[i].getInputStream(), destTemp);
       
        BufferedImage sourceImg =ImageIO.read(new FileInputStream(destTemp)); 
        int imgWidth  = sourceImg.getWidth();
        int imgHeight = sourceImg.getHeight();
        
        if (imgWidth > 640) {//图片宽度大于640进行压缩
          DecimalFormat df = new DecimalFormat("0.00"); 
          double proportion = Double.parseDouble(df.format((float)640/imgWidth));
//          System.out.println(imgHeight);
//          System.out.println(proportion);
//          System.out.println(df.format((float)proportion * imgHeight));
//          System.out.println(Float.parseFloat(df.format((float)proportion * imgHeight)));
          BufferedImage img = ImageIO.read(file[i].getInputStream());
          ImageUtils.compressPic(img, destTemp, 640, (int)Float.parseFloat(df.format((float)proportion * imgHeight)));
        }
        is = new FileInputStream(destTemp);
        SysFile sysFilePrefix = sysFileService.fileUploadImg(file[i].getOriginalFilename(), "image", is,floder, sysFile.getFilePath());
        String url = systemConfig.getFileServBaseUrl() + sysFilePrefix.getFilePath();
        String u = Base64.encodeBase64String(url.getBytes("UTF-8"));
        //result.put("url", SystemConfig.localUrl + "/upload/getImage/" + u);
        result.put("url", sysFile.getFilePath());
        result.put("original", sysFilePrefix.getFileName());
        result.put("size", sysFilePrefix.getFileSize());
        result.put("type", sysFilePrefix.getFileType());
        result.put("state", "SUCCESS");
      }
    } catch (Exception e) {
      e.printStackTrace();
	}
    return result;
  }
}
