package com.acl.platform.controller;

import com.acl.component.MongodbBaseDao;
import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformRefundvouchersService;
import com.acl.platform.service.PlatformAgentDeptService;
import com.acl.pojo.LoginSession;
import com.acl.pojo.SysFile;
import com.acl.pojo.UserInfo;
import com.acl.sys.service.SysDeptInfoService;
import com.acl.utils.excel.ParsingExcelUtil;
import com.acl.utils.excel.RefundExcelDataEntity;
import com.acl.utils.excel.RefundVouchersInfo;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.*;
import com.alibaba.fastjson.JSONArray;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import com.anchol.common.util.file.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年4月30日 上午10:48:57
 * department: 铭锐达
 * description:查询充值券信息
 */
@Controller
@RequestMapping("/platform")
public class PlatformRefundvouchersController extends CoreBaseController {
    @Autowired
    private IPlatformRefundvouchersService platformRefundvouchersService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/refundVouchers"})
    @RequestMapping("/refundVouchers")
    public String refundVouchers() {
        return "platform/platform_refund_vouchers";
    }

    /**
     * 查询充值券信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/refundVouchers" })
    @RequestMapping("/queryRefundvouchersInfoPageList")
    public Object queryRefundvouchersInfoPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
        if (paramsMap.get("start_date") != null) {
            paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
        }
        if (paramsMap.get("end_date") != null) {
            paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
        }
        //查询充值券信息
        PageList<?> list = (PageList<?>)platformRefundvouchersService.queryRefundvouchersInfoPageList(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    @ResponseBody
    @RequiresAuthentication
    @RequestMapping(value = "/refunduploadfile")
    public Object refunduploadfile(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
       // String brandId = (String)request.getParameter("brandId");
       //String brandType = (String)request.getParameter("brandType");
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
               //充值券信息导入
                platformRefundvouchersService.refundvouchersimport(destTemp);

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
            result.put("state", "上传失败");
        }
        return result;
    }

    /**
     * 删除充值券信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/refundVouchers" })
    @RequestMapping("/deleteRefundVoucherAll")
    public Object deleteRefundVoucherAll(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                @RequestParam HashMap<String, Object> paramsMap) {
        try {
            // 重置密码
            platformRefundvouchersService.deleteRefundVoucherAll(paramsMap);
            msg.setMsg("操作成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


}
