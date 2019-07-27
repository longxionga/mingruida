package com.acl.platform.controller;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformCompanyStaffService;
import com.acl.platform.service.IPlatformRefundvouchersService;
import com.acl.pojo.LoginSession;
import com.acl.pojo.SysFile;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import com.anchol.common.util.file.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年5月7日 上午10:48:57
 * department: 铭锐达
 * description:查询直营员工信息
 */
@Controller
@RequestMapping("/platform")
public class PlatformCompanyStaffController extends CoreBaseController {
    @Autowired
    private IPlatformCompanyStaffService iPlatformCompanyStaffService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/companyStaffInfo"})
    @RequestMapping("/companyStaffInfo")
    public String companyStaffInfo() {
        return "platform/platform_company_staff";
    }


    /**
     * 查询直营员工信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/queryCompanyStaffInfoPageList")
    public Object queryCompanyStaffInfoPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        String dept_id = loginSession.getDept_id();//用户所属部门
        String user_name = loginSession.getUser_name();
        paramsMap.put("user_name",user_name);
        paramsMap.put("dept_ids",dept_id);

        //查询
        PageList<?> list = (PageList<?>)iPlatformCompanyStaffService.queryCompanyStaffInfoPageList(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    @ResponseBody
    @RequiresAuthentication
    @RequestMapping(value = "/companyuploadfile")
    public Object companyuploadfile(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
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
               //直营员工信息导入
                iPlatformCompanyStaffService.companystaffimport(destTemp);

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
     * 删除员工信息信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/deleteCompanyStaffAll")
    public Object deleteCompanyStaffAll(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        try {
            iPlatformCompanyStaffService.deleteCompanyStaffAll(paramsMap);
            msg.setMsg("操作成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 删除员工信息信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/deleteCompanyStaff")
    public Object deleteCompanyStaff(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        try {
           String str =  iPlatformCompanyStaffService.deleteCompanyStaff(paramsMap);
            msg.setMsg(str);
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询员工机下级员工信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/querystaffInfoByType")
    public Object querystaffInfoByType(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                      @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        HashMap<String, Object> parmas = new HashMap<String, Object>();
//        parmas.put("dept_id", loginSession.getDept_id());
        //sysDeptInfoService.queryDeptChildNodeInfo(parmas);
//        if (paramsMap.isEmpty()) {
//            paramsMap.put("dept_id", loginSession.getDept_id());
//        }
        //paramsMap.put("search", loginSession.getDept_id());

        List<Map<String, Object>> list = iPlatformCompanyStaffService.querystaffInfoByType(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 查询员工所属分公司信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/querystaffCompanyInfo")
    public Object querystaffCompanyInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                      @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String brand_id=loginSession.getBrand_id();
        paramsMap.put("brand_id",brand_id);
        List<Map<String, Object>> list = iPlatformCompanyStaffService.querystaffCompanyInfo(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 查询品牌信息下拉框
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/querystaffInfoForCombox")
    public Object querystaffInfoForCombox(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                       @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        List<Map<String, Object>> list = iPlatformCompanyStaffService.querystaffInfoForCombox(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 查询员工所属分公司信息 下拉框
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/querystaffCompanyInfoForCombox")
    public Object querystaffCompanyInfoForCombox(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        List<Map<String, Object>> list = iPlatformCompanyStaffService.querystaffCompanyInfoForCombox(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 查询分公司经理信息 下拉框
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryCompanyManagerForCombox")
    public Object queryCompanyManagerForCombox(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        List<Map<String, Object>> list = iPlatformCompanyStaffService.queryCompanyManagerForCombox(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 新增员工信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/insCompanyStaff")
    public Object insCompanyStaff(@Session(create = false) SessionProvider session,@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        try{
            //新增员工信息
          String str =  iPlatformCompanyStaffService.insCompanyStaff(paramsMap,loginSession);
            if ("1".equals(str)){
                msg.setMsg("登陆员工信息异常，保存失败");
                msg.setSuccess(false);
            }else if ("2".equals(str)){
                msg.setMsg("主管上级不能是主管，保存失败");
                msg.setSuccess(false);
            }else if ("3".equals(str)){
                msg.setMsg("未找到登陆员工信息，保存失败");
                msg.setSuccess(false);
            }else if ("4".equals(str)){
                msg.setMsg("保存成功");
                msg.setSuccess(true);
            }else if ("5".equals(str)){
                msg.setMsg("保存失败，上级员工不存在");
                msg.setSuccess(false);
            }else {
                msg.setMsg("保存失败");
                msg.setSuccess(false);
            }
        }catch(Exception e){
            msg.setMsg("保存失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 修改员工信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/updCompanyStaff")
    public Object updCompanyStaff(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        try{
            iPlatformCompanyStaffService.updCompanyStaff(paramsMap);
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        }catch(Exception e){
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询部门 下拉框
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryStaffDeptInfoForCombox")
    public Object queryStaffDeptInfoForCombox(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                                 @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        List<Map<String, Object>> list = iPlatformCompanyStaffService.queryStaffDeptInfoForCombox(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 员工编号重复校验
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/staffValidate")
    public Object staffValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = iPlatformCompanyStaffService.staffValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 员工名称重复校验
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/companyStaffInfo" })
    @RequestMapping("/staffNameValidate")
    public Object staffNameValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = iPlatformCompanyStaffService.staffNameValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }


    /**
     * 查询部门 下拉框
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/getCompanyStaffBox")
    public Object getCompanyStaffBox(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                              @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        List<Map<String, Object>> list = iPlatformCompanyStaffService.getCompanyStaffBox(paramsMap,loginSession);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 下级员工下拉框
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/querySubStaffForCombox")
    public Object querySubStaffForCombox(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                               @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        List<Map<String, Object>> list = iPlatformCompanyStaffService.querySubStaffForCombox(paramsMap,loginSession);
        Object json = JSONObject.toJSON(list);
        return json;
    }

    /**
     * 查询直营员工信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/querystaffInfoInfoList")
    public Object querystaffInfoInfoList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

        //查询
        PageList<?> list = (PageList<?>)iPlatformCompanyStaffService.querystaffInfoInfoList(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * 查询部门主管 下拉框
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryStaffzhuguanForCombox")
    public Object queryStaffzhuguanForCombox(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                              @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        List<Map<String, Object>> list = iPlatformCompanyStaffService.queryStaffzhuguanForCombox(paramsMap);
        Object json = JSONObject.toJSON(list);
        return json;
    }
}
